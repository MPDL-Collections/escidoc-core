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
 * Copyright 2006-2008 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.  
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.test.common.client.servlet.adm;

import de.escidoc.core.test.common.client.servlet.ClientBase;
import de.escidoc.core.test.Constants;
import de.escidoc.core.test.common.client.servlet.adm.interfaces.AdminClientInterface;

/**
 * Offers access to the AdminHandler.
 *
 * @author Steffen Wagner
 */
public class AdminClient extends ClientBase implements AdminClientInterface {

    /**
     * Delete objects from repository.
     *
     * @param xml XML containing the object id's
     * @return The HttpMethod after the service call .
     * @throws Exception If the service call fails.
     */
    public Object deleteObjects(final String xml) throws Exception {
        return callEsciDoc("Admin.deleteObjects", METHOD_DELETE_OBJECTS, Constants.HTTP_METHOD_POST,
            Constants.WEB_CONTEXT_URI_ESCIDOC + Constants.DELETE_OBJECTS_BASE_URI, new String[] {}, changeToString(xml));

    }

    /**
     * Get the purge status.
     *
     * @return The HttpMethod after the service call .
     * @throws Exception If the service call fails.
     */
    public Object getPurgeStatus() throws Exception {

        return callEsciDoc("Admin.getPurgeStatus", METHOD_GET_PURGE_STATUS, Constants.HTTP_METHOD_GET,
            Constants.WEB_CONTEXT_URI_ESCIDOC + Constants.DELETE_OBJECTS_BASE_URI, new String[] {});
    }

    /**
     * Get repository information.
     *
     * @return The HttpMethod after the service call .
     * @throws Exception If the service call fails.
     */
    public Object getRepositoryInfo(final String key) throws Exception {
        if (key != null) {
            return callEsciDoc("Admin.getRepositoryInfo", METHOD_GET_REPOSITORY_INFO, Constants.HTTP_METHOD_GET,
                Constants.WEB_CONTEXT_URI_ESCIDOC + Constants.REPOSITORY_INFO_BASE_URI, new String[] { key });
        }
        else {
            return callEsciDoc("Admin.getRepositoryInfo", METHOD_GET_REPOSITORY_INFO, Constants.HTTP_METHOD_GET,
                Constants.WEB_CONTEXT_URI_ESCIDOC + Constants.REPOSITORY_INFO_BASE_URI, new String[] {});
        }
    }

    /**
     * Get repository information.
     *
     * @return The HttpMethod after the service call .
     * @throws Exception If the service call fails.
     */
    public Object getIndexConfiguration() throws Exception {

        return callEsciDoc("Admin.getIndexConfiguration", METHOD_GET_INDEX_CONFIGURATION, Constants.HTTP_METHOD_GET,
            Constants.WEB_CONTEXT_URI_ESCIDOC + Constants.INDEX_CONFIGURATION_BASE_URI, new String[] {});
    }

    /**
     * Load example objects.
     *
     * @param type examples type (only "common" allowed at the moment)
     * @return The HttpMethod after the service call .
     * @throws Exception If the service call fails.
     */
    public Object loadExamples(final String type) throws Exception {
        return callEsciDoc("Admin.loadExamples", METHOD_LOAD_EXAMPLES, Constants.HTTP_METHOD_GET,
            Constants.WEB_CONTEXT_URI_ESCIDOC + Constants.LOAD_EXAMPLES_BASE_URI, new String[] { type });
    }

    /**
     * Reindex
     *
     * @param reindexTaskParamXml reindexTaskParamXml
     * @return The HttpMethod after the service call .
     * @throws Exception If the service call fails.
     */
    public Object reindex(final Object reindexTaskParamXml) throws Exception {
        return callEsciDoc("Admin.reindex", METHOD_REINDEX, Constants.HTTP_METHOD_POST,
            Constants.WEB_CONTEXT_URI_ESCIDOC + Constants.REINDEX_BASE_URI, new String[] {},
            changeToString(reindexTaskParamXml));
    }
}
