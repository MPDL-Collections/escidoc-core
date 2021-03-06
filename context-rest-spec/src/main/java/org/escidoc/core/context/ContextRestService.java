/**
 *
 */
package org.escidoc.core.context;

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

import net.sf.oval.constraint.NotNull;
import org.escidoc.core.domain.context.*;
import org.escidoc.core.domain.result.ResultTypeTO;
import org.escidoc.core.domain.sru.ResponseTypeTO;
import org.escidoc.core.domain.sru.parameters.SruSearchRequestParametersBean;
import org.escidoc.core.domain.taskparam.status.StatusTaskParamTO;

import de.escidoc.core.common.exceptions.application.invalid.ContextNotEmptyException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.AdminDescriptorNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentModelNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContextNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OperationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OrganizationalUnitNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.StreamNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.ContextNameNotUniqueException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyAttributeViolationException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyElementViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import org.escidoc.core.utils.io.Stream;

/**
 * @author Marko Voss (marko.voss@fiz-karlsruhe.de)
 */
@Path("/ir/context")
public interface ContextRestService {

    @PUT
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<ContextTypeTO> create(@NotNull ContextTypeTO contextTO)
        throws MissingMethodParameterException, ContextNameNotUniqueException, AuthenticationException,
        AuthorizationException, SystemException, ContentModelNotFoundException, ReadonlyElementViolationException,
        MissingAttributeValueException, MissingElementValueException, ReadonlyAttributeViolationException,
        InvalidContentException, OrganizationalUnitNotFoundException, InvalidStatusException, XmlCorruptedException,
        XmlSchemaValidationException;

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<ContextTypeTO> retrieve(@NotNull @PathParam("id") String id)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException;

    @PUT
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<ContextTypeTO> update(@NotNull @PathParam("id") String id, @NotNull ContextTypeTO contextTO)
        throws ContextNotFoundException, MissingMethodParameterException, InvalidContentException,
        InvalidStatusException, AuthenticationException, AuthorizationException, ReadonlyElementViolationException,
        ReadonlyAttributeViolationException, OptimisticLockingException, ContextNameNotUniqueException,
        InvalidXmlException, MissingElementValueException, SystemException;

    @DELETE
    @Path("/{id}")
    void delete(@NotNull @PathParam("id") String id)
        throws ContextNotFoundException, ContextNotEmptyException, MissingMethodParameterException,
        InvalidStatusException, AuthenticationException, AuthorizationException, SystemException;

    @GET
    @Path("/{id}/properties")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<ContextPropertiesTypeTO> retrieveProperties(@NotNull @PathParam("id") String id)
        throws ContextNotFoundException, SystemException;

    @GET
    @Path("/{id}/resources/{resourceName}")
    @Produces(MediaType.WILDCARD)
    Stream retrieveResource(@NotNull @PathParam("id") String id,
        @NotNull @PathParam("resourceName") String resourceName)
        throws OperationNotFoundException, ContextNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException;

    @GET
    @Path("/{id}/resources")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<ContextResourcesTypeTO> retrieveResources(@NotNull @PathParam("id") String id)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException;

    @GET
    @Path("/{id}/resources/members")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<? extends ResponseTypeTO> retrieveMembers(@NotNull @PathParam("id") String id,
        @NotNull @QueryParam("") SruSearchRequestParametersBean queryParam, @QueryParam("x-info5-roleId") String roleId,
        @QueryParam("x-info5-userId") String userId, @QueryParam("x-info5-omitHighlighting") String omitHighlighting)
        throws ContextNotFoundException, MissingMethodParameterException, SystemException;

    @GET
    @Path("/{id}/admin-descriptors/admin-descriptor/{name}")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<AdminDescriptorTypeTO> retrieveAdminDescriptor(@NotNull @PathParam("id") String id,
        @NotNull @PathParam("name") String name)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, AdminDescriptorNotFoundException;

    @GET
    @Path("/{id}/admin-descriptors")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<AdminDescriptorsTypeTO> retrieveAdminDescriptors(@NotNull @PathParam("id") String id)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException;

    @POST
    @Path("/{id}/admin-descriptors/admin-descriptor/")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<AdminDescriptorTypeTO> updateAdminDescriptor(@NotNull @PathParam("id") String id,
        @NotNull AdminDescriptorTypeTO adminDescriptorTO)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException, AdminDescriptorNotFoundException,
        InvalidXmlException;

    @POST
    @Path("/{id}/open")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<ResultTypeTO> open(@NotNull @PathParam("id") String id, @NotNull StatusTaskParamTO statusTaskParam)
        throws ContextNotFoundException, MissingMethodParameterException, InvalidStatusException,
        AuthenticationException, AuthorizationException, OptimisticLockingException, InvalidXmlException,
        SystemException, LockingException, StreamNotFoundException;

    @POST
    @Path("/{id}/close")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<ResultTypeTO> close(@NotNull @PathParam("id") String id, @NotNull StatusTaskParamTO statusTaskParam)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException, InvalidXmlException,
        InvalidStatusException, LockingException, StreamNotFoundException;
}