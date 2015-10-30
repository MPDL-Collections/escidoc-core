package de.escidoc.core.oai.ejb;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.CreateException;
import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
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

import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.UniqueConstraintViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.oai.ejb.interfaces.SetDefinitionHandlerLocal;
import de.escidoc.core.oai.ejb.interfaces.SetDefinitionHandlerLocalHome;
import de.escidoc.core.oai.ejb.interfaces.SetDefinitionHandlerRemote;
import de.escidoc.core.oai.ejb.interfaces.SetDefinitionHandlerRemoteHome;
import de.escidoc.core.oai.service.interfaces.SetDefinitionHandlerInterface;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;

@Stateless(name = "SetDefinitionHandler")
@RemoteHome(SetDefinitionHandlerRemoteHome.class)
@LocalHome(SetDefinitionHandlerLocalHome.class)
@Remote(SetDefinitionHandlerRemote.class)
@Local(SetDefinitionHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@RunAs("Administrator")
public class SetDefinitionHandlerBean implements SetDefinitionHandlerRemote, SetDefinitionHandlerLocal {

    private SetDefinitionHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(SetDefinitionHandlerBean.class);

    @PostConstruct
    @PermitAll
    public void ejbCreate() throws CreateException {
        try {

            this.service =
                (SetDefinitionHandlerInterface) BeanLocator.getBean("SetDefinitionHandler.spring.ejb.context",
                    "service.SetDefinitionHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception SetDefinitionHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String create(final String setDefinition, final SecurityContext securityContext)
        throws UniqueConstraintViolationException, InvalidXmlException, MissingMethodParameterException,
        SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(setDefinition);
    }

    @PermitAll
    public String create(final String setDefinition, final String authHandle, final Boolean restAccess)
        throws UniqueConstraintViolationException, InvalidXmlException, MissingMethodParameterException,
        SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(setDefinition);
    }

    @RolesAllowed("Administrator")
    public String retrieve(final String setDefinitionId, final SecurityContext securityContext)
        throws ResourceNotFoundException, MissingMethodParameterException, SystemException, AuthenticationException,
        AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(setDefinitionId);
    }

    @PermitAll
    public String retrieve(final String setDefinitionId, final String authHandle, final Boolean restAccess)
        throws ResourceNotFoundException, MissingMethodParameterException, SystemException, AuthenticationException,
        AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(setDefinitionId);
    }

    @RolesAllowed("Administrator")
    public String update(final String setDefinitionId, final String xmlData, final SecurityContext securityContext)
        throws ResourceNotFoundException, OptimisticLockingException, MissingMethodParameterException, SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(setDefinitionId, xmlData);
    }

    @PermitAll
    public String update(
        final String setDefinitionId, final String xmlData, final String authHandle, final Boolean restAccess)
        throws ResourceNotFoundException, OptimisticLockingException, MissingMethodParameterException, SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(setDefinitionId, xmlData);
    }

    @RolesAllowed("Administrator")
    public void delete(final String setDefinitionId, final SecurityContext securityContext)
        throws ResourceNotFoundException, MissingMethodParameterException, SystemException, AuthenticationException,
        AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(setDefinitionId);
    }

    @PermitAll
    public void delete(final String setDefinitionId, final String authHandle, final Boolean restAccess)
        throws ResourceNotFoundException, MissingMethodParameterException, SystemException, AuthenticationException,
        AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(setDefinitionId);
    }

    @RolesAllowed("Administrator")
    public String retrieveSetDefinitions(final Map filter, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException,
        InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveSetDefinitions(filter);
    }

    @PermitAll
    public String retrieveSetDefinitions(final Map filter, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException,
        InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveSetDefinitions(filter);
    }
}