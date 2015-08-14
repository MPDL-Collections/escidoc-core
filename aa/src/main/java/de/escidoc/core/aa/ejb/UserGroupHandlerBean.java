package de.escidoc.core.aa.ejb;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.aa.ejb.interfaces.UserGroupHandlerLocal;
import de.escidoc.core.aa.ejb.interfaces.UserGroupHandlerRemote;
import de.escidoc.core.aa.service.interfaces.UserGroupHandlerInterface;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidScopeException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.GrantNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OrganizationalUnitNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.RoleNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.UserAccountNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.UserGroupNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyActiveException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyDeactiveException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyExistsException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyRevokedException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.UniqueConstraintViolationException;
import de.escidoc.core.common.exceptions.application.violated.UserGroupHierarchyViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;

@Stateless(name = "UserGroupHandler")
@Remote(UserGroupHandlerRemote.class)
@Local(UserGroupHandlerLocal.class)
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RunAs("Administrator")
public class UserGroupHandlerBean implements UserGroupHandlerRemote, UserGroupHandlerLocal {

    private UserGroupHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("UserGroupHandler.spring.ejb.context").getFactory();
            this.service = (UserGroupHandlerInterface) factory.getBean("service.UserGroupHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception UserGroupHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String create(final String xmlData, final SecurityContext securityContext)
        throws UniqueConstraintViolationException, XmlCorruptedException, XmlSchemaValidationException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
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
        throws UniqueConstraintViolationException, XmlCorruptedException, XmlSchemaValidationException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
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
    public void delete(final String groupId, final SecurityContext securityContext) throws UserGroupNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(groupId);
    }

    @PermitAll
    public void delete(final String groupId, final String authHandle, final Boolean restAccess)
        throws UserGroupNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(groupId);
    }

    @RolesAllowed("Administrator")
    public String retrieve(final String groupId, final SecurityContext securityContext)
        throws UserGroupNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(groupId);
    }

    @PermitAll
    public String retrieve(final String groupId, final String authHandle, final Boolean restAccess)
        throws UserGroupNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(groupId);
    }

    @RolesAllowed("Administrator")
    public String update(final String groupId, final String xmlData, final SecurityContext securityContext)
        throws UserGroupNotFoundException, UniqueConstraintViolationException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, MissingAttributeValueException,
        OptimisticLockingException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(groupId, xmlData);
    }

    @PermitAll
    public String update(final String groupId, final String xmlData, final String authHandle, final Boolean restAccess)
        throws UserGroupNotFoundException, UniqueConstraintViolationException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, MissingAttributeValueException,
        OptimisticLockingException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(groupId, xmlData);
    }

    @RolesAllowed("Administrator")
    public void activate(final String groupId, final String taskParam, final SecurityContext securityContext)
        throws AlreadyActiveException, UserGroupNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.activate(groupId, taskParam);
    }

    @PermitAll
    public void activate(final String groupId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws AlreadyActiveException, UserGroupNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.activate(groupId, taskParam);
    }

    @RolesAllowed("Administrator")
    public void deactivate(final String groupId, final String taskParam, final SecurityContext securityContext)
        throws AlreadyDeactiveException, UserGroupNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deactivate(groupId, taskParam);
    }

    @PermitAll
    public void deactivate(
        final String groupId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws AlreadyDeactiveException, UserGroupNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deactivate(groupId, taskParam);
    }

    @RolesAllowed("Administrator")
    public String retrieveCurrentGrants(final String userGroupId, final SecurityContext securityContext)
        throws UserGroupNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveCurrentGrants(userGroupId);
    }

    @PermitAll
    public String retrieveCurrentGrants(final String userGroupId, final String authHandle, final Boolean restAccess)
        throws UserGroupNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveCurrentGrants(userGroupId);
    }

    @RolesAllowed("Administrator")
    public String createGrant(final String groupId, final String grantXML, final SecurityContext securityContext)
        throws AlreadyExistsException, UserGroupNotFoundException, InvalidScopeException, RoleNotFoundException,
        XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createGrant(groupId, grantXML);
    }

    @PermitAll
    public String createGrant(
        final String groupId, final String grantXML, final String authHandle, final Boolean restAccess)
        throws AlreadyExistsException, UserGroupNotFoundException, InvalidScopeException, RoleNotFoundException,
        XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createGrant(groupId, grantXML);
    }

    @RolesAllowed("Administrator")
    public void revokeGrant(
        final String groupId, final String grantId, final String taskParam, final SecurityContext securityContext)
        throws UserGroupNotFoundException, GrantNotFoundException, AlreadyRevokedException, XmlCorruptedException,
        MissingAttributeValueException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrant(groupId, grantId, taskParam);
    }

    @PermitAll
    public void revokeGrant(
        final String groupId, final String grantId, final String taskParam, final String authHandle,
        final Boolean restAccess) throws UserGroupNotFoundException, GrantNotFoundException, AlreadyRevokedException,
        XmlCorruptedException, MissingAttributeValueException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrant(groupId, grantId, taskParam);
    }

    @RolesAllowed("Administrator")
    public String retrieveGrant(final String groupId, final String grantId, final SecurityContext securityContext)
        throws UserGroupNotFoundException, GrantNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveGrant(groupId, grantId);
    }

    @PermitAll
    public String retrieveGrant(
        final String groupId, final String grantId, final String authHandle, final Boolean restAccess)
        throws UserGroupNotFoundException, GrantNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveGrant(groupId, grantId);
    }

    @RolesAllowed("Administrator")
    public void revokeGrants(final String groupId, final String taskParam, final SecurityContext securityContext)
        throws UserGroupNotFoundException, GrantNotFoundException, AlreadyRevokedException, XmlCorruptedException,
        MissingAttributeValueException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrants(groupId, taskParam);
    }

    @PermitAll
    public void revokeGrants(
        final String groupId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws UserGroupNotFoundException, GrantNotFoundException, AlreadyRevokedException, XmlCorruptedException,
        MissingAttributeValueException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrants(groupId, taskParam);
    }

    @RolesAllowed("Administrator")
    public String retrieveResources(final String groupId, final SecurityContext securityContext)
        throws UserGroupNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResources(groupId);
    }

    @PermitAll
    public String retrieveResources(final String groupId, final String authHandle, final Boolean restAccess)
        throws UserGroupNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResources(groupId);
    }

    @RolesAllowed("Administrator")
    public String retrieveUserGroups(final Map filter, final SecurityContext securityContext)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException,
        InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUserGroups(filter);
    }

    @PermitAll
    public String retrieveUserGroups(final Map filter, final String authHandle, final Boolean restAccess)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException,
        InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUserGroups(filter);
    }

    @RolesAllowed("Administrator")
    public String addSelectors(final String groupId, final String taskParam, final SecurityContext securityContext)
        throws OrganizationalUnitNotFoundException, UserAccountNotFoundException, UserGroupNotFoundException,
        InvalidContentException, MissingMethodParameterException, SystemException, AuthenticationException,
        AuthorizationException, OptimisticLockingException, XmlCorruptedException, XmlSchemaValidationException,
        UserGroupHierarchyViolationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.addSelectors(groupId, taskParam);
    }

    @PermitAll
    public String addSelectors(
        final String groupId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws OrganizationalUnitNotFoundException, UserAccountNotFoundException, UserGroupNotFoundException,
        InvalidContentException, MissingMethodParameterException, SystemException, AuthenticationException,
        AuthorizationException, OptimisticLockingException, XmlCorruptedException, XmlSchemaValidationException,
        UserGroupHierarchyViolationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.addSelectors(groupId, taskParam);
    }

    @RolesAllowed("Administrator")
    public String removeSelectors(final String groupId, final String taskParam, final SecurityContext securityContext)
        throws XmlCorruptedException, XmlSchemaValidationException, AuthenticationException, AuthorizationException,
        SystemException, UserGroupNotFoundException, OptimisticLockingException, MissingMethodParameterException,
        UserAccountNotFoundException, OrganizationalUnitNotFoundException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.removeSelectors(groupId, taskParam);
    }

    @PermitAll
    public String removeSelectors(
        final String groupId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws XmlCorruptedException, XmlSchemaValidationException, AuthenticationException, AuthorizationException,
        SystemException, UserGroupNotFoundException, OptimisticLockingException, MissingMethodParameterException,
        UserAccountNotFoundException, OrganizationalUnitNotFoundException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.removeSelectors(groupId, taskParam);
    }
}