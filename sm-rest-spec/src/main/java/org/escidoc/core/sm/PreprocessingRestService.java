/**
 *
 */
package org.escidoc.core.sm;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import net.sf.oval.constraint.NotNull;

import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import org.escidoc.core.domain.sm.pi.PreprocessingInformationTypeTO;

/**
 * @author Michael Hoppe
 * @author Marko Voss (marko.voss@fiz-karlsruhe.de)
 */
@Path("/statistic/preprocessing")
public interface PreprocessingRestService {

    /**
     * Preprocess Statistic raw data.<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>Extract startDate and endDate.</li> <li>Get data from database-table containing the
     * rae statistic-data (aa.statistic_data).</li> <li>Preprocess data according to aggregation-table desciption in
     * aggregation-definition.</li> <li>Write data into aggregation-tables.</li> </ul>
     *
     * @param id                         Aggregation Definition ID to preprocess.
     * @param preprocessingInformationTO The XML representation of the Preprocessing Information to be processed
     *                                   corresponding to XML-schema "preprocessing-information.xsd" as TO.
     * @throws AuthenticationException      Thrown in case of failed authentication.
     * @throws AuthorizationException       Thrown in case of failed authorization.
     * @throws XmlSchemaValidationException ex
     * @throws XmlCorruptedException        ex
     * @throws MissingMethodParameterException
     *                                      ex
     * @throws SystemException              ex
     */
    @POST
    @Path("/{id}")
    @Consumes(MediaType.TEXT_XML)
    void preprocess(@NotNull @PathParam("id") String id, @NotNull PreprocessingInformationTypeTO preprocessingInformationTO)
        throws AuthenticationException, AuthorizationException, XmlSchemaValidationException, XmlCorruptedException,
        MissingMethodParameterException, SystemException;
}