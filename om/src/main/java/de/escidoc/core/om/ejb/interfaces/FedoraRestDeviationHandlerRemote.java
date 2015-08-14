package de.escidoc.core.om.ejb.interfaces;

import java.util.Map;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.business.fedora.EscidocBinaryContent;

/**
 * Remote interface for FedoraRestDeviationHandler.
 */
public interface FedoraRestDeviationHandlerRemote {

    EscidocBinaryContent getDatastreamDissemination(
        String pid, String dsID, Map parameters, SecurityContext securityContext) throws Exception;

    EscidocBinaryContent getDatastreamDissemination(
        String pid, String dsID, Map parameters, String authHandle, Boolean restAccess) throws Exception;

    String export(String pid, Map parameters, SecurityContext securityContext) throws Exception;

    String export(String pid, Map parameters, String authHandle, Boolean restAccess) throws Exception;

    void cache(String pid, String xml, SecurityContext securityContext) throws Exception;

    void cache(String pid, String xml, String authHandle, Boolean restAccess) throws Exception;

    void removeFromCache(String pid, SecurityContext securityContext) throws Exception;

    void removeFromCache(String pid, String authHandle, Boolean restAccess) throws Exception;

    void replaceInCache(String pid, String xml, SecurityContext securityContext) throws Exception;

    void replaceInCache(String pid, String xml, String authHandle, Boolean restAccess) throws Exception;

    void create() throws CreateException;
}
