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

import de.escidoc.core.common.exceptions.remote.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.remote.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.remote.application.notfound.ContentRelationNotFoundException;
import de.escidoc.core.common.exceptions.remote.application.notfound.ItemNotFoundException;
import de.escidoc.core.common.exceptions.remote.application.notfound.ReferencedResourceNotFoundException;
import de.escidoc.core.common.exceptions.remote.application.notfound.RelationPredicateNotFoundException;
import de.escidoc.core.common.exceptions.remote.application.violated.AlreadyExistsException;
import de.escidoc.core.test.EscidocAbstractTest;
import de.escidoc.core.test.TaskParamFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test the mock implementation of the item resource.
 * 
 * @author Michael Schneider
 */
public class ItemContentRelationsIT extends ItemTestBase {

    private String itemId = null;

    private String itemXml = null;

    /**
     * Set up servlet test.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Before
    public void setUp() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        itemXml = create(itemWithoutComponents);
        this.itemId = getObjidValue(itemXml);

        // Node itemObjiId = selectSingleNode(getDocument(itemXml),
        // "/item/@objid");
        // String itemId = itemObjiId.getTextContent();
        // this.itemId = itemId;
    }

    @Test
    public void testIssueInfr1007() throws Exception {
        addRelation(itemId, TaskParamFactory.ONTOLOGY_IS_REVISION_OF);
        addRelation(itemId, "http://escidoc.org/examples/test1");
        addRelation(itemId, "http://escidoc.org/examples/#test2");

        String relationsElementXml = retrieveRelations(this.itemId);
        Document relationsElementDocument = EscidocAbstractTest.getDocument(relationsElementXml);
        selectSingleNodeAsserted(relationsElementDocument, "/relations");
        selectSingleNodeAsserted(relationsElementDocument, "/relations/relation[@predicate = '"
            + TaskParamFactory.ONTOLOGY_IS_REVISION_OF + "']");
        selectSingleNodeAsserted(relationsElementDocument,
            "/relations/relation[@predicate = 'http://escidoc.org/examples/test1']");
        selectSingleNodeAsserted(relationsElementDocument,
            "/relations/relation[@predicate = 'http://escidoc.org/examples/#test2']");
        assertXmlValidItem(relationsElementXml);
    }

    /**
     * Tets successfully adding a new relation to the item.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testAddRelation() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String targetId1 = getObjidValue(create(itemWithoutComponents));
        String targetId2 = getObjidValue(create(itemWithoutComponents));

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(2);
        relations.add(new TaskParamFactory.Relation(targetId1, null));
        relations.add(new TaskParamFactory.Relation(targetId2, null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam = getRelationTaskParamWithUmlaut(relations, lastModDate);
        addContentRelations(this.itemId, taskParam);
        String itemWithRelations = retrieve(this.itemId);
        assertXmlValidItem(itemWithRelations);
        Document itemWithRelationsDocument = EscidocAbstractTest.getDocument(itemWithRelations);

        NodeList relationTargets = selectNodeList(itemWithRelationsDocument, "/item/relations/relation/@href");
        boolean contains1 = false;
        boolean contains2 = false;

        for (int i = relationTargets.getLength() - 1; i >= 0; i--) {
            String id = relationTargets.item(i).getNodeValue();
            if (id.matches(".*" + targetId1 + "$")) {
                contains1 = true;
            }
            if (id.matches(".*" + targetId2 + "$")) {
                contains2 = true;
            }

        }

        assertTrue("added relation targetId1 is not container the relation list ", contains1);
        assertTrue("added relation targetId2 is not container the relation list ", contains2);

        // and retrieve relations only and check
        String relationsElementXml = retrieveRelations(this.itemId);
        selectSingleNodeAsserted(EscidocAbstractTest.getDocument(relationsElementXml), "/relations");
        assertXmlValidRelations(relationsElementXml);

        NodeList relationsNode =
            selectNodeList(EscidocAbstractTest.getDocument(itemWithRelations), "/item/relations/relation");
        assertEquals("Number of relations is wrong ", relationsNode.getLength(), 2);
    }

    /**
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = MissingMethodParameterException.class)
    public void testAddRelationWithoutId() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String xml1 = create(itemWithoutComponents);
        String xml2 = create(itemWithoutComponents);
        String targetId1 = getObjidValue(xml1);
        String targetId2 = getObjidValue(xml2);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(1);
        relations.add(new TaskParamFactory.Relation(targetId1, null));
        relations.add(new TaskParamFactory.Relation(targetId2, null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        addContentRelations(null, taskParam);
    }

    /**
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = MissingMethodParameterException.class)
    public void testAddRelationWithoutTaskParam() throws Exception {

        addContentRelations(this.itemId, null);
    }

    /**
     * Test declining adding of an relation with a non existing target to the item.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = ReferencedResourceNotFoundException.class)
    public void testAddRelationWithNonExistingTarget() throws Exception {
        String targetId = "bla";

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(1);
        relations.add(new TaskParamFactory.Relation(targetId, null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        addContentRelations(this.itemId, taskParam);
    }

    /**
     * Test declining adding of an relation with a non existing predicate to the item.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = RelationPredicateNotFoundException.class)
    public void testAddRelationWithNonExistingPredicate() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String xml1 = create(itemWithoutComponents);

        String xml2 = create(itemWithoutComponents);
        String targetId1 = getObjidValue(xml1);
        String targetId2 = getObjidValue(xml2);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(2);
        relations.add(new TaskParamFactory.Relation(targetId1, "bla"));
        relations.add(new TaskParamFactory.Relation(targetId2, "bla"));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        addContentRelations(this.itemId, taskParam);
    }

    /**
     * Test declining adding of an relation with a target id containing a version number.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = InvalidContentException.class)
    public void testAddRelationWithTargetContainingVersionNumber() throws Exception {

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(1);
        relations.add(new TaskParamFactory.Relation("escidoc:123:1", null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        addContentRelations(this.itemId, taskParam);
    }

    /**
     * Test declining adding of an existing relation to the item.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = AlreadyExistsException.class)
    public void testAddExistingRelation() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String xml = create(itemWithoutComponents);
        String targetId = getObjidValue(xml);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>();
        relations.add(new TaskParamFactory.Relation(targetId, null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        addContentRelations(this.itemId, taskParam);
        lastModDate = getTheLastModificationParam(this.itemId);
        taskParam = TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        addContentRelations(this.itemId, taskParam);
    }

    /**
     * Test successfully removing an existing relation.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRemoveRelation() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String xml = create(itemWithoutComponents);
        String targetId = getObjidValue(xml);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(1);
        relations.add(new TaskParamFactory.Relation(targetId, null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        addContentRelations(this.itemId, taskParam);

        lastModDate = getTheLastModificationParam(this.itemId);

        taskParam = TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        removeContentRelations(this.itemId, taskParam);
        String itemWithoutContentRelations = retrieve(this.itemId);
        Document itemWithoutContentRelationsDoc = EscidocAbstractTest.getDocument(itemWithoutContentRelations);
        Node relationsNode = selectSingleNode(itemWithoutContentRelationsDoc, "/item/relations/relation");
        assertNull("relations may not exist", relationsNode);
    }

    /**
     * Test declining removing of a already deleted relation.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRemoveDeletedRelation() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String xml = create(itemWithoutComponents);
        String targetId = getObjidValue(xml);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(1);
        relations.add(new TaskParamFactory.Relation(targetId, null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        addContentRelations(this.itemId, taskParam);

        lastModDate = getTheLastModificationParam(this.itemId);

        taskParam = TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        removeContentRelations(this.itemId, taskParam);
        String itemWithoutContentRelations = retrieve(this.itemId);
        Document itemWithoutContentRelationsDoc = EscidocAbstractTest.getDocument(itemWithoutContentRelations);
        Node relationsNode = selectSingleNode(itemWithoutContentRelationsDoc, "/item/relations/relation");
        assertNull("relations may not exist", relationsNode);
        lastModDate = getTheLastModificationParam(this.itemId);
        taskParam = TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        try {
            removeContentRelations(this.itemId, taskParam);
            fail("No exception occurred on remove a already deleted relation");
        }
        catch (final Exception e) {
            EscidocAbstractTest.assertExceptionType("ContentRelationNotFoundException expected.",
                ContentRelationNotFoundException.class, e);
        }
    }

    /**
     * Test declining removing of an existing relation, which belongs to another source resource.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRemoveRelationWithWrongSource() throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String xml1 = create(itemWithoutComponents);
        String xml2 = create(itemWithoutComponents);
        String targetId = getObjidValue(xml1);
        String sourceId = getObjidValue(xml2);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(1);
        relations.add(new TaskParamFactory.Relation(targetId, null));

        String lastModDate = getTheLastModificationParam(sourceId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        addContentRelations(sourceId, taskParam);

        lastModDate = getTheLastModificationParam(this.itemId);
        taskParam = TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        try {
            removeContentRelations(this.itemId, taskParam);
            fail("No exception occurred on remove an relation with a wrong source");
        }
        catch (final Exception e) {
            EscidocAbstractTest.assertExceptionType("ContentRelationNotFoundException expected.",
                ContentRelationNotFoundException.class, e);
        }

    }

    /**
     * Test retrieveRelations. A relation is created via addContentRelationMethod. 
     * Predicate contains '#'.
     *   
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRetrieveRelations() throws Exception {

        final String predicate = "http://www.escidoc.de/ontologies/mpdl-ontologies/content-relations#isPartOf";
        String targetId = addRelation(itemId, predicate);

        String relationsElementXml = retrieveRelations(this.itemId);
        selectSingleNodeAsserted(EscidocAbstractTest.getDocument(relationsElementXml), "/relations");
        assertXmlValidItem(relationsElementXml);
        Document relationsDoc = getDocument(relationsElementXml);
        assertXmlExists("relation ids are not equal", relationsDoc, "/relations/relation[@href = '/ir/item/" + targetId
            + "']");
        assertXmlExists("relation predicate is not equal", relationsDoc, "/relations/relation[@predicate = '"
            + predicate + "']");
    }

    /**
     * Test retrieveRelations. A relation is created via addContentRelationMethod. 
     * Predicate contains '/'.
     *   
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRetrieveRelations2() throws Exception {

        final String predicate = "http://escidoc.org/examples/test1";
        String targetId = addRelation(itemId, predicate);

        String relationsElementXml = retrieveRelations(this.itemId);
        selectSingleNodeAsserted(EscidocAbstractTest.getDocument(relationsElementXml), "/relations");
        assertXmlValidItem(relationsElementXml);
        Document relationsDoc = getDocument(relationsElementXml);
        assertXmlExists("relation ids are not equal", relationsDoc, "/relations/relation[@href = '/ir/item/" + targetId
            + "']");
        assertXmlExists("relation predicate is not equal", relationsDoc, "/relations/relation[@predicate = '"
            + predicate + "']");
    }

    /**
     * Test retrieveRelations. A relation is created via addContentRelationMethod. 
     * Predicate contains '/'.
     *   
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRetrieveNonexistingRelations() throws Exception {

        String relationsElementXml = retrieveRelations(this.itemId);
        Node relationsElementDoc = EscidocAbstractTest.getDocument(relationsElementXml);
        selectSingleNodeAsserted(relationsElementDoc, "/relations");
        assertNull(selectSingleNode(relationsElementDoc, "/relations/*"));
    }

    /**
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = ItemNotFoundException.class)
    public void testRetrieveRelationsWithWrongId() throws Exception {

        addRelation(itemId, null);
        retrieveRelations("bla");
    }

    /**
     * @throws Exception
     *             If anything fails.
     */
    @Test(expected = MissingMethodParameterException.class)
    public void testRetrieveRelationsWithoutId() throws Exception {

        addRelation(itemId, null);
        retrieveRelations(null);
    }

    /**
     * Test the last-modification-date in the return value of addContentRelations.
     * 
     * @throws Exception
     *             If anything fails.
     */
    @Test
    public void testRelationReturnValue01() throws Exception {

        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);

        String xml1 = create(itemWithoutComponents);

        String xml2 = create(itemWithoutComponents);
        String targetId1 = getObjidValue(xml1);
        String targetId2 = getObjidValue(xml2);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>();
        relations.add(new TaskParamFactory.Relation(targetId1, null));
        relations.add(new TaskParamFactory.Relation(targetId2, null));

        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        String resultXml = addContentRelations(this.itemId, taskParam);
        assertXmlValidResult(resultXml);

        Document resultDoc = EscidocAbstractTest.getDocument(resultXml);
        String lmdResult = getLastModificationDateValue(resultDoc);

        String itemWithRelations = retrieve(this.itemId);

        Document itemDoc = EscidocAbstractTest.getDocument(itemWithRelations);
        String lmdItem = getLastModificationDateValue(itemDoc);

        assertEquals("Last modification date of result and item not equal", lmdResult, lmdItem);

        // now test last-modification-date of removeContentRelations

        relations.clear();
        relations.add(new TaskParamFactory.Relation(targetId2, null));

        taskParam = TaskParamFactory.getRelationTaskParam(relations, lmdItem, TaskParamFactory.ONTOLOGY_IS_PART_OF);
        resultXml = removeContentRelations(this.itemId, taskParam);
        assertXmlValidResult(resultXml);

        resultDoc = EscidocAbstractTest.getDocument(resultXml);
        String lmdResultRemove = getLastModificationDateValue(resultDoc);

        String itemWithOutRelations = retrieve(this.itemId);

        itemDoc = EscidocAbstractTest.getDocument(itemWithOutRelations);
        lmdItem = getLastModificationDateValue(itemDoc);

        assertEquals("Last modification date of result and item not equal", lmdResultRemove, lmdItem);
    }

    /**
     * Test successfully retrieving a last version of an item, which has an active relation in the last version but not
     * in the old version.
     * 
     * @throws Exception
     */
    @Test
    public void testRelationsWithVersionedItem() throws Exception {

        submit(this.itemId, TaskParamFactory.getStatusTaskParam(
            getLastModificationDateValue2(getDocument(this.itemXml)), null));
        String submittedItem = retrieve(this.itemId);

        String target = create(getTemplateAsString(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml"));
        String targetId = getObjidValue(target);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>(1);
        relations.add(new TaskParamFactory.Relation(targetId, null));

        String lastModDate = getLastModificationDateValue(getDocument(submittedItem));
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        // update tu version 2
        addContentRelations(this.itemId, taskParam);

        String submittedWithRelations = retrieve(this.itemId);
        Document submittedWithRelationsDoc = getDocument(submittedWithRelations);
        assertXmlExists("number of relations is wrong", submittedWithRelationsDoc,
            "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", submittedWithRelationsDoc,
            "/item/relations/relation[@href = '/ir/item/" + targetId + "']");

        // update to version 3
        String newItemXml = addCtsElement(submittedWithRelations);
        String updatedItem = update(itemId, newItemXml);
        Document updatedItemDoc = getDocument(updatedItem);
        assertXmlExists("number of relations is wrong", updatedItemDoc, "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", updatedItemDoc, "/item/relations/relation[@href = '/ir/item/"
            + targetId + "']");

        // check version 1
        Document itemVersion1Doc = getDocument(retrieve(this.itemId + ":1"));
        assertXmlExists("number of relations is wrong", itemVersion1Doc, "/item/relations[count(./relation)='0']");

        // check version 2
        String item = retrieve(this.itemId + ":2");
        Document itemDoc = getDocument(item);
        assertXmlExists("number of relations is wrong", itemDoc, "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", itemDoc, "/item/relations/relation[@href = '/ir/item/" + targetId
            + "']");

        // check version 3
        item = retrieve(this.itemId);
        itemDoc = getDocument(item);
        assertXmlExists("number of relations is wrong", itemDoc, "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", itemDoc, "/item/relations/relation[@href = '/ir/item/" + targetId
            + "']");
    }

    /**
     * Test add lightweigth content relation by Item.update().
     * 
     * (issue INFR-1329)
     * 
     * @throws Exception
     */
    @Test
    public void addContentRelationbyItemUpdate() throws Exception {

        final String targetItemXml =
            create(getTemplateAsString(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml"));
        final String targetId = getObjidValue(targetItemXml);

        final String predicate = "http://www.escidoc.de/ontologies/mpdl-ontologies/content-relations#isPartOf";

        String itemWithCR = addRelationToItemXml(this.itemXml, predicate, targetId);

        String updatedItemXml = update(itemId, itemWithCR);
        Document updatedItemDoc = getDocument(updatedItemXml);

        assertXmlExists("number of relations is wrong", updatedItemDoc, "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", updatedItemDoc, "/item/relations/relation[@href = '/ir/item/"
            + targetId + "']");
        assertXmlExists("relation predicate is not equal", updatedItemDoc, "/item/relations/relation[@predicate = '"
            + predicate + "']");
    }

    /**
     * Test add lightweigth content relation by Item.update().
     * This test checks if a slash (instead of #) as separator is well handled.
     * 
     * (issue INFR-1329)
     * 
     * @throws Exception
     */
    @Test
    public void addContentRelationbyItemUpdate2() throws Exception {

        final String targetItemXml =
            create(getTemplateAsString(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml"));
        final String targetId = getObjidValue(targetItemXml);

        final String predicate = "http://escidoc.org/examples/test1";

        String itemWithCR = addRelationToItemXml(this.itemXml, predicate, targetId);

        String updatedItemXml = update(itemId, itemWithCR);
        Document updatedItemDoc = getDocument(updatedItemXml);

        assertXmlExists("number of relations is wrong", updatedItemDoc, "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", updatedItemDoc, "/item/relations/relation[@href = '/ir/item/"
            + targetId + "']");
        assertXmlExists("relation predicate is not equal", updatedItemDoc, "/item/relations/relation[@predicate = '"
            + predicate + "']");
    }

    /**
     * Test add lightweigth content relation by Item.update() and remove is afterwards.
     * 
     * (issue INFR-1329)
     * 
     * @throws Exception
     *             Thrown if add or remove behavior is not as expected
     */
    @Test
    public void removeContentRelationbyItemUpdate() throws Exception {

        final String predicate = "http://www.escidoc.de/ontologies/mpdl-ontologies/content-relations#isPartOf";

        final String targetItemXml =
            create(getTemplateAsString(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml"));
        final String targetId = getObjidValue(targetItemXml);

        String itemWithCR = addRelationToItemXml(this.itemXml, predicate, targetId);

        String updatedItemXml = update(itemId, itemWithCR);
        Document updatedItemDoc = getDocument(updatedItemXml);

        assertXmlExists("number of relations is wrong", updatedItemDoc, "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", updatedItemDoc, "/item/relations/relation[@href = '/ir/item/"
            + targetId + "']");
        assertXmlExists("relation predicate is not equal", updatedItemDoc, "/item/relations/relation[@predicate = '"
            + predicate + "']");

        deleteNodes(updatedItemDoc, "/item/relations/relation");

        updatedItemXml = update(this.itemId, toString(updatedItemDoc, true));
        updatedItemDoc = getDocument(updatedItemXml);

        assertXmlExists("number of relations is wrong", updatedItemDoc, "/item/relations[count(./relation)='0']");
    }

    /**
     * Test add lightweigth content relation by Item.update() and remove is afterwards.
     * This test checks if a slash (instead of #) as separator is well handled.
     * 
     * (issue INFR-1329)
     * 
     * @throws Exception
     *             Thrown if add or remove behavior is not as expected
     */
    @Test
    public void removeContentRelationbyItemUpdate2() throws Exception {

        final String predicate = "http://escidoc.org/examples/test1";

        final String targetItemXml =
            create(getTemplateAsString(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml"));
        final String targetId = getObjidValue(targetItemXml);

        String itemWithCR = addRelationToItemXml(this.itemXml, predicate, targetId);

        String updatedItemXml = update(itemId, itemWithCR);
        Document updatedItemDoc = getDocument(updatedItemXml);

        assertXmlExists("number of relations is wrong", updatedItemDoc, "/item/relations[count(./relation)='1']");
        assertXmlExists("relation ids are not equal", updatedItemDoc, "/item/relations/relation[@href = '/ir/item/"
            + targetId + "']");
        assertXmlExists("relation predicate is not equal", updatedItemDoc, "/item/relations/relation[@predicate = '"
            + predicate + "']");

        deleteNodes(updatedItemDoc, "/item/relations/relation");

        updatedItemXml = update(this.itemId, toString(updatedItemDoc, true));
        updatedItemDoc = getDocument(updatedItemXml);

        assertXmlExists("number of relations is wrong", updatedItemDoc, "/item/relations[count(./relation)='0']");
    }

    /**
     * @param objectId
     *            The id of the object to which the relation should be added. The source id.
     * @param predicate
     *            The predicate of the relation.
     * @throws Exception
     *             If anything fails.
     */
    private String addRelation(final String objectId, final String predicate) throws Exception {
        Document xmlItem =
            EscidocAbstractTest.getTemplateAsDocument(TEMPLATE_ITEM_PATH + "/rest", "escidoc_item_198_for_create.xml");
        Node xmlItemWithoutComponents = deleteElement(xmlItem, "/item/components");
        String itemWithoutComponents = toString(xmlItemWithoutComponents, true);
        String createdItem = create(itemWithoutComponents);
        String targetId = getObjidValue(createdItem);

        List<TaskParamFactory.Relation> relations = new ArrayList<TaskParamFactory.Relation>();
        relations.add(new TaskParamFactory.Relation(targetId, predicate));
        String lastModDate = getTheLastModificationParam(this.itemId);
        String taskParam =
            TaskParamFactory.getRelationTaskParam(relations, lastModDate, TaskParamFactory.ONTOLOGY_IS_PART_OF);

        addContentRelations(this.itemId, taskParam);

        return targetId;
    }

    /**
     * @param id
     *            The id of the resource.
     * @return The date of last modification of the resource as string.
     * @throws Exception
     *             If anything fails.
     */
    private String getTheLastModificationParam(final String id) throws Exception {
        Document item = EscidocAbstractTest.getDocument(retrieve(id));

        // get last-modification-date
        NamedNodeMap atts = item.getDocumentElement().getAttributes();
        Node lastModificationDateNode = atts.getNamedItem("last-modification-date");
        String lastModificationDate = lastModificationDateNode.getNodeValue();

        return lastModificationDate;
    }

    /**
     * Get taskParameter with german Umlaut.
     *
     * @param relations
     * @param lastModDate
     *            last-modification-date
     * @return task-parameter (for task oriented methods)
     */
    private String getRelationTaskParamWithUmlaut(
        final List<TaskParamFactory.Relation> relations, final String lastModDate) {
        return TaskParamFactory.getRelationTaskParam(relations, lastModDate,
            "http://www.escidoc.org/ontologies/test/content-relations#isTest\u00dc\u00c4\u00d6");
    }

    /**
     * Add relation to item xml.
     *
     * @param xml item-xml
     * @param predicate relation-predicate
     * @param targetId relation-targetId
     * @return item-xml with relation
     */
    private String addRelationToItemXml(final String xml, final String predicate, final String targetId) {
        String itemWithCR = null;
        String xlinkPrefix = xml.replaceFirst("(?s).*?\\s([^\\s]*?):href.*", "$1");
        if (xml.matches("(?s).*<([^>\\/]*?):relations[^>]*?\\/\\s*?>.*")) {
            itemWithCR =
                xml.replaceFirst("(?s)<([^>\\/]*?):relations[^>]*?\\/\\s*?>", "<$1:relations>" + "<$1:relation "
                    + "predicate=\"" + predicate + "\" " + xlinkPrefix + ":href=\"/ir/item/" + targetId + "\" />"
                    + "</$1:relations>");
        }
        else if (xml.matches("(?s).*</([^>]*?):relations>.*")) {
            itemWithCR =
                xml.replaceFirst("(?s)</([^>]*?):relations>", "<$1:relation " + "predicate=\"" + predicate + "\" "
                    + xlinkPrefix + ":href=\"/ir/item/" + targetId + "\" />" + "</$1:relations>");
        }
        else {
            itemWithCR = xml;
        }
        return itemWithCR;
    }

}