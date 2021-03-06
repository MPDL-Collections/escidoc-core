/**
 *
 */
package org.escidoc.core.sm;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import net.sf.oval.constraint.NotNull;

import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ScopeNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import org.escidoc.core.domain.sm.scope.ScopeTypeTO;

/**
 * @author Michael Hoppe
 * @author Marko Voss (marko.voss@fiz-karlsruhe.de)
 */
@Path("/statistic/scope")
public interface ScopeRestService {

    /**
     * Create a new Scope.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The provided XML data in the body is only accepted if the size is less than ESCIDOC_MAX_XML_SIZE.<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Scope is created.</li> <li>The XML data is returned as TO.</li> </ul>
     *
     * @param scopeTO The XML representation of the Scope to be created corresponding to XML-schema "scope.xsd" as TO.
     * @return The XML representation of the created Scope corresponding to XML-schema "scope.xsd" as TO.
     * @throws AuthenticationException      Thrown in case of failed authentication.
     * @throws AuthorizationException       Thrown in case of failed authorization.
     * @throws XmlSchemaValidationException ex
     * @throws XmlCorruptedException        ex
     * @throws MissingMethodParameterException
     *                                      ex
     * @throws SystemException              ex
     */
    @PUT
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<ScopeTypeTO> create(@NotNull ScopeTypeTO scopeTO)
        throws AuthenticationException, AuthorizationException, XmlSchemaValidationException, XmlCorruptedException,
        MissingMethodParameterException, SystemException;

    /**
     * Delete the Scope with the given id.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The Scope must exist<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Scope is accessed using the provided reference.</li> <li>The Scope is
     * deleted.</li> <li>No data is returned.</li> </ul>
     *
     * @param id The Scope ID to be deleted.
     * @throws AuthenticationException Thrown in case of failed authentication.
     * @throws AuthorizationException  Thrown in case of failed authorization.
     * @throws ScopeNotFoundException  e.
     * @throws MissingMethodParameterException
     *                                 e.
     * @throws SystemException         e.
     */
    @DELETE
    @Path("/{id}")
    void delete(@NotNull @PathParam("id") String id)
        throws AuthenticationException, AuthorizationException, ScopeNotFoundException, MissingMethodParameterException,
        SystemException;

    /**
     * Retrieve the Scope with the given id.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The Scope must exist<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Scope is accessed using the provided reference.</li> <li>The XML data is returned
     * as TO.</li> </ul>
     *
     * @param id The Scope ID to be retrieved.
     * @return The XML representation of the retrieved scope corresponding to XML-schema "scope.xsd" as TO.
     * @throws AuthenticationException Thrown in case of failed authentication.
     * @throws AuthorizationException  Thrown in case of failed authorization.
     * @throws ScopeNotFoundException  e.
     * @throws MissingMethodParameterException
     *                                 e.
     * @throws SystemException         e.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<ScopeTypeTO> retrieve(@NotNull @PathParam("id") String id)
        throws AuthenticationException, AuthorizationException, ScopeNotFoundException, MissingMethodParameterException,
        SystemException;

    /**
     * Updates the specified Scope with the provided data.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The provided XML data in the body is only accepted if the size is less than ESCIDOC_MAX_XML_SIZE.<br/>
     * <p/>
     * The Scope must exist<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Scope is accessed using the provided reference.</li> <li>The Scope is
     * updated.</li> <li>The XML data of the updated Scope is returned.</li> </ul>
     *
     * @param id      The Scope ID to be updated.
     * @param scopeTO The XML representation of the Scope to be updated corresponding to XML-schema "scope.xsd" as TO.
     * @return The XML representation of the updated Scope corresponding to XML-schema "scope.xsd" as TO.
     * @throws AuthenticationException      Thrown in case of failed authentication.
     * @throws AuthorizationException       Thrown in case of failed authorization.
     * @throws ScopeNotFoundException       e.
     * @throws MissingMethodParameterException
     *                                      e.
     * @throws XmlSchemaValidationException e.
     * @throws XmlCorruptedException        e.
     * @throws SystemException              e.
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<ScopeTypeTO> update(@NotNull @PathParam("id") String id, @NotNull ScopeTypeTO scopeTO)
        throws AuthenticationException, AuthorizationException, ScopeNotFoundException, MissingMethodParameterException,
        XmlSchemaValidationException, XmlCorruptedException, SystemException;
}