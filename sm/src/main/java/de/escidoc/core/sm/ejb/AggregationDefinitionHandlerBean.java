package de.escidoc.core.sm.ejb;

import java.util.Map;

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

import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.AggregationDefinitionNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ScopeNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import de.escidoc.core.sm.ejb.interfaces.AggregationDefinitionHandlerLocal;
import de.escidoc.core.sm.ejb.interfaces.AggregationDefinitionHandlerRemote;
import de.escidoc.core.sm.service.interfaces.AggregationDefinitionHandlerInterface;

@Stateless(name = "AggregationDefinitionHandler")
@Remote(AggregationDefinitionHandlerRemote.class)
@Local(AggregationDefinitionHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class AggregationDefinitionHandlerBean
    implements AggregationDefinitionHandlerRemote, AggregationDefinitionHandlerLocal {

    private AggregationDefinitionHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationDefinitionHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (AggregationDefinitionHandlerInterface) BeanLocator.getBean(
                    "AggregationDefinitionHandler.spring.ejb.context", "service.AggregationDefinitionHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception AggregationDefinitionHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String create(final String xmlData, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, XmlSchemaValidationException, XmlCorruptedException, MissingMethodParameterException,
        ScopeNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(xmlData);
    }

    @PermitAll
    public String create(final String xmlData, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, XmlSchemaValidationException, XmlCorruptedException,
        MissingMethodParameterException, ScopeNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(xmlData);
    }

    @RolesAllowed("Administrator")
    public void delete(final String id, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, AggregationDefinitionNotFoundException, MissingMethodParameterException,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(id);
    }

    @PermitAll
    public void delete(final String id, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, AggregationDefinitionNotFoundException,
        MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(id);
    }

    @RolesAllowed("Administrator")
    public String retrieve(final String id, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, AggregationDefinitionNotFoundException, MissingMethodParameterException,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(id);
    }

    @PermitAll
    public String retrieve(final String id, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, AggregationDefinitionNotFoundException,
        MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(id);
    }

    @RolesAllowed("Administrator")
    public String retrieveAggregationDefinitions(final Map parameters, final SecurityContext securityContext)
        throws InvalidSearchQueryException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAggregationDefinitions(parameters);
    }

    @PermitAll
    public String retrieveAggregationDefinitions(final Map parameters, final String authHandle, final Boolean restAccess)
        throws InvalidSearchQueryException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAggregationDefinitions(parameters);
    }
}