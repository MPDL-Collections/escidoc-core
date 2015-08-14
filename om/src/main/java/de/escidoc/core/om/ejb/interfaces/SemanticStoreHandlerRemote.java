package de.escidoc.core.om.ejb.interfaces;

import javax.ejb.CreateException;

import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.exceptions.application.invalid.InvalidTripleStoreOutputFormatException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidTripleStoreQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;

/**
 * Remote interface for SemanticStoreHandler.
 */
public interface SemanticStoreHandlerRemote {

    String spo(String taskParam, SecurityContext securityContext) throws SystemException,
        InvalidTripleStoreQueryException, InvalidTripleStoreOutputFormatException, InvalidXmlException,
        MissingElementValueException, AuthenticationException, AuthorizationException;

    String spo(String taskParam, String authHandle, Boolean restAccess) throws SystemException,
        InvalidTripleStoreQueryException, InvalidTripleStoreOutputFormatException, InvalidXmlException,
        MissingElementValueException, AuthenticationException, AuthorizationException;

    void create() throws CreateException;
}
