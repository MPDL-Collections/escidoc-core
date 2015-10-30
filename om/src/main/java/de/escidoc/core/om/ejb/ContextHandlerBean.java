package de.escidoc.core.om.ejb;

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

import de.escidoc.core.common.business.fedora.EscidocBinaryContent;
import de.escidoc.core.common.exceptions.application.invalid.ContextNotEmptyException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.AdminDescriptorNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentModelNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContextNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OperationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.OrganizationalUnitNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.StreamNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.ContextNameNotUniqueException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyAttributeViolationException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyElementViolationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.ejb.interfaces.ContextHandlerLocal;
import de.escidoc.core.om.ejb.interfaces.ContextHandlerRemote;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import de.escidoc.core.om.service.interfaces.ContextHandlerInterface;

@Stateless(name = "ContextHandler")
@Remote(ContextHandlerRemote.class)
@Local(ContextHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@RunAs("Administrator")
public class ContextHandlerBean implements ContextHandlerRemote, ContextHandlerLocal {

    private ContextHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (ContextHandlerInterface) BeanLocator.getBean("ContextHandler.spring.ejb.context",
                    "service.ContextHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception ContextHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String create(final String xmlData, final SecurityContext securityContext)
        throws MissingMethodParameterException, ContextNameNotUniqueException, AuthenticationException,
        AuthorizationException, SystemException, ContentModelNotFoundException, ReadonlyElementViolationException,
        MissingAttributeValueException, MissingElementValueException, ReadonlyAttributeViolationException,
        InvalidContentException, OrganizationalUnitNotFoundException, InvalidStatusException, XmlCorruptedException,
        XmlSchemaValidationException {
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
        throws MissingMethodParameterException, ContextNameNotUniqueException, AuthenticationException,
        AuthorizationException, SystemException, ContentModelNotFoundException, ReadonlyElementViolationException,
        MissingAttributeValueException, MissingElementValueException, ReadonlyAttributeViolationException,
        InvalidContentException, OrganizationalUnitNotFoundException, InvalidStatusException, XmlCorruptedException,
        XmlSchemaValidationException {
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
    public void delete(final String id, final SecurityContext securityContext) throws ContextNotFoundException,
        ContextNotEmptyException, MissingMethodParameterException, InvalidStatusException, AuthenticationException,
        AuthorizationException, SystemException {
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
        throws ContextNotFoundException, ContextNotEmptyException, MissingMethodParameterException,
        InvalidStatusException, AuthenticationException, AuthorizationException, SystemException {
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
    public String retrieve(final String id, final SecurityContext securityContext) throws ContextNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
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
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
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
    public String retrieveProperties(final String id, final SecurityContext securityContext)
        throws ContextNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveProperties(id);
    }

    @PermitAll
    public String retrieveProperties(final String id, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveProperties(id);
    }

    @RolesAllowed("Administrator")
    public String update(final String id, final String xmlData, final SecurityContext securityContext)
        throws ContextNotFoundException, MissingMethodParameterException, InvalidContentException,
        InvalidStatusException, AuthenticationException, AuthorizationException, ReadonlyElementViolationException,
        ReadonlyAttributeViolationException, OptimisticLockingException, ContextNameNotUniqueException,
        InvalidXmlException, MissingElementValueException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(id, xmlData);
    }

    @PermitAll
    public String update(final String id, final String xmlData, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, MissingMethodParameterException, InvalidContentException,
        InvalidStatusException, AuthenticationException, AuthorizationException, ReadonlyElementViolationException,
        ReadonlyAttributeViolationException, OptimisticLockingException, ContextNameNotUniqueException,
        InvalidXmlException, MissingElementValueException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.update(id, xmlData);
    }

    @RolesAllowed("Administrator")
    public EscidocBinaryContent retrieveResource(
        final String id, final String resourceName, final Map parameters, final SecurityContext securityContext)
        throws OperationNotFoundException, ContextNotFoundException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResource(id, resourceName, parameters);
    }

    @PermitAll
    public EscidocBinaryContent retrieveResource(
        final String id, final String resourceName, final Map parameters, final String authHandle,
        final Boolean restAccess) throws OperationNotFoundException, ContextNotFoundException,
        MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResource(id, resourceName, parameters);
    }

    @RolesAllowed("Administrator")
    public String retrieveResources(final String id, final SecurityContext securityContext)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResources(id);
    }

    @PermitAll
    public String retrieveResources(final String id, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResources(id);
    }

    @RolesAllowed("Administrator")
    public String open(final String id, final String taskParam, final SecurityContext securityContext)
        throws ContextNotFoundException, MissingMethodParameterException, InvalidStatusException,
        AuthenticationException, AuthorizationException, OptimisticLockingException, InvalidXmlException,
        SystemException, LockingException, StreamNotFoundException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.open(id, taskParam);
    }

    @PermitAll
    public String open(final String id, final String taskParam, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, MissingMethodParameterException, InvalidStatusException,
        AuthenticationException, AuthorizationException, OptimisticLockingException, InvalidXmlException,
        SystemException, LockingException, StreamNotFoundException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.open(id, taskParam);
    }

    @RolesAllowed("Administrator")
    public String close(final String id, final String taskParam, final SecurityContext securityContext)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException, InvalidXmlException,
        InvalidStatusException, LockingException, StreamNotFoundException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.close(id, taskParam);
    }

    @PermitAll
    public String close(final String id, final String taskParam, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, OptimisticLockingException, InvalidXmlException,
        InvalidStatusException, LockingException, StreamNotFoundException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.close(id, taskParam);
    }

    @RolesAllowed("Administrator")
    public String retrieveContexts(final Map filter, final SecurityContext securityContext)
        throws MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContexts(filter);
    }

    @PermitAll
    public String retrieveContexts(final Map filter, final String authHandle, final Boolean restAccess)
        throws MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContexts(filter);
    }

    @RolesAllowed("Administrator")
    public String retrieveMembers(final String id, final Map filter, final SecurityContext securityContext)
        throws ContextNotFoundException, MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMembers(id, filter);
    }

    @PermitAll
    public String retrieveMembers(final String id, final Map filter, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMembers(id, filter);
    }

    @RolesAllowed("Administrator")
    public String retrieveAdminDescriptor(final String id, final String name, final SecurityContext securityContext)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, AdminDescriptorNotFoundException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAdminDescriptor(id, name);
    }

    @PermitAll
    public String retrieveAdminDescriptor(
        final String id, final String name, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException, AdminDescriptorNotFoundException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAdminDescriptor(id, name);
    }

    @RolesAllowed("Administrator")
    public String retrieveAdminDescriptors(final String id, final SecurityContext securityContext)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAdminDescriptors(id);
    }

    @PermitAll
    public String retrieveAdminDescriptors(final String id, final String authHandle, final Boolean restAccess)
        throws ContextNotFoundException, MissingMethodParameterException, AuthenticationException,
        AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveAdminDescriptors(id);
    }
}