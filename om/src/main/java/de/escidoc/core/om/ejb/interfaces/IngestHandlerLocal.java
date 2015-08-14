package de.escidoc.core.om.ejb.interfaces;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.EscidocException;

/**
 * Local interface for IngestHandler.
 */
public interface IngestHandlerLocal {

    String ingest(String xmlData, SecurityContext securityContext) throws EscidocException;

    String ingest(String xmlData, String authHandle, Boolean restAccess) throws EscidocException;

    void create() throws CreateException;
}
