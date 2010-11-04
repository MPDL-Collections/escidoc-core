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
package de.escidoc.core.om.service;

import java.util.Map;

import de.escidoc.core.common.business.fedora.EscidocBinaryContent;
import de.escidoc.core.common.business.filter.SRURequestParameters;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContextException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContextStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidItemStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingContentException;
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMdRecordException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ContainerNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentModelNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentRelationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContextNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.FileNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ItemNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.MdRecordNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OperationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ReferencedResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.RelationPredicateNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.XmlSchemaNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyDeletedException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyExistsException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyWithdrawnException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyAttributeViolationException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyElementViolationException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyVersionException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.om.service.interfaces.ContainerHandlerInterface;

/**
 * A container resource handler.
 * 
 * @spring.bean id="service.ContainerHandler" scope="prototype"
 * @interface 
 *            class="de.escidoc.core.om.service.interfaces.ContainerHandlerInterface"
 * @author TTE
 * @service
 * @om
 */
public class ContainerHandler implements ContainerHandlerInterface {

    private de.escidoc.core.om.business.interfaces.ContainerHandlerInterface handler;

    /**
     * Injects the container handler.
     * 
     * @param containerHandler
     *            The container handler bean to inject.
     * 
     * @spring.property ref="business.FedoraContainerHandler"
     * @service.exclude
     * @om
     */
    public void setContainerHandler(
        final de.escidoc.core.om.business.interfaces.ContainerHandlerInterface containerHandler) {

        this.handler = containerHandler;
    }

    // CHECKSTYLE:JAVADOC-OFF

    // FIXME: exception handling
    /**
     * See Interface for functional description.
     * 
     * @param xmlData
     * @return
     * @see de.escidoc.core.common.service.interfaces.ResourceHandlerInterface
     *      #create(java.lang.String)
     */
    public String create(final String xmlData) throws ContextNotFoundException,
        ContentModelNotFoundException, InvalidContentException,
        MissingMethodParameterException, XmlCorruptedException,
        MissingAttributeValueException, MissingElementValueException,
        SystemException, ReferencedResourceNotFoundException,
        RelationPredicateNotFoundException, AuthenticationException,
        AuthorizationException, InvalidStatusException,
        MissingMdRecordException, XmlSchemaValidationException {

        return handler.create(xmlData);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @throws InvalidStatusException
     * @see de.escidoc.core.common.service.interfaces.ResourceHandlerInterface
     *      #delete(java.lang.String)
     * @om
     */
    public void delete(final String id) throws ContainerNotFoundException,
        LockingException, InvalidStatusException, SystemException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException {

        handler.delete(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @see de.escidoc.core.common.service.interfaces.ResourceHandlerInterface
     *      #retrieve(java.lang.String)
     * @om
     */
    public String retrieve(final String id)
        throws MissingMethodParameterException, ContainerNotFoundException,
        AuthenticationException, AuthorizationException, SystemException {

        return handler.retrieve(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param xmlData
     * @return
     * @throws ContextNotFoundException
     * @throws ContentModelNotFoundException
     * @throws FileNotFoundException
     * @throws InvalidXmlException
     * @throws OptimisticLockingException
     * @throws InvalidStatusException
     * @throws ReadonlyAttributeViolationException
     * @see de.escidoc.core.common.service.interfaces.ResourceHandlerInterface
     *      #update(java.lang.String, java.lang.String)
     * @om
     */
    public String update(final String id, final String xmlData)
        throws ContainerNotFoundException, LockingException,
        InvalidContentException, MissingMethodParameterException,
        InvalidXmlException, OptimisticLockingException,
        InvalidStatusException, ReadonlyVersionException, SystemException,
        ReferencedResourceNotFoundException,
        RelationPredicateNotFoundException, AuthenticationException,
        AuthorizationException, MissingAttributeValueException,
        MissingMdRecordException {

        return handler.update(id, xmlData);
    }

    //
    // Subresources
    //

    //
    // Subresource - members
    //

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param filter
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface#retrieveMembers(java.lang.String,
     *      java.util.Map)
     */
    public String retrieveMembers(
        final String id, final Map<String, String[]> filter)
        throws ContainerNotFoundException, InvalidSearchQueryException,
        MissingMethodParameterException, SystemException {

        return handler.retrieveMembers(id, new SRURequestParameters(filter));
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param filter
     * @return List of Tocs
     * 
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface#retrieveTocs(java.lang.String,
     *      java.util.Map)
     */
    public String retrieveTocs(
        final String id, final Map<String, String[]> filter)
        throws ContainerNotFoundException, InvalidXmlException,
        InvalidSearchQueryException, MissingMethodParameterException,
        SystemException {

        return handler.retrieveTocs(id, new SRURequestParameters(filter));
    }

    public String addMembers(final String id, final String taskParam)
        throws ContainerNotFoundException, LockingException,
        InvalidContentException, OptimisticLockingException,
        MissingMethodParameterException, SystemException,
        InvalidContextException, AuthenticationException,
        AuthorizationException, MissingAttributeValueException {

        return handler.addMembers(id, taskParam);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param taskParam
     * @return last-modification-date within XML (result.xsd)
     * 
     * @throws ContainerNotFoundException
     * @throws LockingException
     * @throws InvalidContentException
     * @throws OptimisticLockingException
     * @throws SystemException
     * @throws InvalidContextException
     * @throws MissingAttributeValueException
     */
    public String addTocs(final String id, final String taskParam)
        throws ContainerNotFoundException, LockingException,
        InvalidContentException, OptimisticLockingException,
        MissingMethodParameterException, SystemException,
        InvalidContextException, AuthenticationException,
        AuthorizationException, MissingAttributeValueException {

        return handler.addTocs(id, taskParam);
    }

    public String removeMembers(final String id, final String taskParam)
        throws ContextNotFoundException, LockingException,
        XmlSchemaValidationException, ItemNotFoundException,
        InvalidContextStatusException, InvalidItemStatusException,
        AuthenticationException, AuthorizationException, SystemException,
        ContainerNotFoundException, InvalidContentException {

        return handler.removeMembers(id, taskParam);
    }

    //
    // Subresource - metadata record
    //

    /**
     * See Interface for functional description.
     * 
     * Deprecated because of inconsistent naming. Use createMdRecord instead of.
     * 
     * @param id
     * @param xmlData
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #createMetadataRecord(java.lang.String, java.lang.String)
     * @service.exclude
     */
    @Deprecated
    public String createMetadataRecord(final String id, final String xmlData)
        throws ContainerNotFoundException, InvalidXmlException,
        LockingException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {

        return handler.createMdRecord(id, xmlData);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param xmlData
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #createMdRecord(java.lang.String, java.lang.String)
     * @service.exclude
     */
    public String createMdRecord(final String id, final String xmlData)
        throws ContainerNotFoundException, InvalidXmlException,
        LockingException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {

        return handler.createMdRecord(id, xmlData);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param mdRecordId
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveMdRecord(java.lang.String, java.lang.String)
     * @om
     */
    public String retrieveMdRecord(final String id, final String mdRecordId)
        throws ContainerNotFoundException, MissingMethodParameterException,
        MdRecordNotFoundException, AuthenticationException,
        AuthorizationException, SystemException {

        return handler.retrieveMdRecord(id, mdRecordId);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param mdRecordId
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveMdRecordContent(java.lang.String, java.lang.String)
     * @axis.exclude
     * @om
     */
    public String retrieveMdRecordContent(
        final String id, final String mdRecordId)
        throws ContainerNotFoundException, MdRecordNotFoundException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException, SystemException {

        return handler.retrieveMdRecordContent(id, mdRecordId);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveDcRecordContent(java.lang.String)
     * @axis.exclude
     * @om
     */
    public String retrieveDcRecordContent(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        return handler.retrieveDcRecordContent(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param mdRecordId
     * @param xmlData
     * @return
     * @throws InvalidStatusException
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #updateMetadataRecord(java.lang.String, java.lang.String,
     *      java.lang.String)
     * @service.exclude
     * @om
     */
    public String updateMetadataRecord(
        final String id, final String mdRecordId, final String xmlData)
        throws ContainerNotFoundException, LockingException,
        XmlSchemaNotFoundException, MdRecordNotFoundException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, InvalidXmlException,
        InvalidStatusException, ReadonlyVersionException {

        return handler.updateMetadataRecord(id, mdRecordId, xmlData);
    }

    //
    // Subresource - metadata records
    //

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveMdRecords(java.lang.String)
     * @om
     */
    public String retrieveMdRecords(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {

        return handler.retrieveMdRecords(id);
    }

    //
    // Subresource - properties
    //

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveProperties(java.lang.String)
     * @om
     */
    public String retrieveProperties(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {

        return handler.retrieveProperties(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveResources(java.lang.String)
     * @axis.exclude
     * @om
     */
    public String retrieveResources(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {

        return handler.retrieveResources(id);
    }

    /**
     * @param id
     * @param resourceName
     * @return
     * @throws SystemException
     * @throws ContainerNotFoundException
     * @throws MissingMethodParameterException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws OperationNotFoundException
     * 
     * @axis.exclude
     * 
     */
    public EscidocBinaryContent retrieveResource(
        final String id, final String resourceName,
        final Map<String, String[]> parameters) throws SystemException,
        ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException,
        OperationNotFoundException {
        return handler.retrieveResource(id, resourceName, parameters);
    }

    public String retrieveStructMap(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {

        return handler.retrieveStructMap(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @seede.escidoc.core.om.service.interfaces.ContainerHandlerInterface#
     * retrieveVersions(java.lang.String)
     */
    public String retrieveVersionHistory(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        return handler.retrieveVersionHistory(id);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveParents(java.lang.String)
     */
    public String retrieveParents(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        String result = handler.retrieveParents(id);
        return result;
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @return
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveContentRelations(java.lang.String)
     * @om
     */
    public String retrieveRelations(final String id)
        throws ContainerNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        String result = handler.retrieveRelations(id);
        return result;
    }

    //
    // Subresource - status
    //

    public String release(final String id, final String lastModified)
        throws ContainerNotFoundException, LockingException,
        MissingMethodParameterException, AuthenticationException,
        ReadonlyVersionException, AuthorizationException,
        InvalidStatusException, SystemException, OptimisticLockingException,
        InvalidXmlException {

        return handler.release(id, lastModified);
    }

    public String submit(final String id, final String lastModified)
        throws ContainerNotFoundException, LockingException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException, InvalidStatusException, SystemException,
        OptimisticLockingException, ReadonlyVersionException,
        InvalidXmlException {

        return handler.submit(id, lastModified);
    }

    public String withdraw(final String id, final String lastModified)
        throws ContainerNotFoundException, LockingException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException, InvalidStatusException, SystemException,
        OptimisticLockingException, AlreadyWithdrawnException,
        ReadonlyVersionException, InvalidXmlException {

        return handler.withdraw(id, lastModified);
    }

    public String revise(final String id, final String lastModified)
        throws ContainerNotFoundException, LockingException,
        MissingMethodParameterException, InvalidStatusException,
        SystemException, OptimisticLockingException, ReadonlyVersionException,
        XmlCorruptedException {

        return handler.revise(id, lastModified);
    }

    public String lock(final String id, final String lastModified)
        throws ContainerNotFoundException, LockingException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException,
        InvalidStatusException, InvalidXmlException {

        return handler.lock(id, lastModified);
    }

    public String unlock(final String id, final String lastModified)
        throws ContainerNotFoundException, LockingException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException,
        InvalidStatusException, InvalidXmlException {

        return handler.unlock(id, lastModified);
    }

    public String moveToContext(final String containerId, final String taskParam)
        throws ContainerNotFoundException, ContextNotFoundException,
        InvalidContentException, LockingException,
        MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {

        return handler.moveToContext(containerId, taskParam);
    }

    public String createItem(final String containerId, final String xmlData)
        throws ContainerNotFoundException, MissingContentException,
        ContextNotFoundException, ContentModelNotFoundException,
        ReadonlyElementViolationException, MissingAttributeValueException,
        MissingElementValueException, ReadonlyAttributeViolationException,
        MissingMethodParameterException, InvalidXmlException,
        FileNotFoundException, LockingException, InvalidContentException,
        InvalidContextException, RelationPredicateNotFoundException,
        ReferencedResourceNotFoundException, SystemException,
        AuthenticationException, AuthorizationException,
        MissingMdRecordException, InvalidStatusException {

        return handler.createItem(containerId, xmlData);
    }

    public String createContainer(final String containerId, final String xmlData)
        throws MissingMethodParameterException, ContainerNotFoundException,
        LockingException, ContextNotFoundException,
        ContentModelNotFoundException, InvalidContentException,
        InvalidXmlException, MissingAttributeValueException,
        MissingElementValueException, AuthenticationException,
        AuthorizationException, InvalidContextException,
        RelationPredicateNotFoundException, InvalidStatusException,
        ReferencedResourceNotFoundException, SystemException,
        MissingMdRecordException {

        return handler.createContainer(containerId, xmlData);
    }

    /**
     * See Interface for functional description.
     * 
     * @param filter
     * @return
     * @throws InvalidXmlException
     * @throws MissingMethodParameterException
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws SystemException
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface
     *      #retrieveContainers(java.util.Map)
     */
    public String retrieveContainers(final Map<String, String[]> filter)
        throws MissingMethodParameterException, InvalidSearchQueryException,
        InvalidXmlException, SystemException {

        return handler.retrieveContainers(new SRURequestParameters(filter));
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param param
     * @return
     * @throws SystemException
     * @throws ContainerNotFoundException
     * @throws OptimisticLockingException
     * @throws ReferencedResourceNotFoundException
     * @throws RelationPredicateNotFoundException
     * @throws AlreadyExistsException
     * @throws InvalidContentException
     * @throws InvalidStatusException
     * @throws InvalidXmlException
     * @throws MissingElementValueException
     * @throws LockingException
     * @throws ReadonlyVersionException
     *             cf. Interface
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface#addContentRelations(java.lang.String,
     *      java.lang.String)
     * 
     * @om
     */
    public String addContentRelations(final String id, final String param)
        throws SystemException, ContainerNotFoundException,
        OptimisticLockingException, ReferencedResourceNotFoundException,
        RelationPredicateNotFoundException, AlreadyExistsException,
        InvalidStatusException, InvalidXmlException,
        MissingElementValueException, LockingException,
        ReadonlyVersionException, InvalidContentException,
        AuthenticationException, AuthorizationException,
        MissingMethodParameterException {

        return handler.addContentRelations(id, param);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param param
     * @throws SystemException
     * @throws ContainerNotFoundException
     * @throws OptimisticLockingException
     * @throws InvalidStatusException
     * @throws MissingElementValueException
     * @throws InvalidContentException
     * @throws InvalidXmlException
     * @throws ContentRelationNotFoundException
     * @throws AlreadyDeletedException
     * @throws LockingException
     * @throws ReadonlyVersionException
     *             cf. Interface
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface#removeContentRelations(java.lang.String,
     *      java.lang.String)
     * 
     * @om
     */
    public String removeContentRelations(final String id, final String param)
        throws SystemException, ContainerNotFoundException,
        OptimisticLockingException, InvalidStatusException,
        MissingElementValueException, InvalidXmlException,
        ContentRelationNotFoundException, LockingException,
        ReadonlyVersionException, AuthenticationException,
        AuthorizationException {

        return handler.removeContentRelations(id, param);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param param
     * @return
     * @throws InvalidStatusException
     * @throws ContainerNotFoundException
     * @throws LockingException
     * @throws MissingMethodParameterException
     * @throws OptimisticLockingException
     * @throws SystemException
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface#assignObjectPid(java.lang.String,
     *      java.lang.String)
     */
    public String assignObjectPid(final String id, final String param)
        throws InvalidStatusException, ContainerNotFoundException,
        LockingException, MissingMethodParameterException,
        OptimisticLockingException, SystemException, InvalidXmlException {

        return handler.assignObjectPid(id, param);
    }

    /**
     * See Interface for functional description.
     * 
     * @param id
     * @param param
     * @return
     * @throws ContainerNotFoundException
     * @throws LockingException
     * @throws MissingMethodParameterException
     * @throws SystemException
     * @throws OptimisticLockingException
     * @throws InvalidStatusException
     * @throws XmlCorruptedException
     * @throws ReadonlyVersionException
     *             Thrown if a provided container version id is not a latest
     *             version.
     * @see de.escidoc.core.om.service.interfaces.ContainerHandlerInterface#assignVersionPid(java.lang.String,
     *      java.lang.String)
     */
    public String assignVersionPid(final String id, final String param)
        throws ContainerNotFoundException, LockingException,
        MissingMethodParameterException, SystemException,
        OptimisticLockingException, InvalidStatusException,
        XmlCorruptedException, ReadonlyVersionException {

        return handler.assignVersionPid(id, param);
    }

}
