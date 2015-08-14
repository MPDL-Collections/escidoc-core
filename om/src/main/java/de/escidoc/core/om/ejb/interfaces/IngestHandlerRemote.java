package de.escidoc.core.om.ejb.interfaces;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.EscidocException;

/**
 * Remote interface for IngestHandler.
 */
public interface IngestHandlerRemote {

    String ingest(String xmlData, SecurityContext securityContext) throws EscidocException;

    String ingest(String xmlData, String authHandle, Boolean restAccess) throws EscidocException;

    void create() throws CreateException;
}
