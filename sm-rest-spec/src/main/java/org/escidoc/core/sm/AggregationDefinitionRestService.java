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
import de.escidoc.core.common.exceptions.application.notfound.AggregationDefinitionNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ScopeNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import org.escidoc.core.domain.sm.ad.AggregationDefinitionTypeTO;

/**
 * @author Michael Hoppe
 * @author Marko Voss (marko.voss@fiz-karlsruhe.de)
 */
@Path("/statistic/aggregation-definition")
public interface AggregationDefinitionRestService {

    /**
     * Creates new Aggregation Definition with given xmlData.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>Validation of the delivered XML-data</li> <li>Create the Aggregation Definition</li>
     * <li>Create associated Aggregation Tables in database.</li> <li>The XML data is returned.</li> </ul>
     *
     * @param aggregationDefinitionTO The XML representation of the Aggregation Definition to be created corresponding
     *                                to XML-schema "aggregation-definition.xsd" as TO.
     * @return The XML representation of the created Aggregation Definition corresponding to XML-schema
     *         "aggregation-definition.xsd" as TO.
     * @throws AuthenticationException      Thrown in case of failed authentication.
     * @throws AuthorizationException       Thrown in case of failed authorization.
     * @throws XmlSchemaValidationException ex
     * @throws XmlCorruptedException        ex
     * @throws MissingMethodParameterException
     *                                      ex
     * @throws ScopeNotFoundException       ex
     * @throws SystemException              ex
     */
    @PUT
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<AggregationDefinitionTypeTO> create(@NotNull AggregationDefinitionTypeTO aggregationDefinitionTO)
        throws AuthenticationException, AuthorizationException, XmlSchemaValidationException, XmlCorruptedException,
        MissingMethodParameterException, ScopeNotFoundException, SystemException;

    /**
     * Delete Aggregation Definition.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The Aggregation Definition must exist<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Aggregation Definition is accessed using the provided reference.</li> <li>The
     * Aggregation Definition is deleted.</li> <li>Associated Aggregation Tables are deleted.</li> <li>No data is
     * returned.</li> </ul>
     *
     * @param id The Aggregation Definition ID.
     * @throws AuthenticationException Thrown in case of failed authentication.
     * @throws AuthorizationException  Thrown in case of failed authorization.
     * @throws AggregationDefinitionNotFoundException
     *                                 e.
     * @throws MissingMethodParameterException
     *                                 e.
     * @throws SystemException         e.
     */
    @DELETE
    @Path("/{id}")
    void delete(@NotNull @PathParam("id") String id)
        throws AuthenticationException, AuthorizationException, AggregationDefinitionNotFoundException,
        MissingMethodParameterException, SystemException;

    /**
     * Retrieve a specified Aggregation Definition.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The Aggregation Definition must exist<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Aggregation Definition is accessed using the provided reference.</li> <li>The XML
     * data is returned.</li> </ul>
     *
     * @param id The Aggregation Definition ID.
     * @return The XML representation of the Aggregation Definition corresponding to XML-schema
     *         "aggregation-definition.xsd" as TO.
     * @throws AuthenticationException Thrown in case of failed authentication.
     * @throws AuthorizationException  Thrown in case of failed authorization.
     * @throws AggregationDefinitionNotFoundException
     *                                 e.
     * @throws MissingMethodParameterException
     *                                 e.
     * @throws SystemException         e.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    JAXBElement<AggregationDefinitionTypeTO> retrieve(@NotNull @PathParam("id") String id)
        throws AuthenticationException, AuthorizationException, AggregationDefinitionNotFoundException,
        MissingMethodParameterException, SystemException;
}