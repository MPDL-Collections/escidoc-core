package de.escidoc.core.adm.ejb;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.adm.ejb.interfaces.AdminHandlerLocal;
import de.escidoc.core.adm.ejb.interfaces.AdminHandlerLocalHome;
import de.escidoc.core.adm.ejb.interfaces.AdminHandlerRemote;
import de.escidoc.core.adm.ejb.interfaces.AdminHandlerRemoteHome;
import de.escidoc.core.adm.service.interfaces.AdminHandlerInterface;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;

@Stateless(name = "AdminHandler")
@RemoteHome(AdminHandlerRemoteHome.class)
@LocalHome(AdminHandlerLocalHome.class)
@Remote(AdminHandlerRemote.class)
@Local(AdminHandlerLocal.class)
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RunAs("Administrator")
public class AdminHandlerBean implements AdminHandlerRemote, AdminHandlerLocal {

    private AdminHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminHandlerBean.class);

    @PostConstruct
    @PermitAll
    public void ejbCreate() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("AdminHandler.spring.ejb.context").getFactory();
            this.service = (AdminHandlerInterface) factory.getBean("service.AdminHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception AdminHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String deleteObjects(final String taskParam, final SecurityContext securityContext)
        throws InvalidXmlException, SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.deleteObjects(taskParam);
    }

    @PermitAll
    public String deleteObjects(final String taskParam, final String authHandle, final Boolean restAccess)
        throws InvalidXmlException, SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.deleteObjects(taskParam);
    }

    @RolesAllowed("Administrator")
    public String getPurgeStatus(final SecurityContext securityContext) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getPurgeStatus();
    }

    @PermitAll
    public String getPurgeStatus(final String authHandle, final Boolean restAccess) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getPurgeStatus();
    }

    @RolesAllowed("Administrator")
    public String getReindexStatus(final SecurityContext securityContext) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getReindexStatus();
    }

    @PermitAll
    public String getReindexStatus(final String authHandle, final Boolean restAccess) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getReindexStatus();
    }

    @RolesAllowed("Administrator")
    public void decreaseReindexStatus(final String objectTypeXml, final SecurityContext securityContext)
        throws InvalidXmlException, SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.decreaseReindexStatus(objectTypeXml);
    }

    @PermitAll
    public void decreaseReindexStatus(final String objectTypeXml, final String authHandle, final Boolean restAccess)
        throws InvalidXmlException, SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.decreaseReindexStatus(objectTypeXml);
    }

    @RolesAllowed("Administrator")
    public String reindex(final String clearIndex, final String indexNamePrefix, final SecurityContext securityContext)
        throws SystemException, InvalidSearchQueryException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.reindex(clearIndex, indexNamePrefix);
    }

    @PermitAll
    public String reindex(
        final String clearIndex, final String indexNamePrefix, final String authHandle, final Boolean restAccess)
        throws SystemException, InvalidSearchQueryException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.reindex(clearIndex, indexNamePrefix);
    }

    @RolesAllowed("Administrator")
    public String getIndexConfiguration(final SecurityContext securityContext) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getIndexConfiguration();
    }

    @PermitAll
    public String getIndexConfiguration(final String authHandle, final Boolean restAccess) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getIndexConfiguration();
    }

    @RolesAllowed("Administrator")
    public String getRepositoryInfo(final SecurityContext securityContext) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getRepositoryInfo();
    }

    @PermitAll
    public String getRepositoryInfo(final String authHandle, final Boolean restAccess) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getRepositoryInfo();
    }

    @RolesAllowed("Administrator")
    public String getRepositoryInfo(final String key, final SecurityContext securityContext) throws SystemException,
        AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getRepositoryInfo(key);
    }

    @PermitAll
    public String getRepositoryInfo(final String key, final String authHandle, final Boolean restAccess)
        throws SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getRepositoryInfo(key);
    }

    @RolesAllowed("Administrator")
    public String loadExamples(final String type, final SecurityContext securityContext)
        throws InvalidSearchQueryException, SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.loadExamples(type);
    }

    @PermitAll
    public String loadExamples(final String type, final String authHandle, final Boolean restAccess)
        throws InvalidSearchQueryException, SystemException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.loadExamples(type);
    }
}