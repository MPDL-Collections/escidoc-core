package de.escidoc.core.aa.ejb;

import java.util.List;

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

import de.escidoc.core.aa.ejb.interfaces.PolicyDecisionPointLocal;
import de.escidoc.core.aa.ejb.interfaces.PolicyDecisionPointRemote;
import de.escidoc.core.aa.service.interfaces.PolicyDecisionPointInterface;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;

@Stateless(name = "PolicyDecisionPoint")
@Remote(PolicyDecisionPointRemote.class)
@Local(PolicyDecisionPointLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class PolicyDecisionPointBean implements PolicyDecisionPointRemote, PolicyDecisionPointLocal {

    private PolicyDecisionPointInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDecisionPointBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("PolicyDecisionPoint.spring.ejb.context").getFactory();
            this.service = (PolicyDecisionPointInterface) factory.getBean("service.PolicyDecisionPoint");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception PolicyDecisionPointComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String evaluate(final String requestsXml, final SecurityContext securityContext)
        throws ResourceNotFoundException, XmlCorruptedException, XmlSchemaValidationException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluate(requestsXml);
    }

    @PermitAll
    public String evaluate(final String requestsXml, final String authHandle, final Boolean restAccess)
        throws ResourceNotFoundException, XmlCorruptedException, XmlSchemaValidationException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluate(requestsXml);
    }

    @RolesAllowed("Administrator")
    public boolean[] evaluateRequestList(final List requests, final SecurityContext securityContext)
        throws ResourceNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluateRequestList(requests);
    }

    @PermitAll
    public boolean[] evaluateRequestList(final List requests, final String authHandle, final Boolean restAccess)
        throws ResourceNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluateRequestList(requests);
    }

    @RolesAllowed("Administrator")
    public List evaluateRetrieve(final String resourceName, final List ids, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException,
        ResourceNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluateRetrieve(resourceName, ids);
    }

    @PermitAll
    public List evaluateRetrieve(
        final String resourceName, final List ids, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException,
        ResourceNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluateRetrieve(resourceName, ids);
    }

    @RolesAllowed("Administrator")
    public List evaluateMethodForList(
        final String resourceName, final String methodName, final List argumentList,
        final SecurityContext securityContext) throws AuthenticationException, AuthorizationException,
        MissingMethodParameterException, ResourceNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluateMethodForList(resourceName, methodName, argumentList);
    }

    @PermitAll
    public List evaluateMethodForList(
        final String resourceName, final String methodName, final List argumentList, final String authHandle,
        final Boolean restAccess) throws AuthenticationException, AuthorizationException,
        MissingMethodParameterException, ResourceNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.evaluateMethodForList(resourceName, methodName, argumentList);
    }

    @RolesAllowed("Administrator")
    public void touch(final SecurityContext securityContext) throws SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.touch();
    }

    @PermitAll
    public void touch(final String authHandle, final Boolean restAccess) throws SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.touch();
    }
}