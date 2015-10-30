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
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

import de.escidoc.core.aa.ejb.interfaces.UserAccountHandlerLocal;
import de.escidoc.core.aa.ejb.interfaces.UserAccountHandlerRemote;
import de.escidoc.core.aa.service.interfaces.UserAccountHandlerInterface;
import de.escidoc.core.common.exceptions.application.invalid.InvalidScopeException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.GrantNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OrganizationalUnitNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.PreferenceNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.RoleNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.UserAccountNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.UserAttributeNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyActiveException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyDeactiveException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyExistsException;
import de.escidoc.core.common.exceptions.application.violated.AlreadyRevokedException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyElementViolationException;
import de.escidoc.core.common.exceptions.application.violated.UniqueConstraintViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;

@Stateless(name = "UserAccountHandler")
@Remote(UserAccountHandlerRemote.class)
@Local(UserAccountHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class UserAccountHandlerBean implements UserAccountHandlerRemote, UserAccountHandlerLocal {

    private UserAccountHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (UserAccountHandlerInterface) BeanLocator.getBean("UserAccountHandler.spring.ejb.context",
                    "service.UserAccountHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception UserAccountHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String create(final String user, final SecurityContext securityContext)
        throws UniqueConstraintViolationException, XmlCorruptedException, XmlSchemaValidationException,
        OrganizationalUnitNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, InvalidStatusException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(user);
    }

    @PermitAll
    public String create(final String user, final String authHandle, final Boolean restAccess)
        throws UniqueConstraintViolationException, XmlCorruptedException, XmlSchemaValidationException,
        OrganizationalUnitNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, InvalidStatusException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(user);
    }

    @RolesAllowed("Administrator")
    public void delete(final String userId, final SecurityContext securityContext) throws UserAccountNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(userId);
    }

    @PermitAll
    public void delete(final String userId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(userId);
    }

    @RolesAllowed("Administrator")
    public String update(final String userId, final String user, final SecurityContext securityContext)
        throws UserAccountNotFoundException, UniqueConstraintViolationException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, MissingAttributeValueException,
        OptimisticLockingException, AuthenticationException, AuthorizationException,
        OrganizationalUnitNotFoundException, SystemException, InvalidStatusException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(userId, user);
    }

    @PermitAll
    public String update(final String userId, final String user, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, UniqueConstraintViolationException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, MissingAttributeValueException,
        OptimisticLockingException, AuthenticationException, AuthorizationException,
        OrganizationalUnitNotFoundException, SystemException, InvalidStatusException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(userId, user);
    }

    @RolesAllowed("Administrator")
    public void updatePassword(final String userId, final String taskParam, final SecurityContext securityContext)
        throws UserAccountNotFoundException, InvalidStatusException, XmlCorruptedException,
        MissingMethodParameterException, OptimisticLockingException, AuthenticationException, AuthorizationException,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.updatePassword(userId, taskParam);
    }

    @PermitAll
    public void updatePassword(
        final String userId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, InvalidStatusException, XmlCorruptedException,
        MissingMethodParameterException, OptimisticLockingException, AuthenticationException, AuthorizationException,
        SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.updatePassword(userId, taskParam);
    }

    @RolesAllowed("Administrator")
    public String retrieve(final String userId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(userId);
    }

    @PermitAll
    public String retrieve(final String userId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(userId);
    }

    @RolesAllowed("Administrator")
    public String retrieveCurrentUser(final SecurityContext securityContext) throws UserAccountNotFoundException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveCurrentUser();
    }

    @PermitAll
    public String retrieveCurrentUser(final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveCurrentUser();
    }

    @RolesAllowed("Administrator")
    public String retrieveResources(final String userId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResources(userId);
    }

    @PermitAll
    public String retrieveResources(final String userId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResources(userId);
    }

    @RolesAllowed("Administrator")
    public String retrieveCurrentGrants(final String userId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveCurrentGrants(userId);
    }

    @PermitAll
    public String retrieveCurrentGrants(final String userId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveCurrentGrants(userId);
    }

    @RolesAllowed("Administrator")
    public String retrieveGrant(final String userId, final String grantId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, GrantNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveGrant(userId, grantId);
    }

    @PermitAll
    public String retrieveGrant(
        final String userId, final String grantId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, GrantNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveGrant(userId, grantId);
    }

    @RolesAllowed("Administrator")
    public String retrieveGrants(final Map filter, final SecurityContext securityContext)
        throws MissingMethodParameterException, InvalidSearchQueryException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveGrants(filter);
    }

    @PermitAll
    public String retrieveGrants(final Map filter, final String authHandle, final Boolean restAccess)
        throws MissingMethodParameterException, InvalidSearchQueryException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveGrants(filter);
    }

    @RolesAllowed("Administrator")
    public void activate(final String userId, final String taskParam, final SecurityContext securityContext)
        throws AlreadyActiveException, UserAccountNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.activate(userId, taskParam);
    }

    @PermitAll
    public void activate(final String userId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws AlreadyActiveException, UserAccountNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.activate(userId, taskParam);
    }

    @RolesAllowed("Administrator")
    public void deactivate(final String userId, final String taskParam, final SecurityContext securityContext)
        throws AlreadyDeactiveException, UserAccountNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deactivate(userId, taskParam);
    }

    @PermitAll
    public void deactivate(
        final String userId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws AlreadyDeactiveException, UserAccountNotFoundException, XmlCorruptedException,
        MissingMethodParameterException, MissingAttributeValueException, OptimisticLockingException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deactivate(userId, taskParam);
    }

    @RolesAllowed("Administrator")
    public String createGrant(final String userId, final String grantXML, final SecurityContext securityContext)
        throws AlreadyExistsException, UserAccountNotFoundException, InvalidScopeException, RoleNotFoundException,
        XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createGrant(userId, grantXML);
    }

    @PermitAll
    public String createGrant(
        final String userId, final String grantXML, final String authHandle, final Boolean restAccess)
        throws AlreadyExistsException, UserAccountNotFoundException, InvalidScopeException, RoleNotFoundException,
        XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createGrant(userId, grantXML);
    }

    @RolesAllowed("Administrator")
    public void revokeGrant(
        final String userId, final String grantId, final String taskParam, final SecurityContext securityContext)
        throws UserAccountNotFoundException, GrantNotFoundException, AlreadyRevokedException, XmlCorruptedException,
        MissingAttributeValueException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrant(userId, grantId, taskParam);
    }

    @PermitAll
    public void revokeGrant(
        final String userId, final String grantId, final String taskParam, final String authHandle,
        final Boolean restAccess) throws UserAccountNotFoundException, GrantNotFoundException, AlreadyRevokedException,
        XmlCorruptedException, MissingAttributeValueException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrant(userId, grantId, taskParam);
    }

    @RolesAllowed("Administrator")
    public void revokeGrants(final String userId, final String taskParam, final SecurityContext securityContext)
        throws UserAccountNotFoundException, GrantNotFoundException, AlreadyRevokedException, XmlCorruptedException,
        MissingAttributeValueException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrants(userId, taskParam);
    }

    @PermitAll
    public void revokeGrants(
        final String userId, final String taskParam, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, GrantNotFoundException, AlreadyRevokedException, XmlCorruptedException,
        MissingAttributeValueException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.revokeGrants(userId, taskParam);
    }

    @RolesAllowed("Administrator")
    public String retrieveUserAccounts(final Map filter, final SecurityContext securityContext)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException,
        InvalidSearchQueryException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUserAccounts(filter);
    }

    @PermitAll
    public String retrieveUserAccounts(final Map filter, final String authHandle, final Boolean restAccess)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException,
        InvalidSearchQueryException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUserAccounts(filter);
    }

    @RolesAllowed("Administrator")
    public UserDetails retrieveUserDetails(final String handle, final SecurityContext securityContext)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException,
        UserAccountNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUserDetails(handle);
    }

    @PermitAll
    public UserDetails retrieveUserDetails(final String handle, final String authHandle, final Boolean restAccess)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException,
        UserAccountNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveUserDetails(handle);
    }

    @RolesAllowed("Administrator")
    public String retrievePreferences(final String userId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrievePreferences(userId);
    }

    @PermitAll
    public String retrievePreferences(final String userId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrievePreferences(userId);
    }

    @RolesAllowed("Administrator")
    public String createPreference(
        final String userId, final String preferenceXML, final SecurityContext securityContext)
        throws AlreadyExistsException, UserAccountNotFoundException, PreferenceNotFoundException,
        XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createPreference(userId, preferenceXML);
    }

    @PermitAll
    public String createPreference(
        final String userId, final String preferenceXML, final String authHandle, final Boolean restAccess)
        throws AlreadyExistsException, UserAccountNotFoundException, PreferenceNotFoundException,
        XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createPreference(userId, preferenceXML);
    }

    @RolesAllowed("Administrator")
    public String updatePreferences(
        final String userId, final String preferencesXML, final SecurityContext securityContext)
        throws UserAccountNotFoundException, XmlCorruptedException, XmlSchemaValidationException,
        OptimisticLockingException, MissingMethodParameterException, AuthenticationException, AuthorizationException,
        SystemException, MissingAttributeValueException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.updatePreferences(userId, preferencesXML);
    }

    @PermitAll
    public String updatePreferences(
        final String userId, final String preferencesXML, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, XmlCorruptedException, XmlSchemaValidationException,
        OptimisticLockingException, MissingMethodParameterException, AuthenticationException, AuthorizationException,
        SystemException, MissingAttributeValueException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.updatePreferences(userId, preferencesXML);
    }

    @RolesAllowed("Administrator")
    public String updatePreference(
        final String userId, final String preferenceName, final String preferenceXML,
        final SecurityContext securityContext) throws AlreadyExistsException, UserAccountNotFoundException,
        XmlCorruptedException, XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, PreferenceNotFoundException, OptimisticLockingException,
        MissingAttributeValueException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.updatePreference(userId, preferenceName, preferenceXML);
    }

    @PermitAll
    public String updatePreference(
        final String userId, final String preferenceName, final String preferenceXML, final String authHandle,
        final Boolean restAccess) throws AlreadyExistsException, UserAccountNotFoundException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException, AuthorizationException,
        SystemException, PreferenceNotFoundException, OptimisticLockingException, MissingAttributeValueException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.updatePreference(userId, preferenceName, preferenceXML);
    }

    @RolesAllowed("Administrator")
    public String retrievePreference(
        final String userId, final String preferenceName, final SecurityContext securityContext)
        throws UserAccountNotFoundException, PreferenceNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrievePreference(userId, preferenceName);
    }

    @PermitAll
    public String retrievePreference(
        final String userId, final String preferenceName, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, PreferenceNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrievePreference(userId, preferenceName);
    }

    @RolesAllowed("Administrator")
    public void deletePreference(final String userId, final String preferenceName, final SecurityContext securityContext)
        throws UserAccountNotFoundException, PreferenceNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deletePreference(userId, preferenceName);
    }

    @PermitAll
    public void deletePreference(
        final String userId, final String preferenceName, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, PreferenceNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deletePreference(userId, preferenceName);
    }

    @RolesAllowed("Administrator")
    public String createAttribute(final String userId, final String attributeXml, final SecurityContext securityContext)
        throws AlreadyExistsException, UserAccountNotFoundException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException, AuthorizationException,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createAttribute(userId, attributeXml);
    }

    @PermitAll
    public String createAttribute(
        final String userId, final String attributeXml, final String authHandle, final Boolean restAccess)
        throws AlreadyExistsException, UserAccountNotFoundException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException, AuthorizationException,
        SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.createAttribute(userId, attributeXml);
    }

    @RolesAllowed("Administrator")
    public String retrieveAttributes(final String userId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAttributes(userId);
    }

    @PermitAll
    public String retrieveAttributes(final String userId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAttributes(userId);
    }

    @RolesAllowed("Administrator")
    public String retrieveNamedAttributes(final String userId, final String name, final SecurityContext securityContext)
        throws UserAccountNotFoundException, UserAttributeNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveNamedAttributes(userId, name);
    }

    @PermitAll
    public String retrieveNamedAttributes(
        final String userId, final String name, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, UserAttributeNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveNamedAttributes(userId, name);
    }

    @RolesAllowed("Administrator")
    public String retrieveAttribute(final String userId, final String attributeId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, UserAttributeNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAttribute(userId, attributeId);
    }

    @PermitAll
    public String retrieveAttribute(
        final String userId, final String attributeId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, UserAttributeNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAttribute(userId, attributeId);
    }

    @RolesAllowed("Administrator")
    public String updateAttribute(
        final String userId, final String attributeId, final String attributeXml, final SecurityContext securityContext)
        throws UserAccountNotFoundException, OptimisticLockingException, UserAttributeNotFoundException,
        ReadonlyElementViolationException, XmlCorruptedException, XmlSchemaValidationException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.updateAttribute(userId, attributeId, attributeXml);
    }

    @PermitAll
    public String updateAttribute(
        final String userId, final String attributeId, final String attributeXml, final String authHandle,
        final Boolean restAccess) throws UserAccountNotFoundException, OptimisticLockingException,
        UserAttributeNotFoundException, ReadonlyElementViolationException, XmlCorruptedException,
        XmlSchemaValidationException, MissingMethodParameterException, AuthenticationException, AuthorizationException,
        SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.updateAttribute(userId, attributeId, attributeXml);
    }

    @RolesAllowed("Administrator")
    public void deleteAttribute(final String userId, final String attributeId, final SecurityContext securityContext)
        throws UserAccountNotFoundException, UserAttributeNotFoundException, ReadonlyElementViolationException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deleteAttribute(userId, attributeId);
    }

    @PermitAll
    public void deleteAttribute(
        final String userId, final String attributeId, final String authHandle, final Boolean restAccess)
        throws UserAccountNotFoundException, UserAttributeNotFoundException, ReadonlyElementViolationException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.deleteAttribute(userId, attributeId);
    }

    @RolesAllowed("Administrator")
    public String retrievePermissionFilterQuery(final Map parameters, final SecurityContext securityContext)
        throws SystemException, InvalidSearchQueryException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrievePermissionFilterQuery(parameters);
    }

    @PermitAll
    public String retrievePermissionFilterQuery(final Map parameters, final String authHandle, final Boolean restAccess)
        throws SystemException, InvalidSearchQueryException, AuthenticationException, AuthorizationException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrievePermissionFilterQuery(parameters);
    }
}