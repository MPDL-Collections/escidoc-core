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
package de.escidoc.core.test.om.ingest;

import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.w3c.dom.Document;

import de.escidoc.core.test.sb.SearchTestBase;

/**
 * Test ingesting resource via ingest interface.<br>
 * By default, the tests are executed using a depositor user.
 * 
 * @author Steffen Wagner, KST
 */
public class IngestOrganizationalUnitIT extends IngestTestBase {

    private static final Pattern OBJECT_PATTERN =
        Pattern.compile("<objid resourceType=\"([^\"][^\"]*)\">(escidoc:\\d+)</objid>", Pattern.MULTILINE);

    /**
     * Test if a valid ou gets ingested. The return value must be a xml fragment containing the object id and conforming
     * the the result.xsd schema.
     * 
     * @throws Exception
     *             Throws Exception if test failes.
     */
    @Test
    public void ingestOuValid() throws Exception {
        Document ou = getTemplateAsDocument(TEMPLATE_ORGANIZATIONAL_UNIT_PATH, "escidoc_ou_create.xml");

        setUniqueValue(ou, XPATH_ORGANIZATIONAL_UNIT_TITLE);
        String toBeCreatedXml = toString(ou, false);
        assertXmlValidOrganizationalUnit(toBeCreatedXml);

        String createdXml = ingest(toBeCreatedXml);
        // Document is well formed and valid
        assertXmlValidResult(createdXml);

        Matcher matcher = OBJECT_PATTERN.matcher(createdXml);

        if (matcher.find()) {
            String resourceType = matcher.group(1);
            String objectId = matcher.group(2);

            //check if object is indexed
            assertIndexed(SearchTestBase.OU_ADMIN_INDEX_NAME, objectId);

            // Have we just ingested an item ?
            assert (resourceType.equals("OU"));

            // We can't assume anything about the object's id except not being
            // null, can we ?
            assert (objectId != null);
        }
        else {
            fail("no match for organizational unit found, return value "
                + "of ingest could not be matched successfully.");
        }
    }

}
