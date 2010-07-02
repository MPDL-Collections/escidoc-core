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
package de.escidoc.core.om.business.fedora.container;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.business.fedora.HandlerBase;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.business.fedora.Utility;
import de.escidoc.core.common.business.fedora.resources.Container;
import de.escidoc.core.common.business.fedora.resources.GenericResource;
import de.escidoc.core.common.business.fedora.resources.Item;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.notfound.ContainerNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ItemNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.StreamNotFoundException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyVersionException;
import de.escidoc.core.common.exceptions.system.EncodingSystemException;
import de.escidoc.core.common.exceptions.system.FedoraSystemException;
import de.escidoc.core.common.exceptions.system.IntegritySystemException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.exceptions.system.TripleStoreSystemException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.exceptions.system.XmlParserSystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.xml.XmlUtility;
import de.escidoc.core.om.business.renderer.VelocityXmlContainerFoXmlRenderer;
import de.escidoc.core.om.business.renderer.VelocityXmlContainerRenderer;
import de.escidoc.core.om.business.renderer.interfaces.ContainerFoXmlRendererInterface;
import de.escidoc.core.om.business.renderer.interfaces.ContainerRendererInterface;

/**
 * Contains base functionality of FedoraContainerHandler. Is extended at least
 * by FedoraContainerHandler.
 * 
 * @author ROF
 * 
 */
public class ContainerHandlerBase extends HandlerBase {

    private static AppLogger log =
        new AppLogger(ContainerHandlerBase.class.getName());

    private Container container = null;

    private GenericResource item = null;

    private Utility utility = null;

    private ContainerRendererInterface renderer = null;

    private ContainerFoXmlRendererInterface foxmlRenderer = null;

    /**
     * @return the container
     */
    public Container getContainer() {
        return container;
    }

    /**
     * @return the item
     */
    public GenericResource getItem() {
        return item;
    }

    /**
     * Bounds a Item object to this handler. Subsequent calls to this method
     * have no effect.
     * 
     * @param id
     *            The ID of the item which should be bound to this Handler.
     * @throws ItemNotFoundException
     *             If there is no item with <code>id</code> in the repository.
     * @throws XmlParserSystemException
     *             If xml parser fails.
     * @throws TripleStoreSystemException
     *             If triple store reports an error.
     * @throws WebserverSystemException
     *             In case of an internal error.
     * @throws IntegritySystemException
     *             If the integrity of the repository is violated.
     */
    protected void setItem(final String id) throws ItemNotFoundException,
        WebserverSystemException, XmlParserSystemException,
        TripleStoreSystemException, IntegritySystemException {

        try {

            this.item = new Item(id);

        }
        catch (final FedoraSystemException e) {
            // FIXME all exceptions are caught in caller
            log.error(e);
        }
        catch (final StreamNotFoundException e) {
            log.error(e.toString());
            throw new ItemNotFoundException(e);
        }
        catch (final ResourceNotFoundException e) {
            log.debug(e.toString());
            throw new ItemNotFoundException(e);
        }
    }

    /**
     * Bounds a Container object to this handler. Subsequent calls to this
     * method have no effect.
     * 
     * @param id
     *            The ID of the container which should be bound to this Handler.
     * @throws ContainerNotFoundException
     *             If there is no container with <code>id</code> in the
     *             repository.
     * @throws SystemException
     *             Thrown in case of an internal system error.
     */
    public void setContainer(final String id)
        throws ContainerNotFoundException, SystemException {

        try {
            // if (container == null ||
            // !container.getId().equals(idWithOutVersion)) {}
            this.container = new Container(id);

            // check if withdrawn; in Q2 only content
            // String status = TripleStoreUtility.getInstance()
            // .getPropertiesElements(container.getId(), "status",
            // Constants.CONTAINER_PROPERTIES_NAMESPACE_URI);
            // if
            // (status.equals(Constants.STATUS_WITHDRAWN))
            // {
            // // version data
            // HashMap versionData = null;
            // try {
            // StaxParser sp = new StaxParser();
            // WovReadHandler wrh = new WovReadHandler(sp);
            // sp.addHandler(wrh);
            // sp.parse(new ByteArrayInputStream(container.getWov()
            // .getStream()));
            // versionData = wrh.getVersionData();
            // }
            // catch (Exception e) {
            // throw new EscidocRuntimeException(e);
            // }
            // String withdrawComment = (String) versionData.get("comment");
            // // TODO WithdrawnException
            // throw new ContainerNotFoundException(
            // "Container is found but withdrawn. Cause: "
            // + withdrawComment);
            // }
        }
        catch (final StreamNotFoundException e) {
            log.error(e.toString());
            throw new IntegritySystemException(e);
        }
        catch (final ResourceNotFoundException e) {
            log.debug(e.toString());
            throw new ContainerNotFoundException(e);
        }

    }

    /**
     * Get versions datastream of the container.
     * 
     * @return The versions datastream.
     * @throws WebserverSystemException
     * @throws StreamNotFoundException
     *             If a datastream can not be retrieved.
     * @throws FedoraSystemException
     *             If Fedora reports an error.
     * @throws EncodingSystemException
     *             In case of an encoding failure.
     * @throws WebserverSystemException
     *             Thrown if converting character encoding failed.
     */
    protected String getVersions() throws EncodingSystemException,
        WebserverSystemException, FedoraSystemException,
        StreamNotFoundException {

        return getContainer().getWov().toStringUTF8();
    }

    /**
     * Check if the container is locked.
     * 
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @throws LockingException
     *             If the container is locked and the current user is not the
     *             one who locked it.
     */
    protected void checkLocked() throws LockingException,
        WebserverSystemException {
        if (getContainer().isLocked()
            && !getContainer().getLockOwner().equals(
                getUtility().getCurrentUser()[0])) {
            final String message =
                "Container + "
                    + getContainer().getId()
                    + " is locked by "
                    + XmlUtility.escapeForbiddenXmlCharacters(getContainer()
                        .getLockOwner()) + ".";
            log.debug(message);
            throw new LockingException(message);
        }
    }

    /**
     * Check if container is tagged with status released.
     * 
     * @throws InvalidStatusException
     *             If container status is not released.
     * @throws WebserverSystemException
     *             In case of an internal error.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     */
    protected void checkNotReleased() throws InvalidStatusException,
        WebserverSystemException, TripleStoreSystemException {
        checkNotStatus(Constants.STATUS_RELEASED);
    }

    /**
     * Check if container is not submitted.
     * 
     * @throws InvalidStatusException
     *             If container status is not released.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     * @throws WebserverSystemException
     *             In case of an internal error.
     */
    protected void checkNotSubmitted() throws InvalidStatusException,
        WebserverSystemException, TripleStoreSystemException {
        checkNotStatus(Constants.STATUS_SUBMITTED);
    }

    /**
     * Check if container is not in the specified status.
     * 
     * @param status
     *            A status.
     * 
     * @throws InvalidStatusException
     *             If container is not the specified status.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     * @throws WebserverSystemException
     *             In case of an internal error.
     */
    protected void checkNotStatus(final String status)
        throws InvalidStatusException, TripleStoreSystemException,
        WebserverSystemException {

        final String curStatus =
            TripleStoreUtility.getInstance().getPropertiesElements(
                getContainer().getId(), TripleStoreUtility.PROP_PUBLIC_STATUS);
        // In first release, if object is once released no changes are allowed
        if (!status.equals(curStatus)) {
            final String msg = "The object is in not state '" + status + "'.";
            log.debug(msg);
            throw new InvalidStatusException(msg);
        }
    }

    /**
     * Check release status of object.
     * 
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @throws InvalidStatusException
     *             Thrown if object is not in status released.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     */
    protected void checkReleased() throws InvalidStatusException,
        TripleStoreSystemException, WebserverSystemException {

        final String status =
            TripleStoreUtility.getInstance().getPropertiesElements(
                getContainer().getId(), TripleStoreUtility.PROP_PUBLIC_STATUS);
        // In first release, if object is once released no changes are allowed
        if (status.equals(Constants.STATUS_RELEASED)) {
            // check if the version is in status released
            // FIXME check if the LATEST version is in status released. That
            // seems to be the same because all methods that call checkReleased
            // also call checkLatestVersion. But the semantic should be true
            // without another method call. (? FRS)
            if (TripleStoreUtility.getInstance().getPropertiesElements(
                getContainer().getId(),
                TripleStoreUtility.PROP_LATEST_VERSION_STATUS).equals(
                Constants.STATUS_RELEASED)) {

                final String msg =
                    "The object is in state '" + Constants.STATUS_RELEASED
                        + "' and can not be" + " changed.";
                log.info(msg);
                throw new InvalidStatusException(msg);
            }
        }
    }

    /**
     * Check if the provided container version is tagged with a status released.
     * 
     * @param checkStatus
     *            The compared status.
     * @throws InvalidStatusException
     *             If provided container version status is not released
     * 
     */
    protected void checkVersionStatusNot(final String checkStatus)
        throws InvalidStatusException {

        String status = null;
        String timestamp = null;
        DocumentBuilder db = null;
        Document xmlDom = null;

        final XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmlDom =
                db.parse(new ByteArrayInputStream(getContainer()
                    .getWov().getStream()));

            // get status from version-history/version[@objid='id']/
            // version-status[@timestamp='timestamp']
            final String xpathTimestamp =
                "/version-history/version[@objid='"
                    + getContainer().getLatestVersionId() + "']/timestamp";
            timestamp = xpath.evaluate(xpathTimestamp, xmlDom);

            // fetch the status with the newest timestamp
            final String xpathStatus =
                "/version-history/version[@objid='"
                    + getContainer().getLatestVersionId() + "'][@timestamp='"
                    + timestamp + "']/version-status";

            status = xpath.evaluate(xpathStatus, xmlDom);
        }
        catch (final Exception e) {
            log.equals(e.toString());
            throw new InvalidStatusException(e);
        }

        // In first release, if object is once released no changes are allowed
        if (status.equals(checkStatus)) {
            final String msg =
                "The object is in state '" + checkStatus + "' and can not be"
                    + " changed.";
            log.info(msg);
            throw new InvalidStatusException(msg);
        }
    }
    /**
     * Check if container version is tagged with provided status.
     * 
     * @param checkStatus
     *            The status which is to validate.
     * 
     * @throws InvalidStatusException
     *             If container version status is not equal to the requested
     *             <code>checkStatus</code>.
     * @throws IntegritySystemException
     *             Thrown if version status could not be obtained.
     */
    protected void checkVersionStatus(final String checkStatus)
        throws InvalidStatusException, IntegritySystemException {

        final String status = getContainer().getVersionStatus();

        // In first release, if object is once released no changes are allowed
        if (!status.equals(checkStatus)) {
            final String msg =
                "The object is in state '" + checkStatus + "' and can not be"
                    + " changed.";
            log.debug(msg);
            throw new InvalidStatusException(msg);
        }
    }
    /**
     * Check if no object PID is assigned to container. (called floating PID
     * before)
     * 
     * @throws InvalidStatusException
     *             If PID is assigned and part of the container.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     */
    protected void checkNoObjectPidAssigned() throws InvalidStatusException,
        TripleStoreSystemException, WebserverSystemException {

        final String pid =
            TripleStoreUtility.getInstance().getPropertiesElements(
                getContainer().getId(), TripleStoreUtility.PROP_OBJECT_PID);
        // In first release, if object is once released no changes are allowed
        if ((pid != null) && (pid.length() > 0)) {
            final String msg =
                "The object is already assigned with PID '" + pid
                    + "' and can not be reassigned.";
            log.info(msg);
            throw new InvalidStatusException(msg);
        }
    }

    /**
     * Checks if the version of container has no PID assigned.
     * 
     * @param versionId
     *            Version ID of container (e.g. escidoc:123:3)
     * @throws InvalidStatusException
     *             If object version hasPID assigned already.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     * 
     */
    protected void checkNoVersionPidAssigned(final String versionId)
        throws InvalidStatusException, TripleStoreSystemException {

        // expand this method to support more than one pid system

        String pid = null;
        DocumentBuilder db = null;
        Document xmlDom = null;

        final XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            xmlDom =
                db.parse(new ByteArrayInputStream(getContainer()
                    .getWov().getStream()));

            // get status from /version-history/version[@objid='id']/pid
            final String xpathPid =
                "/version-history/version[@objid='" + versionId + "']/pid";
            pid = xpath.evaluate(xpathPid, xmlDom);
        }
        catch (final Exception e) {
            log.debug(e.toString());
            throw new InvalidStatusException(e);
        }

        // FIXME pid structure check ?
        if (pid.length() > 0) {
            final String msg =
                "This object version is already assigned with PID '" + pid
                    + "' and can not be reassigned.";
            log.info(msg);
            throw new InvalidStatusException(msg);
        }
    }

    /**
     * Check if the container is in status withdrawn.
     * 
     * @param additionalMessage
     *            An error message prefix.
     * @throws InvalidStatusException
     *             If the container is not in status withdrawn.
     * @throws WebserverSystemException
     *             In case of an internal error.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     */
    protected void checkWithdrawn(final String additionalMessage)
        throws InvalidStatusException, TripleStoreSystemException,
        WebserverSystemException {

        final String status =
            TripleStoreUtility.getInstance().getPropertiesElements(
                getContainer().getId(), TripleStoreUtility.PROP_PUBLIC_STATUS);
        // In first release, if object is once released no changes are allowed
        if (status.equals(Constants.STATUS_WITHDRAWN)) {
            final String msg =
                "The object is in state '" + Constants.STATUS_WITHDRAWN + "'. "
                    + additionalMessage;
            log.info(msg);
            throw new InvalidStatusException(msg);

        }
    }

    /**
     * @return Returns the utility.
     */
    protected Utility getUtility() {
        if (utility == null) {
            utility = Utility.getInstance();
        }
        return utility;
    }

    /**
     * 
     * @return The foxml renderer.
     */
    public ContainerFoXmlRendererInterface getFoxmlRenderer() {

        if (foxmlRenderer == null) {
            foxmlRenderer = new VelocityXmlContainerFoXmlRenderer();
        }
        return foxmlRenderer;
    }

    /**
     * @return the renderer
     */
    public ContainerRendererInterface getRenderer() 
                    throws WebserverSystemException {
        if (renderer == null) {
            renderer = new VelocityXmlContainerRenderer();
        }
        return renderer;
    }

    /**
     * Check if the requested container version is the latest version.
     * 
     * @throws ReadonlyVersionException
     *             if the requested container version is not the last version
     */
    protected void checkLatestVersion() throws ReadonlyVersionException {
        final String thisVersion = container.getVersionNumber();
        if (thisVersion != null
            && !thisVersion.equals(container.getLatestVersionNumber())) {
            final String message = "Only latest version can be modified.";
            log.info(message);
            throw new ReadonlyVersionException(message);
        }
    }

    /**
     * Check if container is in the specified status.
     * 
     * @param status
     *            A status.
     * 
     * @throws InvalidStatusException
     *             If container is not in the specified status .
     * @throws SystemException
     *             In case of an internal error.
     */
    protected void checkStatus(final String status)
        throws InvalidStatusException, SystemException {
        final String objectStatus =
            TripleStoreUtility.getInstance().getPropertiesElements(
                container.getId(), TripleStoreUtility.PROP_PUBLIC_STATUS);
        if (!(objectStatus.equals(status))) {
            final String msg =
                "Container " + container.getId() + " is in status '"
                    + objectStatus + "'.";
            log.info(msg);
            throw new InvalidStatusException(msg);
        }
    }

    /**
     * Check status of a Context.
     * 
     * @param contextId
     *            The id of the Context.
     * @param status
     *            The expected status of Context.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @throws InvalidStatusException
     *             Thrown if the Context is not in the requested status.
     * @throws TripleStoreSystemException
     *             If the triple store reports an error.
     */
    protected void checkContextStatus(
        final String contextId, final String status)
        throws InvalidStatusException, TripleStoreSystemException,
        WebserverSystemException {

        final String curStatus =
            TripleStoreUtility.getInstance().getPropertiesElements(contextId,
                TripleStoreUtility.PROP_PUBLIC_STATUS);
        // In first release, if object is once released no changes are allowed
        if (!curStatus.equals(status)) {
            final String msg =
                "The Context is in state '" + curStatus
                    + "' and not in status " + status + ".";
            log.info(msg);
            throw new InvalidStatusException(msg);
        }
    }

    /**
     * Check if container is not in the specified status.
     * 
     * @param status
     *            A status.
     * 
     * @throws InvalidStatusException
     *             If container is in the specified status.
     * @throws SystemException
     *             Thrown in case of internal error.
     */
    protected void checkStatusNot(final String status)
        throws InvalidStatusException, SystemException {
        final String objectStatus =
            TripleStoreUtility.getInstance().getPropertiesElements(
                container.getId(), TripleStoreUtility.PROP_PUBLIC_STATUS);
        if (objectStatus.equals(status)) {
            final String msg =
                "Container " + container.getId() + " is in status '"
                    + objectStatus + "'.";
            log.info(msg);
            throw new InvalidStatusException(msg);
        }
    }
}
