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
 * Copyright 2008 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.  
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.test.cmm.contentmodel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import de.escidoc.core.common.exceptions.remote.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.remote.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.remote.application.notfound.ContentModelNotFoundException;
import de.escidoc.core.test.EscidocRestSoapTestsBase;

/**
 * Test the mock implementation of the item resource.
 * 
 * @author MSC
 * 
 */
public class ContentModelUpdateTest extends ContentModelTestBase {

    /**
     * @param transport
     *            The transport identifier.
     */
    public ContentModelUpdateTest(final int transport) {
        super(transport);
    }

    /**
     * Test updating a ContentModel with unchanged representation.
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testCmUpdateUnchanged() throws Exception {

        String cmXml;
        String createdXML;
        String contentModelId;
        String updatedXml;
        String lmd;
        String retrievedXML;

        // minimal Content Model
        cmXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        createdXML = create(cmXml);
        contentModelId = getObjidValue(createdXML);

        // updated with representation from create
        lmd = getLastModificationDateValue(getDocument(createdXML));
        updatedXml = update(contentModelId, createdXML);
        assertEquals("Update should not change resource.", lmd,
            getLastModificationDateValue(getDocument(updatedXml)));

        // updated with representation from retrieve
        retrievedXML = retrieve(contentModelId);
        lmd = getLastModificationDateValue(getDocument(retrievedXML));
        updatedXml = update(contentModelId, retrievedXML);
        assertEquals("Update should not change resource.", lmd,
            getLastModificationDateValue(getDocument(updatedXml)));

        // full Content Model
        cmXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-all-for-create.xml");
        createdXML = create(cmXml);
        contentModelId = getObjidValue(createdXML);

        // updated with representation from create
        lmd = getLastModificationDateValue(getDocument(createdXML));
        updatedXml = update(contentModelId, createdXML);
        assertEquals("Update should not change resource.", lmd,
            getLastModificationDateValue(getDocument(updatedXml)));

        // updated with representation from retrieve
        retrievedXML = retrieve(contentModelId);
        lmd = getLastModificationDateValue(getDocument(retrievedXML));
        updatedXml = update(contentModelId, retrievedXML);
        assertEquals("Update should not change resource.", lmd,
            getLastModificationDateValue(getDocument(updatedXml)));

    }

    /**
     * Test updating a not existing ContentModel.
     * 
     * @test.name: Updating Content Model - Unknown Id
     * @test.id: CTM_Uct_2
     * @test.input: Id that is unknown to the system.
     * @test.expected: ContentModelNotFoundException
     * @test.status Implemented
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testCtmUCt2() throws Exception {

        String cmXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        Class<?> ec = ContentModelNotFoundException.class;
        try {
            update(UNKNOWN_ID, cmXml);
            EscidocRestSoapTestsBase.failMissingException(ec);
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec, e);
        }
    }

    /**
     * Test updating a ContentModel with providing an id of an existing resource
     * of another type.
     * 
     * @test.name: Update Content Model - Wrong Id
     * @test.id: CTM_Uct_2-2
     * @test.input: Id of an existing resource of another resource type.
     * @test.expected: ContentModelNotFoundException
     * @test.status Implemented
     * 
     * @test.issues 
     *              http://www.escidoc-project.de/issueManagement/show_bug.cgi?id
     *              =294
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testCtmUCt2_2() throws Exception {

        String cmXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        Class<?> ec = ContentModelNotFoundException.class;
        try {
            update(CONTEXT_ID, cmXml);
            EscidocRestSoapTestsBase.failMissingException(ec);
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec, e);
        }
    }

    /**
     * Test updating an ContentModel without id.
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testCtmUCt3() throws Exception {

        String cmXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        Class<?> ec = MissingMethodParameterException.class;
        try {
            update(null, cmXml);
            EscidocRestSoapTestsBase.failMissingException(ec);
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec, e);
        }
    }

    /**
     * Test updating an ContentModel without xml.
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testCtmUCt3a() throws Exception {
        String cmXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        String createdXML = create(cmXml);
        String contentModelId = getObjidValue(createdXML);

        Class<?> ec = MissingMethodParameterException.class;
        try {
            update(contentModelId, null);
            EscidocRestSoapTestsBase.failMissingException(ec);
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec, e);
        }
    }

    /**
     * Test updating an ContentModel with invalid xml.
     * 
     * @throws Exception
     *             If anything fails.
     */
    public void testCtmUCt4() throws Exception {
        String cmXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        String createdXML = create(cmXml);
        String contentModelId = getObjidValue(createdXML);

        Class<?> ec = XmlSchemaValidationException.class;
        try {
            update(contentModelId, "<content-model/>");
            EscidocRestSoapTestsBase.failMissingException(ec);
        }
        catch (Exception e) {
            EscidocRestSoapTestsBase.assertExceptionType(ec, e);
        }
    }

    /**
     * Test updating //properties/name element of Content Model.
     * 
     * Content Model changed in following way:
     * <ul>
     * <li>create</li>
     * <li>update</li>
     * </ul>
     * 
     * @throws Exception
     *             If behavior or timestamps is not as expected.
     */
    public void testCtmUCt5() throws Exception {

        // version 1
        String contentModelXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        String cmV1E1 = create(contentModelXml);

        Document cmDocV1E1 = getDocument(cmV1E1);
        String objid = getObjidValue(cmV1E1);

        String newName = String.valueOf(System.nanoTime());
        Node tmpl =
            substitute(cmDocV1E1, "/content-model/properties/name", newName);

        // version 2
        String cmXmlV2E1 = update(objid, toString(tmpl, true));
        Document cmDocV2E1 = EscidocRestSoapTestsBase.getDocument(cmXmlV2E1);

        assertXmlExists("Properties element name not updated ", cmDocV2E1,
            "/content-model/properties/name[text() = '" + newName + "']");
    }

    /**
     * Test updating //properties/description element of Content Model.
     * 
     * Content Model changed in following way:
     * <ul>
     * <li>create</li>
     * <li>update</li>
     * </ul>
     * 
     * @throws Exception
     *             If behavior or timestamps is not as expected.
     */
    public void testCtmUCt6() throws Exception {

        // version 1
        String contentModelXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        String cmV1E1 = create(contentModelXml);

        Document cmDocV1E1 = getDocument(cmV1E1);
        String objid = getObjidValue(cmV1E1);

        String newName = String.valueOf(System.nanoTime());
        Node tmpl =
            substitute(cmDocV1E1, "/content-model/properties/description",
                newName);

        // version 2
        String cmXmlV2E1 = update(objid, toString(tmpl, true));
        Document cmDocV2E1 = EscidocRestSoapTestsBase.getDocument(cmXmlV2E1);

        assertXmlExists("Properties element name not updated ", cmDocV2E1,
            "/content-model/properties/description[text() = '" + newName + "']");
    }

    /**
     * Test update of Content Model by adding a meta data definition.
     * 
     * Content Model changed in following way:
     * <ul>
     * <li>create</li>
     * <li>update</li>
     * </ul>
     * 
     * @throws Exception
     *             If behavior or timestamps is not as expected.
     */
    public void testCtmUCt7() throws Exception {

        // version 1
        String contentModelXml =
            EscidocRestSoapTestsBase.getTemplateAsString(
                TEMPLATE_CONTENT_MODEL_PATH + "/" + getTransport(false),
                "content-model-minimal-for-create.xml");
        String cmV1E1 = create(contentModelXml);

        Document cmDocV1E1 = getDocument(cmV1E1);
        String objid = getObjidValue(cmV1E1);

        // create MdRecord
        Element mdRecord =
            cmDocV1E1.createElementNS(
                "http://www.escidoc.de/schemas/contentmodel/0.1",
                "escidocContentModel:md-record-definition");
        mdRecord.setAttribute("name", "test_definition");
        Element mdRecordContent =
            cmDocV1E1.createElementNS(
                "http://www.escidoc.de/schemas/contentmodel/0.1",
                "escidocContentModel:schema");
        mdRecordContent
            .setAttributeNS(
                "http://www.w3.org/1999/xlink",
                "xlink:href",
                "http://localhost:8080/xsd/soap/organizational-unit/0.7/organizational-unit.xsd");
        mdRecord.appendChild(mdRecordContent);

        // create MdRecords
        Element mdRecords =
            cmDocV1E1.createElementNS(
                "http://www.escidoc.de/schemas/contentmodel/0.1",
                "escidocContentModel:md-record-definitions");
        mdRecords.appendChild(mdRecord);

        Node resources =
            selectSingleNode(cmDocV1E1, "/content-model/resources");

        selectSingleNode(cmDocV1E1, "/content-model").insertBefore(mdRecords,
            resources);

        String cmWithMdRecordXml = toString(cmDocV1E1, true);
        cmWithMdRecordXml =
            cmWithMdRecordXml.replaceFirst(":md-record-definitions",
                ":md-record-definitions xmlns:xlink=\"http://www.w3.org/1999/xlink\"");

        // version 2
        String cmXmlV2E1 = update(objid, cmWithMdRecordXml);
        Document cmDocV2E1 = EscidocRestSoapTestsBase.getDocument(cmXmlV2E1);

    }

}
