package de.escidoc.core.aa.ejb.interfaces;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Local interface for UserManagementWrapper.
 */
public interface UserManagementWrapperLocal {

    void logout(SecurityContext securityContext) throws AuthenticationException, SystemException;

    void logout(String authHandle, Boolean restAccess) throws AuthenticationException, SystemException;

    void initHandleExpiryTimestamp(String handle, SecurityContext securityContext) throws AuthenticationException,
        SystemException;

    void initHandleExpiryTimestamp(String handle, String authHandle, Boolean restAccess)
        throws AuthenticationException, SystemException;

    void create() throws CreateException;
}
