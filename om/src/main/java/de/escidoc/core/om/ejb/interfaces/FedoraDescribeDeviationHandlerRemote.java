package de.escidoc.core.om.ejb.interfaces;

import java.rmi.RemoteException;
import java.util.Map;

import javax.ejb.EJBObject;

import org.springframework.security.core.context.SecurityContext;

/**
 * Remote interface for FedoraDescribeDeviationHandler.
 */
public interface FedoraDescribeDeviationHandlerRemote extends EJBObject {

    String getFedoraDescription(Map parameters, SecurityContext securityContext) throws Exception, RemoteException;

    String getFedoraDescription(Map parameters, String authHandle, Boolean restAccess) throws Exception,
        RemoteException;

}
