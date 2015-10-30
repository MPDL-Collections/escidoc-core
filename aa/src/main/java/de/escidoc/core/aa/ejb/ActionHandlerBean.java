package de.escidoc.core.aa.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.CreateException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.aa.ejb.interfaces.ActionHandlerLocal;
import de.escidoc.core.aa.ejb.interfaces.ActionHandlerRemote;
import de.escidoc.core.aa.service.interfaces.ActionHandlerInterface;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.notfound.ContextNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;

@Stateless(name = "ActionHandler")
@Remote(ActionHandlerRemote.class)
@Local(ActionHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@RunAs("Administrator")
public class ActionHandlerBean implements ActionHandlerRemote, ActionHandlerLocal {

    private ActionHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (ActionHandlerInterface) BeanLocator.getBean("ActionHandler.spring.ejb.context",
                    "service.ActionHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception ActionHandlerComponent: " + e);
            throw new CreateException(e.getMessage());
        }
    }

    @RolesAllowed("Administrator")
    public String createUnsecuredActions(
        final String contextId, final String actions, final SecurityContext securityContext)
        throws ContextNotFoundException, XmlCorruptedException, XmlSchemaValidationException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createUnsecuredActions(contextId, actions);
    }

    @PermitAll
    public String createUnsecuredActions(
        final String contextId, final String actions, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, XmlCorruptedException, XmlSchemaValidationException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createUnsecuredActions(contextId, actions);
    }

    @RolesAllowed("Administrator")
    public void deleteUnsecuredActions(final String contextId, final SecurityContext securityContext)
        throws ContextNotFoundException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deleteUnsecuredActions(contextId);
    }

    @PermitAll
    public void deleteUnsecuredActions(final String contextId, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deleteUnsecuredActions(contextId);
    }

    @RolesAllowed("Administrator")
    public String retrieveUnsecuredActions(final String contextId, final SecurityContext securityContext)
        throws ContextNotFoundException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUnsecuredActions(contextId);
    }

    @PermitAll
    public String retrieveUnsecuredActions(final String contextId, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUnsecuredActions(contextId);
    }
}