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
package de.escidoc.core.test.om.item;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import de.escidoc.core.common.exceptions.remote.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.remote.application.notfound.ComponentNotFoundException;
import de.escidoc.core.common.exceptions.remote.application.notfound.ItemNotFoundException;
import de.escidoc.core.test.EscidocRestSoapTestsBase;
import de.escidoc.core.test.common.client.servlet.Constants;

/**
 * 
 * @author MSC
 * 
 */
public class ItemRetrieveComponentPropertiesTest extends ItemTestBase {

    private static String itemId = null;

    private static String itemXml = null;

    private static Document createdItem = null;

    private static String componentId = null;

    private static int componentNo = 2;

    /**
     * @param transport
     *            The transport identifier.
     */
    public ItemRetrieveComponentPropertiesTest(final int transport) {
        super(transport);
    }

    /**
     * Set up servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void setUp() throws Exception {

        super.setUp();
        if (itemId == null) {
            itemXml =
                EscidocRestSoapTestsBase.getTemplateAsString(TEMPLATE_ITEM_PATH
                    + "/" + getTransport(false),
                    "escidoc_item_198_for_create.xml");
            createdItem = EscidocRestSoapTestsBase.getDocument(create(itemXml));
            itemId = getObjidValue(createdItem);
            componentNo = 1;
            componentId =
                getObjidValue(getTransport(), createdItem,
                    "/item/components/component[1]");
            // getComponentObjidValue(createdItem, 1);
            Node node =
                selectSingleNode(createdItem,
                    "/item/components/component[1]/properties/description");
            if (node == null) {
                componentNo = 2;
            }
        }
    }

    /**
     * Clean up after servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void tearDown() throws Exception {

        super.tearDown();
    }

    /**
     * Test successfully retrieving the properties of a component of an item.
     * 
     * @test.status Implemented
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testOMRCP1() throws Exception {
        // Node description =
        // selectSingleNode(EscidocRestSoapTestsBase.getDocument(itemXml),
        // "/item/components/component/md-records/md-record[@name='escidoc']//description");
        // if (description != null) {
        // System.out.println("description " + description.getTextContent());
        // }
        componentId =
            getObjidValue(
                getTransport(),
                createdItem,
                "/item/components/component[md-records/md-record[@name='escidoc']//description]");

        String properties = retrieveComponentProperties(itemId, componentId);

        Node escidocMdRecordWithDescription =
            selectSingleNode(
                EscidocRestSoapTestsBase.getDocument(itemXml),
                "/item/components/component/md-records/md-record[@name='escidoc' and *//description]");
        String escidocMdRecordWithDescriptionTemplate =
            toString(escidocMdRecordWithDescription, false);

        String templateProperties =
            toString(
                selectSingleNode(
                    EscidocRestSoapTestsBase.getDocument(itemXml),
                    "/item/components/component[md-records/md-record[@name='escidoc']//description]/properties"),
                true);
        assertComponentProperties(properties, templateProperties,
            escidocMdRecordWithDescriptionTemplate, "/ir/item/" + itemId
                + "/components/component/" + componentId + "/properties",
            getLastModificationDateValue(createdItem), startTimestamp);

        componentId =
            getObjidValue(
                getTransport(),
                createdItem,
                "/item/components/component[not(md-records/md-record[@name='escidoc']//description)]");

        properties = retrieveComponentProperties(itemId, componentId);

        Node escidocMdRecordWithoutDescription =
            selectSingleNode(
                EscidocRestSoapTestsBase.getDocument(itemXml),
                "/item/components/component/md-records/md-record[@name='escidoc' and not(*//description)]");
        String escidocMdRecordWithoutDescriptionTemplate =
            toString(escidocMdRecordWithoutDescription, false);
        assertComponentProperties(
            properties,
            toString(
                selectSingleNode(
                    EscidocRestSoapTestsBase.getDocument(itemXml),
                    "/item/components/component[not(md-records/md-record[@name='escidoc']//description)]/properties"),
                true), escidocMdRecordWithoutDescriptionTemplate, "/ir/item/"
                + itemId + "/components/component/" + componentId
                + "/properties", getLastModificationDateValue(createdItem),
            startTimestamp);

    }

    /**
     * Test retrieving the properties of a component of an unknown item.
     * 
     * @test.status Implemented
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testOMRCP2a() throws Exception {
        Class<?> ec = ItemNotFoundException.class;
        try {
            retrieveComponentProperties("unknown", componentId);
            fail(ec + " expected but no error occurred!");
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec.getName()
                + " expected.", ec, e);
        }

    }

    /**
     * Test retrieving the properties of an unknown component of an item.
     * 
     * @test.status Implemented
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testOMRCP2b() throws Exception {
        Class<?> ec = ComponentNotFoundException.class;
        try {
            retrieveComponentProperties(itemId, "unknown");
            fail(ec + " expected but no error occurred!");
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec.getName()
                + " expected.", ec, e);
        }

    }

    /**
     * Test retrieving the properties the properties of a component of an item
     * with missing item id.
     * 
     * @test.status Implemented
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testOMRCP3a() throws Exception {
        Class<?> ec = MissingMethodParameterException.class;
        try {
            retrieveComponentProperties(null, componentId);
            fail(ec + " expected but no error occurred!");
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec.getName()
                + " expected.", ec, e);
        }

    }

    /**
     * Test retrieving the properties the properties of a component of an item
     * with missing component id.
     * 
     * @test.status Implemented
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testOMRCP3b() throws Exception {
        Class<?> ec = MissingMethodParameterException.class;
        try {
            retrieveComponentProperties(itemId, null);
            fail(ec + " expected but no error occurred!");
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec.getName()
                + " expected.", ec, e);
        }
    }

    /**
     * Assert the xmlComponentProperties match the expected
     * xmlTemplateComponentProperties.
     * 
     * @param xmlComponentProperties
     *            The retrieved properties.
     * @param xmlTemplateComponentProperties
     *            The expected properties.
     * @param templateComponentEscidocMdRecord
     *            TODO
     * @param expectedHRef
     *            The expected href.
     * @param expectedLastModificationTimestamp
     *            The last-modification timestamp of the item.
     * @param timestampBeforeCreation
     *            A timestamp before the creation of the item/component.
     * @throws Exception
     *             If anything fails.
     */
    private void assertComponentProperties(
        final String xmlComponentProperties,
        final String xmlTemplateComponentProperties,
        final String templateComponentEscidocMdRecord,
        final String expectedHRef,
        final String expectedLastModificationTimestamp,
        final String timestampBeforeCreation) throws Exception {

        Document createdProperties =
            EscidocRestSoapTestsBase.getDocument(xmlComponentProperties);
        if (getTransport() == Constants.TRANSPORT_REST) {
            String href = getRootElementHrefValue(createdProperties);
            if ("".equals(href)) {
                href = null;
            }
            assertNotNull(
                "Component Properties error: href attribute was not set!", href);
            assertEquals("Component Properties error: href has wrong value!",
                expectedHRef, href);
        }
        String rootLastModificationDate =
            getLastModificationDateValue(createdProperties);
        if ("".equals(rootLastModificationDate)) {
            rootLastModificationDate = null;
        }
        assertNotNull(
            "Component Properties error: last-modification-date attribute "
                + "was not set!", rootLastModificationDate);
        assertXmlExists(
            "Component Properties error: creation-date was not set!",
            createdProperties, "/properties/creation-date");
        // assertXmlExists(
        // "Component Properties error: last-modification-date was not set "
        // + "in properties!", createdProperties,
        // "/properties/last-modification-date");
        // assertXmlExists(
        // "Component Properties error: valid-status was not set!",
        // createdProperties, "/properties/valid-status");
        assertXmlExists(
            "Component Properties error: visibilty was not set in properties!",
            createdProperties, "/properties/visibility");
        assertXmlExists(
            "Component Properties error: creator was not set in properties!",
            createdProperties, "/properties/created-by");
        assertXmlExists(
            "Component Properties error: content-category was not set in "
                + "properties!", createdProperties,
            "/properties/content-category");
        // assertXmlExists(
        // "Component Properties error: file-name was not set in properties!",
        // createdProperties, "/properties/file-name");

        // assertXmlExists(
        // "Component Properties error: file-size was not set in properties!",
        // createdProperties, "/properties/file-size");

        String creationDate =
            selectSingleNode(createdProperties, "/properties/creation-date")
                .getTextContent();
        log.debug("assertTimestampAfter( " + creationDate + ", "
            + timestampBeforeCreation + ")");
        assertTimestampAfter(
            "Component Properties error: creation-date is not as expected!",
            creationDate, timestampBeforeCreation);

        assertReferencingElement("Invalid created-by. ", createdProperties,
            "/properties/created-by", Constants.USER_ACCOUNT_BASE_URI);

        Document template =
            EscidocRestSoapTestsBase
                .getDocument(xmlTemplateComponentProperties);
        Document mdRecord =
            EscidocRestSoapTestsBase
                .getDocument(templateComponentEscidocMdRecord);
        if (selectSingleNode(mdRecord, "/md-record//description") != null) {
            assertXmlExists(
                "Component Properties error: description was not set in properties!",
                createdProperties, "/properties/description");

            String decriptionInProperties =
                selectSingleNode(createdProperties, "/properties/description")
                    .getTextContent();
            String decriptionInEscidocmdRecord =
                selectSingleNode(mdRecord, "/md-record//description")
                    .getTextContent();
            assertEquals(
                "Component Properties error: description was changed!",
                decriptionInProperties, decriptionInEscidocmdRecord);
        }
        // System.out.println("md-record "+ templateComponentEscidocMdRecord);
        // System.out.println(selectSingleNode(mdRecord,
        // "/md-record//title").getTextContent());
        if (selectSingleNode(mdRecord, "/md-record//title") != null) {
            assertXmlExists(
                "Component Properties error: 'file-name' was not set in properties!",
                createdProperties, "/properties/file-name");

            String fileNameInProperties =
                selectSingleNode(createdProperties, "/properties/file-name")
                    .getTextContent();
            String fileNameInEscidocmdRecord =
                selectSingleNode(mdRecord, "/md-record//title")
                    .getTextContent();
            assertEquals(
                "Component Properties error: description was changed!",
                fileNameInProperties, fileNameInEscidocmdRecord);
        }
        // if (selectSingleNode(template, "/properties/locator-url") != null) {
        // assertXmlExists(
        // "Component Properties error: locator-url was not set in properties!",
        // createdProperties, "/properties/locator-url");
        // assertXmlEquals(
        // "Component Properties error: locator-url was changed!",
        // template, createdProperties, "/properties/locator-url");
        // }
        assertXmlEquals("Component Properties error: status was changed!",
            template, createdProperties, "/properties/status");
        assertXmlEquals("Component Properties error: visibility was changed!",
            template, createdProperties, "/properties/visibility");

        assertXmlEquals(
            "Component Properties error: content-category was changed!",
            template, createdProperties, "/properties/content-category");
        // assertXmlEquals("Component Properties error: file-name was changed!",
        // template, createdProperties, "/properties/file-name");

        if (selectSingleNode(template, "/properties/mime-type") != null) {
            assertXmlExists(
                "Component Properties error: mime-type was not set in properties!",
                createdProperties, "/properties/mime-type");
            assertXmlEquals(
                "Component Properties error: mime-type was changed!", template,
                createdProperties, "/properties/mime-type");
        }
        // assertXmlEquals("Component Properties error: file-size was changed!",
        // template, createdProperties, "/properties/file-size");
    }
}
