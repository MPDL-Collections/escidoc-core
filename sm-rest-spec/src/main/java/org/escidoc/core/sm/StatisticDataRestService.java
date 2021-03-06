/**
 *
 */
package org.escidoc.core.sm;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import org.escidoc.core.domain.sm.sd.StatisticRecordTypeTO;

/**
 * @author Michael Hoppe
 * @author Marko Voss (marko.voss@fiz-karlsruhe.de)
 */
@Path("/statistic/statistic-data")
public interface StatisticDataRestService {

    /**
     * Create a Statistic Record.<br/>
     * <p/>
     * <b>Prerequisites:</b><br/>
     * <p/>
     * The provided XML data in the body is only accepted if the size is less than ESCIDOC_MAX_XML_SIZE.<br/>
     * <p/>
     * <b>Tasks:</b><br/> <ul> <li>The Statistic Record is created. Creation is done asynchronously by writing the
     * Statistic Record into a message-queue.</li> <li>No data is returned.</li> </ul>
     *
     * @param statisticRecordTO The XML representation of the Statistic Record to be created corresponding to XML-schema
     *                          "statistic-data.xsd" as TO.
     * @throws AuthenticationException Thrown in case of failed authentication.
     * @throws AuthorizationException  Thrown in case of failed authorization.
     * @throws MissingMethodParameterException
     *                                 ex
     * @throws SystemException         ex
     */
    @PUT
    @Consumes(MediaType.TEXT_XML)
    void create(StatisticRecordTypeTO statisticRecordTO)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException, SystemException;
}