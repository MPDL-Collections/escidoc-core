/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at license/ESCIDOC.LICENSE
 * or http://www.escidoc.de/license.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at license/ESCIDOC.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright 2006-2009 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.adm.business.admin;

import de.escidoc.core.purge.PurgeRequest;
import de.escidoc.core.purge.PurgeRequestBuilder;
import de.escidoc.core.purge.PurgeService;
import de.escidoc.core.adm.business.renderer.interfaces.AdminRendererInterface;
import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.business.fedora.Utility;
import de.escidoc.core.common.business.fedora.resources.ResourceType;
import de.escidoc.core.common.business.indexing.IndexingHandler;
import de.escidoc.core.common.business.interfaces.RecacherInterface;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.system.EncodingSystemException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.exceptions.system.TripleStoreSystemException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.configuration.EscidocConfiguration;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.handler.TaskParamHandler;
import de.escidoc.core.common.util.xml.XmlUtility;
import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Administration tool that rebuilds the search index, rebuilds the resource
 * cache and deletes objects physically from the repository.
 * 
 * @spring.bean id="business.AdminHandler"
 * 
 * @author sche
 */
public class AdminHandler {
    // mapping from object type used in indexer to ResourceType
    private static final Map<String, ResourceType> OBJECT_TYPES =
        new HashMap<String, ResourceType>() {
            private static final long serialVersionUID = -3677814223372272211L;

            {
                put(Constants.CONTAINER_OBJECT_TYPE, ResourceType.CONTAINER);
                put(Constants.CONTEXT_OBJECT_TYPE, ResourceType.CONTEXT);
                put(Constants.ITEM_OBJECT_TYPE, ResourceType.ITEM);
                put(Constants.ORGANIZATIONAL_UNIT_OBJECT_TYPE, ResourceType.OU);
            }
        };

    private FrameworkInfo frameworkInfo;

    private RecacherInterface recacher = null;

    private Reindexer reindexer = null;

    private Utility utility = null;

    private IndexingHandler indexingHandler = null;

    private AdminRendererInterface renderer;

    private PurgeService purgeService;

    private static AppLogger log = new AppLogger(AdminHandler.class.getName());

    /**
     * Delete a list of objects given by their object id's from Fedora. In case
     * of items this method will also delete all depending components of the
     * given items. The deletion runs synchronously and returns some useful
     * information for the user, e.g. the total number of objects deleted.
     * 
     * @param taskParam
     *            list of object id's to be deleted boolean value to signal if
     *            the search index and the resource cache have to be kept in
     *            sync. If this value is set to false then the re-indexing and
     *            re-caching should be run manually afterwards.
     * 
     * @return total number of objects deleted, ...
     * @throws InvalidXmlException
     *             thrown if the taskParam has an invalid structure
     * @throws SystemException
     *             thrown in case of an internal error
     */
    public String deleteObjects(final String taskParam)
        throws InvalidXmlException, SystemException {
        StringBuffer result = new StringBuffer();
        PurgeStatus purgeStatus = PurgeStatus.getInstance();

        if (purgeStatus.startMethod()) {
            TaskParamHandler taskParameter =
                XmlUtility.parseTaskParam(taskParam, false);

            try {
                for (String id : taskParameter.getIds()) {
                    final PurgeRequest purgeRequest = PurgeRequestBuilder.createPurgeRequest()
                            .withResourceId(id)
                            .build();
                    this.purgeService.purge(purgeRequest);
                    if (taskParameter.getKeepInSync()) {
                        // synchronize resource cache
                        recacher.deleteResource(id);
                        // synchronize search index
                        reindexer.sendDeleteObjectMessage(id);
                    }
                }
                result.append("<message>\n");
                result.append("scheduling " + taskParameter.getIds().size()
                    + " objects(s) for deletion from Fedora\n");
                result.append("</message>\n");
                if (taskParameter.getKeepInSync()) {
                    result.append("<message>\n");
                    result.append("scheduling " + taskParameter.getIds().size()
                        + " object(s) for deletion from resource cache\n");
                    result.append("</message>\n");
                    result.append("<message>\n");
                    result.append("scheduling " + taskParameter.getIds().size()
                        + " object(s) for deletion from search index\n");
                    result.append("</message>\n");
                }
            }
            finally {
                if (taskParameter.getIds().size() == 0) {
                    purgeStatus.finishMethod();
                }
                purgeStatus.setFillingComplete();
            }
        }
        else {
            result.append("<message>\n");
            result.append(purgeStatus);
            result.append("</message>\n");
        }

        DateTime t = null;
        return getUtility().prepareReturnXml(t, result.toString());
    }

    /**
     * Get the current status of the running/finished purging process.
     * 
     * @return current status (how many objects are still in the queue)
     * @throws SystemException
     *             thrown in case of an internal error
     */
    public String getPurgeStatus() throws SystemException {

        DateTime t = null;
        return getUtility().prepareReturnXml(t, PurgeStatus.getInstance().toString());
    }

    /**
     * Get the current status of the running/finished recaching process.
     * 
     * @return current status (how many objects are still in the queue)
     * @throws SystemException
     *             thrown in case of an internal error
     */
    public String getRecacheStatus() throws SystemException {

        DateTime t = null;
        return getUtility().prepareReturnXml(t, recacher.getStatus());
    }

    /**
     * Get the current status of the running/finished reindexing process.
     * 
     * @return current status (how many objects are still in the queue)
     * @throws SystemException
     *             thrown in case of an internal error
     */
    public String getReindexStatus() throws SystemException {

        DateTime t = null;
        return getUtility().prepareReturnXml(t, reindexer.getStatus());
    }

    /**
     * decrease the type of the current status of the running reindexing process
     * by 1.
     * 
     * @param objectType
     *            object-type to decrease
     */
    public void decreaseReindexStatus(final String objectType) {
        if (objectType != null) {
            ReindexStatus.getInstance().dec(OBJECT_TYPES.get(objectType));
        }
    }

    /**
     * @return Returns the utility.
     */
    private Utility getUtility() {
        if (utility == null) {
            utility = Utility.getInstance();
        }
        return utility;
    }

    /**
     * Reinitialize the resource cache. The initialization runs asynchronously
     * and returns some useful information for the user, e.g. the total number
     * of resources found. To get further information about the progress of this
     * operation use method {@link #getRecacheStatus()}.
     * 
     * @param clearCache
     *            clear the cache before adding objects to it
     * 
     * @return total number of resources found, ...
     * @throws SystemException
     *             thrown in case of an internal error
     */
    public String recache(final boolean clearCache) throws SystemException {

        DateTime t = null;
        return getUtility().prepareReturnXml(t, recacher.recache(clearCache));
    }

    /**
     * Reinitialize the search index. The initialization runs synchronously and
     * returns some useful information for the user, e.g. the total number of
     * objects found.
     * 
     * @param clearIndex
     *            clear the index before adding objects to it
     * @param indexNamePrefix
     *            name of the index (may be null for "all indexes")
     * 
     * @return total number of objects found, ...
     * @throws SystemException
     *             Thrown if a framework internal error occurs.
     * @throws InvalidSearchQueryException
     *             thrown if the given search query could not be translated into
     *             a SQL query
     */
    public String reindex(final boolean clearIndex, final String indexNamePrefix)
        throws SystemException, InvalidSearchQueryException {
        return getUtility().prepareReturnXml((DateTime) null,
            reindexer.reindex(clearIndex, indexNamePrefix));
    }

    /**
     * Provides a xml structure containing the index-configuration.
     * 
     * @return xml structure with index configuration
     * @throws WebserverSystemException
     *             if anything goes wrong.
     * @throws TripleStoreSystemException
     *             if anything goes wrong.
     * @throws EncodingSystemException
     *             if anything goes wrong.
     */
    public String getIndexConfiguration() throws WebserverSystemException,
        TripleStoreSystemException, EncodingSystemException {
        
        HashMap<String, HashMap<String, 
        HashMap<String, Object>>> indexConfiguration = 
                indexingHandler.getObjectTypeParameters();
        return renderer.renderIndexConfiguration(indexConfiguration);
    }

    /**
     * Provides a xml structure containing public configuration properties of
     * escidoc-core framework and the earliest creation date of Escidoc
     * repository objects.
     * 
     * @return xml structure with escidoc configuration properties
     * @throws WebserverSystemException
     *             if anything go wrong.
     * @throws TripleStoreSystemException
     *             if anything go wrong.
     * @throws EncodingSystemException
     *             if anything go wrong.
     */
    public String getRepositoryInfo() throws WebserverSystemException,
        TripleStoreSystemException, EncodingSystemException {

        TripleStoreUtility tu = TripleStoreUtility.getInstance();
        String earliestCreationDate = tu.getEarliestCreationDate();
        EscidocConfiguration config = null;
        try {
            config = EscidocConfiguration.getInstance();
        }
        catch (IOException e) {
            log.error(e);
            throw new WebserverSystemException(e);
        }

        Properties properties = new Properties();
        String gsearchUrl = config.get(EscidocConfiguration.GSEARCH_URL);

        String buildNr = config.get(EscidocConfiguration.BUILD_NUMBER);

        if (buildNr != null) {
            properties.setProperty(EscidocConfiguration.BUILD_NUMBER, buildNr);
        }
        if (gsearchUrl != null) {
            properties
                .setProperty(EscidocConfiguration.GSEARCH_URL, gsearchUrl);
        }
        String baseUrl = config.get(EscidocConfiguration.ESCIDOC_CORE_BASEURL);
        if (baseUrl != null) {
            properties.setProperty(EscidocConfiguration.ESCIDOC_CORE_BASEURL,
                baseUrl);
        }
        String name = config.get(EscidocConfiguration.ESCIDOC_REPOSITORY_NAME);
        if (name != null) {
            properties.setProperty(
                EscidocConfiguration.ESCIDOC_REPOSITORY_NAME, name);
        }
        String email = config.get(EscidocConfiguration.ADMIN_EMAIL);
        if (email != null) {
            properties.setProperty(EscidocConfiguration.ADMIN_EMAIL, email);
        }
        properties.setProperty("escidoc-core.earliest-date",
            earliestCreationDate);
        properties.setProperty("escidoc-core.database.version", frameworkInfo
            .getVersion().toString());
        try {
            properties.setProperty("escidoc-core.database.consistent", String
                .valueOf(frameworkInfo.isConsistent()));
        }
        catch (Exception e) {
            log.error(e);
            throw new WebserverSystemException(e);
        }

        // add namespace of important schemas
        properties.putAll(schemaNamespaces());

        String checksumAlgorithm =
            config
                .get(EscidocConfiguration.ESCIDOC_CORE_OM_CONTENT_CHECKSUM_ALGORITHM);
        if (checksumAlgorithm != null) {
            properties
                .setProperty(
                    EscidocConfiguration.ESCIDOC_CORE_OM_CONTENT_CHECKSUM_ALGORITHM,
                    checksumAlgorithm);
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            properties.storeToXML(os, null);
        }
        catch (IOException e) {
            log.error(e);
            throw new WebserverSystemException(e);
        }
        String propertiesXml = null;
        try {
            propertiesXml = os.toString(XmlUtility.CHARACTER_ENCODING);
        }
        catch (UnsupportedEncodingException e) {
            log.error(e);
            throw new EncodingSystemException(e);
        }
        return propertiesXml;
    }


    /**
     * Namespace of (important) schemas.
     * 
     * @return Properties with name and Namespace URI of important eSciDoc
     *         schemas
     */
    private Properties schemaNamespaces() {

        final Properties p = new Properties();

        p.setProperty("item", Constants.ITEM_NAMESPACE_URI);
        p.setProperty("container", Constants.CONTAINER_NAMESPACE_URI);
        p.setProperty("organizational-unit",
            Constants.ORGANIZATIONAL_UNIT_NAMESPACE_URI);
        p.setProperty("context", Constants.CONTEXT_NAMESPACE_URI);
        p.setProperty("user-account", Constants.USER_ACCOUNT_NS_URI);

        return p;
    }

    /**
     * Loads an set of examples objects into the framework.
     * 
     * @param type
     *            Specifies the type of example set which is to load.
     * 
     * @return some useful information
     * @throws SystemException
     *             Thrown if a framework internal error occurs.
     * @throws InvalidSearchQueryException
     *             thrown if a given search query could not be translated into a
     *             SQL query
     */
    public String loadExamples(final String type)
        throws InvalidSearchQueryException, SystemException {
        StringBuffer result = new StringBuffer();

        // select example package
        if (!type.equals("common")) {
            throw new SystemException("Example set '" + type
                + "' not supported.");
        }

       
        try {
            String selfUrl =
                EscidocConfiguration.getInstance().get(
                    EscidocConfiguration.ESCIDOC_CORE_SELFURL);

            if (!selfUrl.endsWith("/")) {
                selfUrl += "/";
            }
            Examples examples =
                new Examples(selfUrl + "examples/escidoc/");
            result.append(examples.load());
        }
        catch (Exception e) {
            throw new SystemException(e);
        }

        return getUtility()
            .prepareReturnXml((DateTime) null, result.toString());
    }

    /**
     * Ingest the FrameworkInfo object.
     * 
     * @param frameworkInfo
     *            FrameworkInfo object to be ingested
     * 
     * @spring.property ref="admin.FrameworkInfo"
     */
    public void setFrameworkInfo(final FrameworkInfo frameworkInfo) {
        this.frameworkInfo = frameworkInfo;
    }

    /**
     * Ingest the recacher object.
     * 
     * @param recacher
     *            recacher object to be ingested
     * 
     * @spring.property ref="admin.Recacher"
     */
    public void setRecacher(final RecacherInterface recacher) {
        this.recacher = recacher;
    }

    /**
     * Ingest the reindexer object.
     * 
     * @param reindexer
     *            reindexer object to be ingested
     * 
     * @spring.property ref="admin.Reindexer"
     */
    public void setReindexer(final Reindexer reindexer) {
        this.reindexer = reindexer;
    }

    /**
     * Setting the indexingHandler.
     * 
     * @param indexingHandler
     *            The indexingHandler to set.
     * @spring.property ref="common.business.indexing.IndexingHandler"
     */
    public final void setIndexingHandler(
            final IndexingHandler indexingHandler) {
        this.indexingHandler = indexingHandler;
    }

    /**
     * Injects the renderer.
     * 
     * @param renderer
     *            The renderer to inject.
     * 
     * @spring.property ref="eSciDoc.core.adm.business.renderer.VelocityXmlAdminRenderer"
     */
    public void setRenderer(final AdminRendererInterface renderer) {
        this.renderer = renderer;
    }

    /**
     * Sets the {@link PurgeService}.
     *
      * @param purgeService the {@link PurgeService}
     */
    public void setPurgeService(final PurgeService purgeService) {
        this.purgeService = purgeService;
    }
}
