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
package de.escidoc.core.test.om.contentRelation;

import de.escidoc.core.common.exceptions.remote.application.notfound.ContentRelationNotFoundException;
import de.escidoc.core.test.EscidocRestSoapTestsBase;

/**
 * Test content relation delete implementation.
 * 
 * @author SWA
 * 
 */
public class ContentRelationDeleteTest extends ContentRelationTestBase {

    /**
     * @param transport
     *            The transport identifier.
     */
    public ContentRelationDeleteTest(final int transport) {
        super(transport);
    }

    /**
     * Set up servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Clean up after servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test deleting content relation.
     * 
     * @throws Exception
     *             Thrown if deleting failed.
     */
    public void testDelete01() throws Exception {

        String contentRelationXml =
            getExampleTemplate("content-relation-01.xml");
        String xml = create(contentRelationXml);
        String relationId = getObjidValue(xml);

        delete(relationId);

        try {
            retrieve(relationId);
            fail("Content Relation wasn't deleted.");
        }
        catch (Exception e) {
            Class<?> ec = ContentRelationNotFoundException.class;
            EscidocRestSoapTestsBase.assertExceptionType(ec.getName()
                + " expected.", ec, e);
        }
    }
}