/**
 *
 */
package org.escidoc.core.sm;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import net.sf.oval.constraint.NotNull;
import org.escidoc.core.domain.sru.ResponseTypeTO;
import org.escidoc.core.domain.sru.parameters.SruSearchRequestParametersBean;

import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * @author Michael Hoppe
 * @author Marko Voss (marko.voss@fiz-karlsruhe.de)
 */
@Path("/statistic/scopes")
public interface ScopesRestService {

    /**
     * Retrieve Scopes the user is allowed to see. For further information about the filter-names, please see the
     * explain-plan.<br/>
     * <p/>
     * Returns list of Scopes the user may see.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>All Scopes are accessed.</li> <li>The XML data is returned as TO.</li> </ul>
     *
     * @param parameters The Standard SRU Get-Parameters as Object
     * @return The XML representation of the Scopes corresponding to SRW schema. The list only contains these Scopes the
     *         user is allowed to see as TO.
     * @throws MissingMethodParameterException
     *                                     If the parameter filter is not given.
     * @throws InvalidSearchQueryException thrown if the given search query could not be translated into a SQL query
     * @throws AuthenticationException     Thrown in case of failed authentication.
     * @throws AuthorizationException      Thrown in case of failed authorization.
     * @throws SystemException             e.
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    JAXBElement<? extends ResponseTypeTO> retrieveScopes(
        @NotNull @QueryParam("") SruSearchRequestParametersBean parameters)
        throws InvalidSearchQueryException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException;
}