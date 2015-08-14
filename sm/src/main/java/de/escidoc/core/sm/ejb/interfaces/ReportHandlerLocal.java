package de.escidoc.core.sm.ejb.interfaces;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.invalid.InvalidSqlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ReportDefinitionNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Local interface for ReportHandler.
 */
public interface ReportHandlerLocal {

    String retrieve(String xml, SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, XmlCorruptedException, XmlSchemaValidationException, ReportDefinitionNotFoundException,
        MissingMethodParameterException, InvalidSqlException, SystemException;

    String retrieve(String xml, String authHandle, Boolean restAccess) throws AuthenticationException,
        AuthorizationException, XmlCorruptedException, XmlSchemaValidationException, ReportDefinitionNotFoundException,
        MissingMethodParameterException, InvalidSqlException, SystemException;

    void create() throws CreateException;
}
