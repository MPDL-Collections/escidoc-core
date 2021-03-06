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
package de.escidoc.core.test.om.container;

import de.escidoc.core.common.exceptions.remote.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.remote.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.remote.application.notfound.ContainerNotFoundException;
import de.escidoc.core.common.exceptions.remote.application.violated.OptimisticLockingException;
import de.escidoc.core.test.EscidocAbstractTest;
import de.escidoc.core.test.TaskParamFactory;
import de.escidoc.core.test.common.AssignParam;

import org.apache.http.HttpResponse;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.fail;

/**
 * Test the mock implementation of the Container resource.
 *
 * @author Michael Schneider
 */
public class ContainerReleaseIT extends ContainerTestBase {

    private String theContainerXml;

    private String theContainerId;

    private String theItemId;

    private String theSubcontainerId;

    /**
     * Successfully release of container with sub-container (member).
     * <p/>
     * TODO check the member release procedure
     */
    @Test
    public void testOM_RCON_1() throws Exception {

        String lmd = null;
        String itemLmd = null;
        String subContainerId = null;

        // prepare a Item child to release
        String resultXml = submitItemHelp(this.theItemId);
        assertXmlValidResult(resultXml);
        String lmd1 = getLastModificationDateValue(getDocument(resultXml));
        itemLmd = prepareItemPid(this.theItemId, lmd1);
        assertTimestampIsEqualOrAfter("Wrong last modification date", itemLmd, lmd1);

        // prepare a Container child to release
        String xmlData = getContainerTemplate("create_container_WithoutMembers_v1.1.xml");
        xmlData = xmlData.replaceAll("<prop:pid>hdl:123/container456</prop:pid>", "");

        xmlData = createContainer(this.theContainerId, xmlData);
        subContainerId = getObjidValue(xmlData);
        String lmdSubCont = getLastModificationDateValue(getDocument(xmlData));

        lmdSubCont = prepareContainerPid(subContainerId, lmdSubCont);
        submit(subContainerId, TaskParamFactory.getStatusTaskParam(
            getLastModificationDateValue2(getDocument(retrieve(subContainerId))), null));

        // prepare the Container it self to release
        final String submitComment = String.valueOf(System.nanoTime());
        resultXml =
            submit(theContainerId, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), submitComment));
        assertXmlValidResult(resultXml);
        lmd = getLastModificationDateValue(getDocument(resultXml));
        assertXmlEquals("Comment string not as expected", EscidocAbstractTest.getDocument(retrieve(theContainerId)),
            "/container/properties/public-status-comment", submitComment);

        // release the Container
        String containerLmd = getTheLastModificationDate(this.theContainerId);
        containerLmd = prepareContainerPid(this.theContainerId, containerLmd);
        final String releaseComment = String.valueOf(System.nanoTime());
        resultXml =
            release(theContainerId, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), releaseComment));
        assertXmlValidResult(resultXml);
        lmd = getLastModificationDateValue(getDocument(resultXml));
        assertTimestampIsEqualOrAfter("Wrong last modification date", lmd, containerLmd);
        assertXmlEquals("Comment string not as expected", EscidocAbstractTest.getDocument(retrieve(theContainerId)),
            "/container/properties/public-status-comment", releaseComment);

        // check the Container and children
        String containerXml = retrieve(theContainerId);
        assertXmlEquals("Container Status not as expected", EscidocAbstractTest.getDocument(containerXml),
            "/container/properties/public-status", "released");

        String subContainerXml = retrieve(subContainerId);
        assertXmlEquals("Container Status not as expected", EscidocAbstractTest.getDocument(subContainerXml),
            "/container/properties/public-status", "released");

    }

    /**
     * Test result of task oriented status methods.
     *
     * @throws Exception Thrown if anything failed.
     */
    @Test
    public void testContainerResultValue() throws Exception {

        submitItemHelp(this.theItemId);

        String resultXml =
            submit(theSubcontainerId, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theSubcontainerId))), null));
        assertXmlValidResult(resultXml);

        String pidParam;
        AssignParam assignPidParam = new AssignParam();

        // assign pid to member (item)
        if (getContainerClient().getPidConfig("cmm.Item.objectPid.setPidBeforeRelease", "true")
            && !getContainerClient().getPidConfig("cmm.Item.objectPid.releaseWithoutPid", "false")) {

            //            assignPidParam.setUrl(new URL("http://somewhere/" + this.theSubcontainerId));
            //            pidParam =
            //                getAssignPidTaskParam(getLastModificationDateValue2(getDocument(retrieve(this.theSubcontainerId))),
            //                    assignPidParam);
            // assignObjectPid(this.theSubcontainerId, pidParam);
        }
        if (getContainerClient().getPidConfig("cmm.Item.versionPid.setPidBeforeRelease", "true")
            && !getContainerClient().getPidConfig("cmm.Item.versionPid.releaseWithoutPid", "false")) {

            String latestVersion = getLatestVersionObjidValue(retrieve(this.theSubcontainerId));

            assignPidParam.setUrl(new URL("http://somewhere/" + latestVersion));
            pidParam =
                TaskParamFactory.getAssignPidTaskParam(assignPidParam,
                    getLastModificationDateValue2(getDocument(retrieve(latestVersion))));

            assignVersionPid(latestVersion, pidParam);
        }

        resultXml =
            submit(theContainerId, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), null));
        assertXmlValidResult(resultXml);

        // assign pid to container
        if (getContainerClient().getPidConfig("cmm.Container.objectPid.setPidBeforeRelease", "true")
            && !getContainerClient().getPidConfig("cmm.Container.objectPid.releaseWithoutPid", "false")) {

            assignPidParam.setUrl(new URL("http://somewhere/" + this.theContainerId));
            pidParam =
                TaskParamFactory.getAssignPidTaskParam(assignPidParam,
                    getLastModificationDateValue2(getDocument(retrieve(this.theContainerId))));

            assignObjectPid(this.theContainerId, pidParam);
        }
        if (getContainerClient().getPidConfig("cmm.Container.versionPid.setPidBeforeRelease", "true")
            && !getContainerClient().getPidConfig("cmm.Container.versionPid.releaseWithoutPid", "false")) {

            String latestVersion = getLatestVersionObjidValue(retrieve(this.theContainerId));

            assignPidParam.setUrl(new URL("http://somewhere/" + latestVersion));
            pidParam =
                TaskParamFactory.getAssignPidTaskParam(assignPidParam,
                    getLastModificationDateValue2(getDocument(retrieve(latestVersion))));

            assignVersionPid(latestVersion, pidParam);
        }

        resultXml =
            release(theContainerId, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), null));
        assertXmlValidResult(resultXml);

        String containerXml = retrieve(theContainerId);
        assertXmlEquals("Container Status not as expected", EscidocAbstractTest.getDocument(containerXml),
            "/container/properties/public-status", "released");

        String subContainerXml = retrieve(theSubcontainerId);
        assertXmlEquals("Container Status not as expected", EscidocAbstractTest.getDocument(subContainerXml),
            "/container/properties/public-status", "released");

    }

    /**
     * Test declining release of container with non existing container id
     */
    @Test
    public void testOM_RCON_2_1() throws Exception {

        try {
            release("bla", TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), null));
            fail("No exception occurred on release with non" + "existing container id.");
        }
        catch (final Exception e) {
            Class<?> ec = ContainerNotFoundException.class;
            EscidocAbstractTest.assertExceptionType(ec.getName() + " expected.", ec, e);
        }
    }

    /**
     * Test declining release of container with wrong time stamp
     */
    @Test
    public void test_OM_RCON_2_2() throws Exception {

        String pidParam;
        AssignParam assignPidParam = new AssignParam();

        String resultXml =
            submit(theContainerId, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), null));
        assertXmlValidResult(resultXml);
        if (getContainerClient().getPidConfig("cmm.Container.objectPid.setPidBeforeRelease", "true")
            && !getContainerClient().getPidConfig("cmm.Container.objectPid.releaseWithoutPid", "false")) {

            assignPidParam.setUrl(new URL("http://somewhere/" + this.theContainerId));
            pidParam =
                TaskParamFactory.getAssignPidTaskParam(assignPidParam,
                    getLastModificationDateValue2(getDocument(retrieve(this.theContainerId))));
            assignObjectPid(this.theContainerId, pidParam);
        }
        if (getContainerClient().getPidConfig("cmm.Container.versionPid.setPidBeforeRelease", "true")
            && !getContainerClient().getPidConfig("cmm.Container.versionPid.releaseWithoutPid", "false")) {

            String latestVersion = getLatestVersionObjidValue(theContainerXml);

            assignPidParam.setUrl(new URL("http://somewhere/" + latestVersion));
            pidParam =
                TaskParamFactory.getAssignPidTaskParam(assignPidParam,
                    getLastModificationDateValue2(getDocument(retrieve(latestVersion))));

            assignVersionPid(latestVersion, pidParam);
        }

        try {
            release(theContainerId, TaskParamFactory.getStatusTaskParam(new DateTime(), null));
            fail("No exception occurred on release with wrong time stamp.");
        }
        catch (final Exception e) {
            Class<?> ec = OptimisticLockingException.class;
            EscidocAbstractTest.assertExceptionType(ec.getName() + " expected.", ec, e);
        }
    }

    /**
     * Test declining release of container with missing container id
     */
    @Test
    public void testOM_RCON_3_1() throws Exception {

        try {
            release(null, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), null));
            fail("No exception occurred on release with missing " + "container id.");
        }
        catch (final Exception e) {
            Class<?> ec = MissingMethodParameterException.class;
            EscidocAbstractTest.assertExceptionType(ec.getName() + " expected.", ec, e);
        }
    }

    /**
     * Test declining release of container with missing time stamp
     */
    @Test
    public void testOM_RCON_3_2() throws Exception {

        try {
            release(theContainerId, null);
            fail("No exception occurred on release with missing" + "time stamp.");
        }
        catch (final Exception e) {
            Class<?> ec = MissingMethodParameterException.class;
            EscidocAbstractTest.assertExceptionType(ec.getName() + " expected.", ec, e);
        }
    }

    /**
     * Test declining release of container befor submit
     */
    @Test
    public void testOM_RCON_3_3() throws Exception {

        try {
            release(theContainerId, TaskParamFactory.getStatusTaskParam(
                getLastModificationDateValue2(getDocument(retrieve(theContainerId))), null));
            fail("No exception occurred on release before submit.");
        }
        catch (final Exception e) {
            Class<?> ec = InvalidStatusException.class;
            EscidocAbstractTest.assertExceptionType(ec.getName() + " expected.", ec, e);
        }
    }

    /**
     * Set up servlet test.
     *
     * @throws Exception If anything fails.
     */
    @Before
    public void setUp() throws Exception {
        this.theItemId = createItemFromTemplate("escidoc_item_198_for_create.xml");

        String xmlData = getContainerTemplate("create_container_WithoutMembers_v1.1.xml");
        theContainerXml = create(xmlData);

        this.theSubcontainerId = getObjidValue(theContainerXml);

        String xmlData1 = getContainerTemplate("create_container_v1.1-forItemAndforContainer.xml");

        String xmlWithItem = xmlData1.replaceAll("##ITEMID##", theItemId);
        String xmlWithItemAndContainer = xmlWithItem.replaceAll("##CONTAINERID##", theSubcontainerId);
        theContainerXml = create(xmlWithItemAndContainer);

        this.theContainerId = getObjidValue(theContainerXml);

    }

    /**
     * Clean up after test.
     *
     * @throws Exception If anything fails.
     */
    @Override
    @After
    public void tearDown() throws Exception {

        super.tearDown();
        theContainerXml = null;

        theContainerId = null;

        theSubcontainerId = null;
        // TODO purge object from Fedora
    }

    /**
     * Submit the Item. the Item is retrieved before call submit method to determine the last-modification-date.
     *
     * @param itemId The id of the item.
     * @return The return value of the submit method.
     * @throws Exception Thrown if submitting failed.
     */
    private String submitItemHelp(final String itemId) throws Exception {

        String lmd =
            getLastModificationDateValue(EscidocAbstractTest.getDocument(handleXmlResult(getItemClient().retrieve(
                itemId))));

        return submitItemHelp(itemId, lmd);
    }

    /**
     * Submit the Item.
     *
     * @param itemId The id of the item.
     * @param lmd    The last-modification-date of the Item.
     * @return The return value of the submit method.
     * @throws Exception Thrown if submitting failed.
     */
    private String submitItemHelp(final String itemId, final String lmd) throws Exception {

        String param = TaskParamFactory.getStatusTaskParam(new DateTime(lmd, DateTimeZone.UTC), null);

        Object result = getItemClient().submit(itemId, param);
        if (result instanceof HttpResponse) {
            HttpResponse httpRes = (HttpResponse) result;
            assertHttpStatusOfMethod("", httpRes);
        }

        return handleXmlResult(result);
    }

}
