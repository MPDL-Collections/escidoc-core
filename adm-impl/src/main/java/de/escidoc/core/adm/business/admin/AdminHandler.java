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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import de.escidoc.core.adm.business.renderer.interfaces.AdminRendererInterface;
import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.business.fedora.Utility;
import de.escidoc.core.common.business.fedora.resources.ResourceType;
import de.escidoc.core.common.business.indexing.IndexingHandler;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.system.EncodingSystemException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.exceptions.system.TripleStoreSystemException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.configuration.EscidocConfiguration;
import de.escidoc.core.common.util.stax.handler.TaskParamHandler;
import de.escidoc.core.common.util.xml.XmlUtility;
import de.escidoc.core.purge.PurgeRequest;
import de.escidoc.core.purge.PurgeRequestBuilder;
import de.escidoc.core.purge.PurgeService;

/**
 * Administration tool that rebuilds the search index, rebuilds the resource cache and deletes objects physically from
 * the repository.
 * 
 * @author André Schenk
 */
@Service("business.AdminHandler")
public class AdminHandler {

    @Autowired
    @Qualifier("admin.Examples")
    private Examples examples;

    @Autowired
    @Qualifier("admin.FrameworkInfo")
    private FrameworkInfo frameworkInfo;

    @Autowired
    @Qualifier("admin.PurgeStatus")
    private PurgeStatus purgeStatus;

    @Autowired
    @Qualifier("admin.Reindexer")
    private Reindexer reindexer;

    @Autowired
    @Qualifier("admin.ReindexStatus")
    private ReindexStatus reindexStatus;

    @Autowired
    @Qualifier("common.business.indexing.IndexingHandler")
    private IndexingHandler indexingHandler;

    @Autowired
    @Qualifier("eSciDoc.core.adm.business.renderer.VelocityXmlAdminRenderer")
    private AdminRendererInterface renderer;

    @Autowired
    @Qualifier("de.escidoc.core.purge.PurgeService")
    private PurgeService purgeService;

    @Autowired
    @Qualifier("business.TripleStoreUtility")
    private TripleStoreUtility tripleStoreUtility;

    @Autowired
    @Qualifier("business.Utility")
    private Utility utility;

    /**
     * Protected constructor to prevent instantiation outside of the Spring-context.
     */
    protected AdminHandler() {
    }

    /**
     * Delete a list of objects given by their object id's from Fedora. In case of items this method will also delete
     * all depending components of the given items. The deletion runs synchronously and returns some useful information
     * for the user, e.g. the total number of objects deleted.
     * 
     * @param taskParam
     *            list of object id's to be deleted boolean value to signal if the search index and the resource cache
     *            have to be kept in sync. If this value is set to false then the re-indexing and re-caching should be
     *            run manually afterwards.
     * @return total number of objects deleted, ...
     * @throws SystemException
     *             thrown in case of an internal error
     * @throws de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException
     */
    public String deleteObjects(final String taskParam) throws SystemException, XmlCorruptedException {
        final StringBuilder result = new StringBuilder();

        if (purgeStatus.startMethod()) {
            final TaskParamHandler taskParameter = XmlUtility.parseTaskParam(taskParam, false);

            try {
                for (final String id : taskParameter.getIds()) {
                    final PurgeRequest purgeRequest =
                        PurgeRequestBuilder.createPurgeRequest().withResourceId(id).build();
                    this.purgeService.purge(purgeRequest);
                    if (taskParameter.getKeepInSync()) {
                        // synchronize search index
                        reindexer.sendDeleteObjectMessage(id, true);
                    }
                    purgeStatus.inc();
                }
                result.append("<message>\n");
                result.append("scheduling ").append(taskParameter.getIds().size()).append(
                    " objects(s) for deletion from Fedora\n");
                result.append("</message>\n");
                if (taskParameter.getKeepInSync()) {
                    result.append("<message>\n");
                    result.append("scheduling ").append(taskParameter.getIds().size()).append(
                        " object(s) for deletion from search index\n");
                    result.append("</message>\n");
                }
            }
            finally {
                if (taskParameter.getIds().isEmpty()) {
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
        return this.utility.prepareReturnXml(result.toString());
    }

    /**
     * Get the current status of the running/finished purging process.
     * 
     * @return current status (how many objects are still in the queue)
     * @throws SystemException
     *             thrown in case of an internal error
     */
    public String getPurgeStatus() {
        return this.utility.prepareReturnXml(purgeStatus.toString());
    }

    /**
     * Get the current status of the running/finished reindexing process.
     * 
     * @return current status (how many objects are still in the queue)
     * @throws SystemException
     *             thrown in case of an internal error
     */
    public String getReindexStatus() {
        return this.utility.prepareReturnXml(reindexer.getStatus());
    }

    /**
     * decrease the type of the current status of the running reindexing process by 1.
     * 
     * @param objectType
     *            object-type to decrease
     */
    public void decreaseReindexStatus(final String objectType) {
        if (objectType != null) {
            reindexStatus.dec(ResourceType.getResourceTypeFromUri(objectType));
        }
    }

    /**
     * Reinitialize the search index. The initialization runs synchronously and returns some useful information for the
     * user, e.g. the total number of objects found.
     * 
     * @param clearIndex
     *            clear the index before adding objects to it
     * @param indexNamePrefix
     *            name of the index (may be null for "all indexes")
     * @return total number of objects found, ...
     * @throws SystemException
     *             Thrown if a framework internal error occurs.
     */
    @Deprecated
    public String reindex(final boolean clearIndex, final String indexNamePrefix) throws SystemException {
        return this.utility.prepareReturnXml(reindexer.reindex(clearIndex, true, indexNamePrefix));
    }

    /**
     * Reinitialize the search index. The initialization runs synchronously and returns some useful information for the
     * user, e.g. the total number of objects found.
     * 
     * @param taskParam
     *            containing following parameters: clearIndex (True/false), commmitWrites(true/false), indexName (all for all).
     * @return total number of objects found, ...
     * @throws SystemException
     *             Thrown if a framework internal error occurs.
     */
    public String reindex(final String taskParam) throws SystemException, XmlCorruptedException {
        final TaskParamHandler taskParameter = XmlUtility.parseTaskParam(taskParam, false);

        return this.utility.prepareReturnXml(reindexer.reindex(taskParameter.getClearIndex(), taskParameter
            .getCommitWrites(), taskParameter.getIndexName()));
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
    public String getIndexConfiguration() throws WebserverSystemException {

        final Map<String, Map<String, Map<String, Object>>> indexConfiguration =
            indexingHandler.getObjectTypeParameters();
        return renderer.renderIndexConfiguration(indexConfiguration);
    }

    /**
     * Provides a xml structure containing public configuration properties of escidoc-core framework and the earliest
     * creation date of Escidoc repository objects.
     * 
     * @return xml structure with escidoc configuration properties
     * @throws WebserverSystemException
     *             if anything go wrong.
     * @throws TripleStoreSystemException
     *             if anything go wrong.
     * @throws EncodingSystemException
     *             if anything go wrong.
     */
    public String getRepositoryInfo() throws WebserverSystemException, TripleStoreSystemException,
        EncodingSystemException {
        return getRepositoryInfo(null);
    }

    /**
     * Provides a xml structure containing public configuration properties of escidoc-core framework and the earliest
     * creation date of Escidoc repository objects.
     * If parameter "key" is provided, only property with given key is returned.
     * Supported keys are:
     * gsearch.url
     * escidoc-core.build
     * escidoc-core.baseurl
     * escidoc-core.repository-name
     * escidoc-core.admin-email
     * escidoc-core.earliest-date
     * escidoc-core.database.version
     * escidoc-core.database.consistent
     * escidoc-core.om.content.checksum-algorithm
     * 
     * @param key property-key
     * @return xml structure with escidoc configuration properties
     * @throws WebserverSystemException
     *             if anything go wrong.
     * @throws TripleStoreSystemException
     *             if anything go wrong.
     * @throws EncodingSystemException
     *             if anything go wrong.
     */
    public String getRepositoryInfo(final String key) throws WebserverSystemException, TripleStoreSystemException,
        EncodingSystemException {

        final EscidocConfiguration config;
        config = EscidocConfiguration.getInstance();

        final Properties properties = new Properties();

        if (key == null || key.equals(EscidocConfiguration.GSEARCH_URL)) {
            final String gsearchUrl = config.get(EscidocConfiguration.GSEARCH_URL);
            if (gsearchUrl != null) {
                properties.setProperty(EscidocConfiguration.GSEARCH_URL, gsearchUrl);
            }
        }

        if (key == null || key.equals(EscidocConfiguration.BUILD_NUMBER)) {
            final String buildNr = config.get(EscidocConfiguration.BUILD_NUMBER);
            if (buildNr != null) {
                properties.setProperty(EscidocConfiguration.BUILD_NUMBER, buildNr);
            }
        }

        if (key == null || key.equals(EscidocConfiguration.ESCIDOC_CORE_BASEURL)) {
            final String baseUrl = config.get(EscidocConfiguration.ESCIDOC_CORE_BASEURL);
            if (baseUrl != null) {
                properties.setProperty(EscidocConfiguration.ESCIDOC_CORE_BASEURL, baseUrl);
            }
        }

        if (key == null || key.equals(EscidocConfiguration.ESCIDOC_REPOSITORY_NAME)) {
            final String name = config.get(EscidocConfiguration.ESCIDOC_REPOSITORY_NAME);
            if (name != null) {
                properties.setProperty(EscidocConfiguration.ESCIDOC_REPOSITORY_NAME, name);
            }
        }

        if (key == null || key.equals(EscidocConfiguration.ADMIN_EMAIL)) {
            final String email = config.get(EscidocConfiguration.ADMIN_EMAIL);
            if (email != null) {
                properties.setProperty(EscidocConfiguration.ADMIN_EMAIL, email);
            }
        }

        if (key == null || key.equals("escidoc-core.earliest-date")) {
            // handle case where nothing is in the repository 
            final String earliestCreationDate = tripleStoreUtility.getEarliestCreationDate();
            if (earliestCreationDate != null) {
                properties.setProperty("escidoc-core.earliest-date", earliestCreationDate);
            }
            else {
                properties.setProperty("escidoc-core.earliest-date", "n/o");
            }
        }

        if (key == null || key.equals("escidoc-core.database.version")) {
            properties.setProperty("escidoc-core.database.version", frameworkInfo.getVersion().toString());
        }

        if (key == null || key.equals("escidoc-core.database.consistent")) {
            try {
                properties
                    .setProperty("escidoc-core.database.consistent", String.valueOf(frameworkInfo.isConsistent()));
            }
            catch (final Exception e) {
                throw new WebserverSystemException(e);
            }
        }

        if (key == null || key.equals(EscidocConfiguration.ESCIDOC_CORE_OM_CONTENT_CHECKSUM_ALGORITHM)) {
            final String checksumAlgorithm =
                config.get(EscidocConfiguration.ESCIDOC_CORE_OM_CONTENT_CHECKSUM_ALGORITHM);
            if (checksumAlgorithm != null) {
                properties.setProperty(EscidocConfiguration.ESCIDOC_CORE_OM_CONTENT_CHECKSUM_ALGORITHM,
                    checksumAlgorithm);
            }
        }

        // add namespace of important schemas
        if (key == null || key.equals("item")) {
            properties.setProperty("item", Constants.ITEM_NAMESPACE_URI);
        }
        if (key == null || key.equals("container")) {
            properties.setProperty("container", Constants.CONTAINER_NAMESPACE_URI);
        }
        if (key == null || key.equals("organizational-unit")) {
            properties.setProperty("organizational-unit", Constants.ORGANIZATIONAL_UNIT_NAMESPACE_URI);
        }
        if (key == null || key.equals("context")) {
            properties.setProperty("context", Constants.CONTEXT_NAMESPACE_URI);
        }
        if (key == null || key.equals("user-account")) {
            properties.setProperty("user-account", Constants.USER_ACCOUNT_NS_URI);
        }

        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            properties.storeToXML(os, null);
        }
        catch (final IOException e) {
            throw new WebserverSystemException(e);
        }
        final String propertiesXml;
        try {
            propertiesXml = os.toString(XmlUtility.CHARACTER_ENCODING);
        }
        catch (final UnsupportedEncodingException e) {
            throw new EncodingSystemException(e);
        }
        return propertiesXml;
    }

    /**
     * Loads an set of examples objects into the framework. The example set is located in the
     * <tt>ESCIDOC_HOME/conf/examples</tt> directory. The common example set is located in the
     * <tt>ESCIDOC_HOME/conf/examples/common</tt> directory. You may add other example sets by creating a new directory
     * next to the <tt>common</tt> directory and use its name when calling this method to load this example set.<br>
     * <br>
     * Note, that all other example sets have to be exactly like the <tt>common</tt> example set and that their
     * resources may change their state to <tt>released</tt> after being loaded by this method. Therefore you may only
     * change the content of the resources but not their relations towards each other.
     * 
     * @param type
     *            Specifies the type of example set to load.
     * @return some useful information
     * @throws SystemException
     *             Thrown if a framework internal error occurs.
     */
    public String loadExamples(final String type) throws SystemException {
        final StringBuilder result = new StringBuilder();

        final String path = EscidocConfiguration.getEscidocHome();
        final File location = new File(path + "/conf/examples/" + type);

        if (!location.exists()) {
            throw new SystemException("Example set '" + type + "' not supported.");
        }

        try {
            result.append(examples.load(location.getAbsolutePath() + "/"));
        }
        catch (final Exception e) {
            throw new SystemException(e);
        }

        return this.utility.prepareReturnXml(result.toString());
    }
}
