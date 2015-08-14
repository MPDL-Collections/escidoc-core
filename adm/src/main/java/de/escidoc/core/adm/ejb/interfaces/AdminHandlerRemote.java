package de.escidoc.core.adm.ejb.interfaces;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Remote interface for AdminHandler.
 */
public interface AdminHandlerRemote {

    String deleteObjects(String taskParam, SecurityContext securityContext) throws InvalidXmlException,
        SystemException, AuthenticationException, AuthorizationException;

    String deleteObjects(String taskParam, String authHandle, Boolean restAccess) throws InvalidXmlException,
        SystemException, AuthenticationException, AuthorizationException;

    String getPurgeStatus(SecurityContext securityContext) throws SystemException, AuthenticationException,
        AuthorizationException;

    String getPurgeStatus(String authHandle, Boolean restAccess) throws SystemException, AuthenticationException,
        AuthorizationException;

    String getReindexStatus(SecurityContext securityContext) throws SystemException, AuthenticationException,
        AuthorizationException;

    String getReindexStatus(String authHandle, Boolean restAccess) throws SystemException, AuthenticationException,
        AuthorizationException;

    void decreaseReindexStatus(String objectTypeXml, SecurityContext securityContext) throws InvalidXmlException,
        SystemException, AuthenticationException, AuthorizationException;

    void decreaseReindexStatus(String objectTypeXml, String authHandle, Boolean restAccess) throws InvalidXmlException,
        SystemException, AuthenticationException, AuthorizationException;

    String reindex(String clearIndex, String indexNamePrefix, SecurityContext securityContext) throws SystemException,
        InvalidSearchQueryException, AuthenticationException, AuthorizationException;

    String reindex(String clearIndex, String indexNamePrefix, String authHandle, Boolean restAccess)
        throws SystemException, InvalidSearchQueryException, AuthenticationException, AuthorizationException;

    String getIndexConfiguration(SecurityContext securityContext) throws SystemException, AuthenticationException,
        AuthorizationException;

    String getIndexConfiguration(String authHandle, Boolean restAccess) throws SystemException,
        AuthenticationException, AuthorizationException;

    String getRepositoryInfo(SecurityContext securityContext) throws SystemException, AuthenticationException,
        AuthorizationException;

    String getRepositoryInfo(String authHandle, Boolean restAccess) throws SystemException, AuthenticationException,
        AuthorizationException;

    String getRepositoryInfo(final String key, SecurityContext securityContext) throws SystemException,
        AuthenticationException, AuthorizationException;

    String getRepositoryInfo(final String key, String authHandle, Boolean restAccess) throws SystemException,
        AuthenticationException, AuthorizationException;

    String loadExamples(String type, SecurityContext securityContext) throws InvalidSearchQueryException,
        SystemException, AuthenticationException, AuthorizationException;

    String loadExamples(String type, String authHandle, Boolean restAccess) throws InvalidSearchQueryException,
        SystemException, AuthenticationException, AuthorizationException;

}
