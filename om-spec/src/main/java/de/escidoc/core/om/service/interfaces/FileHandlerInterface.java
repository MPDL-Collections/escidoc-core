package de.escidoc.core.om.service.interfaces;

import de.escidoc.core.common.exceptions.EscidocException;

public interface FileHandlerInterface {
	String getFile(String id) throws EscidocException;
}
