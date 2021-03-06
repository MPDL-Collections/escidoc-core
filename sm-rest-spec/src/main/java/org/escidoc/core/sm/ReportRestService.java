/**
 *
 */
package org.escidoc.core.sm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import net.sf.oval.constraint.NotNull;
import org.escidoc.core.domain.sm.report.ReportTypeTO;

import de.escidoc.core.common.exceptions.application.invalid.InvalidSqlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ReportDefinitionNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import org.escidoc.core.domain.sm.report.parameter.ReportParametersTypeTO;

/**
 * @author Michael Hoppe
 * @author Marko Voss (marko.voss@fiz-karlsruhe.de)
 */
@Path("/statistic/report")
public interface ReportRestService {

    /**
     * Retrieve a Statistic Report.<br/>
     * <p/>
     * Parameter for the Method is an xml corresponding to XML-schema "report-parameters.xsd" as TO.<br/>
     * <p/>
     * In this xml you can define:<br/>
     * <p/>
     * <ul> <li>The Report Definition the Statistic Report is based on</li> <li>Additional parameters that fill the
     * placeholders in the sql-statement of the Report Definition.</li> </ul>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The Report Definition must exist<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Report Definition is accessed using the provided reference.</li> <li>The
     * Statistic Report is created.</li> <li>The XML data is returned as TO.</li> </ul>
     *
     * @param reportParametersTO The xml with parameters corresponding to XML-schema "report-parameters.xsd" as TO.
     * @return The XML representation of the retrieved Statistic Report corresponding to XML-schema "report.xsd" as TO.
     * @throws AuthenticationException      Thrown in case of failed authentication.
     * @throws AuthorizationException       Thrown in case of failed authorization.
     * @throws XmlCorruptedException        Thrown in case of provided invalid xml.
     * @throws XmlSchemaValidationException Thrown in case of provided xml not schema conform.
     * @throws ReportDefinitionNotFoundException
     *                                      e.
     * @throws MissingMethodParameterException
     *                                      e.
     * @throws InvalidSqlException          e.
     * @throws SystemException              e.
     */
    @POST
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    JAXBElement<ReportTypeTO> retrieve(@NotNull ReportParametersTypeTO reportParametersTO)
        throws AuthenticationException, AuthorizationException, XmlCorruptedException, XmlSchemaValidationException,
        ReportDefinitionNotFoundException, MissingMethodParameterException, InvalidSqlException, SystemException;
}