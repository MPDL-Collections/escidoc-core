package de.escidoc.core.om.ejb.interfaces;

import java.util.Map;

import javax.ejb.EJBLocalObject;

import org.springframework.security.core.context.SecurityContext;

/**
 * Local interface for FedoraDescribeDeviationHandler.
 */
public interface FedoraDescribeDeviationHandlerLocal extends EJBLocalObject {

    String getFedoraDescription(Map parameters, SecurityContext securityContext) throws Exception;

    String getFedoraDescription(Map parameters, String authHandle, Boolean restAccess) throws Exception;

}
