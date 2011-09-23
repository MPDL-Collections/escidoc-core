/**
 * 
 */
package de.escidoc.core.sm;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.escidoc.core.domain.sm.ReportDefinitionListTO;
import org.escidoc.core.utils.io.MimeTypes;

import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * @author Michael Hoppe
 * 
 */

@Path("/")
@Produces(MimeTypes.TEXT_XML)
@Consumes(MimeTypes.TEXT_XML)
public interface ReportDefinitionsRestService {

    /**
     * FIXME Map
     */
    @GET
    ReportDefinitionListTO retrieveReportDefinitions(Map<String, String[]> filter) throws InvalidSearchQueryException,
    MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

}