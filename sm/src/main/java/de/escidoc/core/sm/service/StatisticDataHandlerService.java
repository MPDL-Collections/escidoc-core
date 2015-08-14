package de.escidoc.core.sm.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Service endpoint interface for StatisticDataHandler.
 */
public interface StatisticDataHandlerService extends Remote {

    void create(String xmlData, SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException, SystemException, RemoteException;

    void create(String xmlData, String authHandle, Boolean restAccess) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException, SystemException, RemoteException;

}
