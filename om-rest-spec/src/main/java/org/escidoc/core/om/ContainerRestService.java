/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the Common Development and Distribution License, Version 1.0
 * only (the "License"). You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at license/ESCIDOC.LICENSE or http://www.escidoc.de/license. See the License
 * for the specific language governing permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each file and include the License file at
 * license/ESCIDOC.LICENSE. If applicable, add the following below this CDDL HEADER, with the fields enclosed by
 * brackets "[]" replaced with your own identifying information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 * Copyright 2006-2011 Fachinformationszentrum Karlsruhe Gesellschaft fuer wissenschaftlich-technische Information mbH
 * and Max-Planck-Gesellschaft zur Foerderung der Wissenschaft e.V. All rights reserved. Use is subject to license
 * terms.
 */

package org.escidoc.core.om;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.escidoc.core.domain.container.ContainerPropertiesTO;
import org.escidoc.core.domain.container.ContainerResourcesTO;
import org.escidoc.core.domain.container.ContainerTO;
import org.escidoc.core.domain.container.structmap.StructMapTO;
import org.escidoc.core.domain.metadatarecords.MdRecordTO;
import org.escidoc.core.domain.metadatarecords.MdRecordsTO;
import org.escidoc.core.domain.ou.ParentsTO;
import org.escidoc.core.domain.relations.RelationsTO;
import org.escidoc.core.domain.result.ResultTO;
import org.escidoc.core.domain.sru.ResponseTypeTO;
import org.escidoc.core.domain.sru.parameters.SruSearchRequestParametersBean;
import org.escidoc.core.domain.taskparam.assignpid.AssignPidTaskParamTO;
import org.escidoc.core.domain.taskparam.members.MembersTaskParamTO;
import org.escidoc.core.domain.taskparam.optimisticlocking.OptimisticLockingTaskParamTO;
import org.escidoc.core.domain.taskparam.relation.RelationTaskParamTO;
import org.escidoc.core.domain.taskparam.status.StatusTaskParamTO;
import org.escidoc.core.domain.version.history.VersionHistoryTO;
import org.escidoc.core.utils.io.Stream;

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
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMdRecordException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ContainerNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentModelNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentRelationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContextNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ItemNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.MdRecordNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OperationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ReferencedResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.RelationPredicateNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.XmlSchemaNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyExistsException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyWithdrawnException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyVersionException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * 
 * @author SWA
 * 
 */
@Path("/ir/container")
public interface ContainerRestService {

    @PUT
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ContainerTO create(final ContainerTO containerTO) throws ContextNotFoundException, ContentModelNotFoundException,
        InvalidContentException, MissingMethodParameterException, MissingAttributeValueException,
        MissingElementValueException, SystemException, ReferencedResourceNotFoundException,
        RelationPredicateNotFoundException, AuthenticationException, AuthorizationException, InvalidStatusException,
        MissingMdRecordException, XmlCorruptedException, XmlSchemaValidationException;

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") String id) throws ContainerNotFoundException, LockingException,
        InvalidStatusException, SystemException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException;

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    ContainerTO retrieve(@PathParam("id") String id) throws AuthenticationException, AuthorizationException,
        MissingMethodParameterException, ContainerNotFoundException, SystemException;

    @PUT
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ContainerTO update(@PathParam("id") String id, ContainerTO containerTO) throws ContainerNotFoundException,
        LockingException, InvalidContentException, MissingMethodParameterException, InvalidXmlException,
        OptimisticLockingException, InvalidStatusException, ReadonlyVersionException, SystemException,
        ReferencedResourceNotFoundException, RelationPredicateNotFoundException, AuthenticationException,
        AuthorizationException, MissingAttributeValueException, MissingMdRecordException;

    @GET
    @Path("/{id}/resources/members")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<? extends ResponseTypeTO> retrieveMembers(@PathParam("id") String id,
        @QueryParam("") SruSearchRequestParametersBean parameters, 
        @QueryParam("x-info5-roleId") String roleId,
        @QueryParam("x-info5-userId") String userId, 
        @QueryParam("x-info5-omitHighlighting") String omitHighlighting) throws InvalidSearchQueryException,
            MissingMethodParameterException, ContainerNotFoundException, SystemException;

    @POST
    @Path("/{id}/members/add")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO addMembers(@PathParam("id") String id, MembersTaskParamTO membersTaskParamTO)
        throws ContainerNotFoundException, LockingException, InvalidContentException, MissingMethodParameterException,
        SystemException, InvalidContextException, AuthenticationException, AuthorizationException,
        OptimisticLockingException, MissingAttributeValueException;

    @POST
    @Path("/{id}/members/remove")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO removeMembers(@PathParam("id") String id, MembersTaskParamTO membersTaskParamTO)
        throws ContextNotFoundException, LockingException, XmlSchemaValidationException, ItemNotFoundException,
        InvalidContextStatusException, InvalidItemStatusException, AuthenticationException, AuthorizationException,
        SystemException, ContainerNotFoundException, InvalidContentException;

    // TODO not supported till version 1.4
    // @POST
    // @Path("/{id}/md-records/md-record")
    // String createMdRecord(@PathParam("id") String id, String xmlData) throws ContainerNotFoundException,
    // InvalidXmlException, LockingException, MissingMethodParameterException, AuthenticationException,
    // AuthorizationException, SystemException;

    @GET
    @Path("/{id}/md-records/md-record/{mdRecordId}")
    @Produces(MediaType.TEXT_XML)
    MdRecordTO retrieveMdRecord(@PathParam("id") String id, @PathParam("mdRecordId") String mdRecordId)
        throws ContainerNotFoundException, MissingMethodParameterException, MdRecordNotFoundException,
        AuthenticationException, AuthorizationException, SystemException;

     @GET
     @Path("/{id}/md-records/md-record/{mdRecordId}/content")
     @Produces(MediaType.TEXT_XML)
     Stream retrieveMdRecordContent(@PathParam("id") String id, @PathParam("mdRecordId") String mdRecordId)
         throws ContainerNotFoundException, MdRecordNotFoundException, AuthenticationException, AuthorizationException,
         MissingMethodParameterException, SystemException;

     @GET
     @Path("/{id}/resources/dc/content")
     @Produces(MediaType.TEXT_XML)
     Stream retrieveDcRecordContent(@PathParam("id") String id) throws ContainerNotFoundException,
     AuthenticationException, AuthorizationException, MissingMethodParameterException, SystemException;

    @POST
    @Path("/{id}/md-records/md-record/{mdRecordId}")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    MdRecordTO updateMetadataRecord(
        @PathParam("id") String id, @PathParam("mdRecordId") String mdRecordId, MdRecordTO mdRecordTO)
        throws ContainerNotFoundException, LockingException, XmlSchemaNotFoundException, MdRecordNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException,
        InvalidXmlException, InvalidStatusException, ReadonlyVersionException;

    @GET
    @Path("/{id}/md-records")
    @Produces(MediaType.TEXT_XML)
    MdRecordsTO retrieveMdRecords(@PathParam("id") String id) throws ContainerNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

    @GET
    @Path("/{id}/properties")
    @Produces(MediaType.TEXT_XML)
    ContainerPropertiesTO retrieveProperties(@PathParam("id") String id) throws ContainerNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

     @GET
     @Path("/{id}/resources")
     @Produces(MediaType.TEXT_XML)
     ContainerResourcesTO retrieveResources(@PathParam("id") String id) throws ContainerNotFoundException,
     MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

    @GET
    @Path("/{id}/resources/{name}")
    @Produces(MediaType.TEXT_XML)
    Stream retrieveResource(@PathParam("id") String id, @PathParam("name") String resourceName,
        @QueryParam("") SruSearchRequestParametersBean parameters, 
        @QueryParam("x-info5-roleId") String roleId,
        @QueryParam("x-info5-userId") String userId, 
        @QueryParam("x-info5-omitHighlighting") String omitHighlighting)
        throws SystemException, ContainerNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, OperationNotFoundException;

    @GET
    @Path("/{id}/relations")
    @Produces(MediaType.TEXT_XML)
    RelationsTO retrieveRelations(@PathParam("id") String id) throws ContainerNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

    @GET
    @Path("/{id}/struct-map")
    @Produces(MediaType.TEXT_XML)
    StructMapTO retrieveStructMap(@PathParam("id") String id) throws ContainerNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

    @GET
    @Path("/{id}/resources/version-history")
    @Produces(MediaType.TEXT_XML)
    VersionHistoryTO retrieveVersionHistory(@PathParam("id") String id) throws ContainerNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

    @GET
    @Path("/{id}/resources/parents")
    @Produces(MediaType.TEXT_XML)
    ParentsTO retrieveParents(@PathParam("id") String id) throws AuthenticationException, AuthorizationException,
        MissingMethodParameterException, ContainerNotFoundException, SystemException;

    @POST
    @Path("/{id}/release")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO release(@PathParam("id") String id, StatusTaskParamTO statusTaskParamTO)
        throws ContainerNotFoundException, LockingException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, InvalidStatusException, SystemException, OptimisticLockingException,
        ReadonlyVersionException, InvalidXmlException;

    @POST
    @Path("/{id}/submit")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO submit(@PathParam("id") String id, StatusTaskParamTO statusTaskParamTO) throws ContainerNotFoundException,
        LockingException, MissingMethodParameterException, AuthenticationException, AuthorizationException,
        InvalidStatusException, SystemException, OptimisticLockingException, ReadonlyVersionException,
        InvalidXmlException;

    @POST
    @Path("/{id}/revise")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO revise(@PathParam("id") String id, StatusTaskParamTO statusTaskParamTO) throws ContainerNotFoundException,
        LockingException, MissingMethodParameterException, InvalidStatusException, SystemException,
        OptimisticLockingException, ReadonlyVersionException, XmlCorruptedException;

    @POST
    @Path("/{id}/withdraw")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO withdraw(@PathParam("id") String id, StatusTaskParamTO statusTaskParamTO)
        throws ContainerNotFoundException, LockingException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, InvalidStatusException, SystemException, OptimisticLockingException,
        AlreadyWithdrawnException, ReadonlyVersionException, InvalidXmlException;

    @POST
    @Path("/{id}/lock")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO lock(@PathParam("id") String id, OptimisticLockingTaskParamTO optimisticLockingTaskParamTO)
        throws ContainerNotFoundException, LockingException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException, InvalidStatusException,
        InvalidXmlException;

    @POST
    @Path("/{id}/unlock")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO unlock(@PathParam("id") String id, OptimisticLockingTaskParamTO optimisticLockingTaskParamTO)
        throws ContainerNotFoundException, LockingException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException, InvalidStatusException,
        InvalidXmlException;

    @POST
    @Path("/{id}/content-relations/add")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO addContentRelations(@PathParam("id") String id, RelationTaskParamTO relationTaskParamTO)
        throws SystemException, ContainerNotFoundException, OptimisticLockingException,
        ReferencedResourceNotFoundException, RelationPredicateNotFoundException, AlreadyExistsException,
        InvalidStatusException, InvalidXmlException, MissingElementValueException, LockingException,
        ReadonlyVersionException, InvalidContentException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException;

    @POST
    @Path("/{id}/content-relations/remove")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO removeContentRelations(@PathParam("id") String id, RelationTaskParamTO relationTaskParamTO)
        throws SystemException, ContainerNotFoundException, OptimisticLockingException, InvalidStatusException,
        MissingElementValueException, InvalidXmlException, ContentRelationNotFoundException, LockingException,
        ReadonlyVersionException, AuthenticationException, AuthorizationException;

    @POST
    @Path("/{id}/assign-object-pid")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO assignObjectPid(@PathParam("id") String id, AssignPidTaskParamTO assignPidTaskParamTO)
        throws InvalidStatusException, ContainerNotFoundException, LockingException, MissingMethodParameterException,
        OptimisticLockingException, SystemException, InvalidXmlException;

    @POST
    @Path("/{id}/assign-version-pid")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    ResultTO assignVersionPid(@PathParam("id") String id, AssignPidTaskParamTO assignPidTaskParamTO)
        throws ContainerNotFoundException, LockingException, MissingMethodParameterException, SystemException,
        OptimisticLockingException, InvalidStatusException, XmlCorruptedException, ReadonlyVersionException;

}