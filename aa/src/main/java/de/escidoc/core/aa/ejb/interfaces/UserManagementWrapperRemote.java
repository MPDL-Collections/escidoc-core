package de.escidoc.core.aa.ejb.interfaces;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Remote interface for UserManagementWrapper.
 */
public interface UserManagementWrapperRemote extends EJBObject {

    void logout(SecurityContext securityContext) throws AuthenticationException, SystemException, RemoteException;

    void logout(String authHandle, Boolean restAccess) throws AuthenticationException, SystemException, RemoteException;

    void initHandleExpiryTimestamp(String handle, SecurityContext securityContext) throws AuthenticationException,
        SystemException, RemoteException;

    void initHandleExpiryTimestamp(String handle, String authHandle, Boolean restAccess)
        throws AuthenticationException, SystemException, RemoteException;

}
