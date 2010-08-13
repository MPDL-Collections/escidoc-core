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
package de.escidoc.core.test.sm;

import org.apache.commons.httpclient.HttpMethod;

/**
 * Base class for report tests.
 * 
 * @author MIH
 * 
 */
public class ReportTestBase extends SmTestBase {

    /**
     * @param transport
     *            The transport identifier.
     */
    public ReportTestBase(final int transport) {
        super(transport);
    }

    /**
     * Set up servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    protected void setUp() throws Exception {

        super.setUp();
    }

    /**
     * Clean up after servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    protected void tearDown() throws Exception {

        super.tearDown();
    }

    /**
     * Test retrieving an report from the mock framework.
     * 
     * @param xml
     *            Treport-parameters xml.
     * @return The retrieved report.
     * @throws Exception
     *             If anything fails.
     */
    public String retrieve(final String xml) throws Exception {

        Object result = getReportClient().retrieve(xml);
        String xmlResult = null;
        if (result instanceof HttpMethod) {
            HttpMethod method = (HttpMethod) result;
            assertHttpStatusOfMethod("", method);
            xmlResult = getResponseBodyAsUTF8(method);
            method.releaseConnection();
        }
        else if (result instanceof String) {
            xmlResult = (String) result;
        }
        return xmlResult;
    }

}
