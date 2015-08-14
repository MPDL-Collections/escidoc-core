package de.escidoc.core.sm.ejb.interfaces;

import java.util.Map;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSqlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ReportDefinitionNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ScopeNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.ScopeContextViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Remote interface for ReportDefinitionHandler.
 */
public interface ReportDefinitionHandlerRemote {

    String create(String xmlData, SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, XmlSchemaValidationException, XmlCorruptedException, InvalidSqlException,
        MissingMethodParameterException, ScopeNotFoundException, ScopeContextViolationException, SystemException;

    String create(String xmlData, String authHandle, Boolean restAccess) throws AuthenticationException,
        AuthorizationException, XmlSchemaValidationException, XmlCorruptedException, InvalidSqlException,
        MissingMethodParameterException, ScopeNotFoundException, ScopeContextViolationException, SystemException;

    void delete(String id, SecurityContext securityContext) throws AuthenticationException, AuthorizationException,
        ReportDefinitionNotFoundException, MissingMethodParameterException, SystemException;

    void delete(String id, String authHandle, Boolean restAccess) throws AuthenticationException,
        AuthorizationException, ReportDefinitionNotFoundException, MissingMethodParameterException, SystemException;

    String retrieve(String id, SecurityContext securityContext) throws AuthenticationException, AuthorizationException,
        ReportDefinitionNotFoundException, MissingMethodParameterException, SystemException;

    String retrieve(String id, String authHandle, Boolean restAccess) throws AuthenticationException,
        AuthorizationException, ReportDefinitionNotFoundException, MissingMethodParameterException, SystemException;

    String retrieveReportDefinitions(Map filter, SecurityContext securityContext) throws InvalidSearchQueryException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException;

    String retrieveReportDefinitions(Map filter, String authHandle, Boolean restAccess)
        throws InvalidSearchQueryException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException;

    String update(String id, String xmlData, SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, ReportDefinitionNotFoundException, MissingMethodParameterException,
        ScopeNotFoundException, InvalidSqlException, ScopeContextViolationException, XmlSchemaValidationException,
        XmlCorruptedException, SystemException;

    String update(String id, String xmlData, String authHandle, Boolean restAccess) throws AuthenticationException,
        AuthorizationException, ReportDefinitionNotFoundException, MissingMethodParameterException,
        ScopeNotFoundException, InvalidSqlException, ScopeContextViolationException, XmlSchemaValidationException,
        XmlCorruptedException, SystemException;

    void create() throws CreateException;
}
