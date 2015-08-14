package de.escidoc.core.om.ejb.interfaces;

import java.util.Map;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

/**
 * Remote interface for FedoraDescribeDeviationHandler.
 */
public interface FedoraDescribeDeviationHandlerRemote {

    String getFedoraDescription(Map parameters, SecurityContext securityContext) throws Exception;

    String getFedoraDescription(Map parameters, String authHandle, Boolean restAccess) throws Exception;

    void create() throws CreateException;
}
