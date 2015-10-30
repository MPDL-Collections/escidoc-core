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

import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ContentRelationNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.MdRecordNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ReferencedResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.RelationPredicateNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.PidAlreadyAssignedException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.ejb.interfaces.ContentRelationHandlerLocal;
import de.escidoc.core.om.ejb.interfaces.ContentRelationHandlerRemote;
import de.escidoc.core.om.service.interfaces.ContainerHandlerInterface;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;

@Stateless(name = "ContentRelationHandler")
@Remote(ContentRelationHandlerRemote.class)
@Local(ContentRelationHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@RunAs("Administrator")
public class ContentRelationHandlerBean implements ContentRelationHandlerRemote, ContentRelationHandlerLocal {

    private ContentRelationHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentRelationHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (ContentRelationHandlerInterface) BeanLocator.getBean("ContentRelationHandler.spring.ejb.context",
                    "service.ContentRelationHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception ContentRelationHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String create(final String xmlData, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, MissingAttributeValueException, MissingMethodParameterException, InvalidXmlException,
        InvalidContentException, ReferencedResourceNotFoundException, RelationPredicateNotFoundException,
        SystemException {
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
        throws AuthenticationException, AuthorizationException, MissingAttributeValueException,
        MissingMethodParameterException, InvalidXmlException, InvalidContentException,
        ReferencedResourceNotFoundException, RelationPredicateNotFoundException, SystemException {
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
        AuthorizationException, ContentRelationNotFoundException, SystemException, LockingException {
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
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, SystemException,
        LockingException {
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
    public String lock(final String id, final String param, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidContentException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        InvalidXmlException, InvalidStatusException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.lock(id, param);
    }

    @PermitAll
    public String lock(final String id, final String param, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidContentException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        InvalidXmlException, InvalidStatusException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.lock(id, param);
    }

    @RolesAllowed("Administrator")
    public String unlock(final String id, final String param, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        MissingMethodParameterException, SystemException, OptimisticLockingException, InvalidXmlException,
        InvalidContentException, InvalidStatusException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.unlock(id, param);
    }

    @PermitAll
    public String unlock(final String id, final String param, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        MissingMethodParameterException, SystemException, OptimisticLockingException, InvalidXmlException,
        InvalidContentException, InvalidStatusException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.unlock(id, param);
    }

    @RolesAllowed("Administrator")
    public String submit(final String id, final String param, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidStatusException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        InvalidXmlException, InvalidContentException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.submit(id, param);
    }

    @PermitAll
    public String submit(final String id, final String param, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidStatusException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        InvalidXmlException, InvalidContentException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.submit(id, param);
    }

    @RolesAllowed("Administrator")
    public String release(final String id, final String param, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidStatusException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        InvalidXmlException, InvalidContentException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.release(id, param);
    }

    @PermitAll
    public String release(final String id, final String param, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidStatusException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        InvalidXmlException, InvalidContentException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.release(id, param);
    }

    @RolesAllowed("Administrator")
    public String revise(final String id, final String param, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidStatusException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        XmlCorruptedException, InvalidContentException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.revise(id, param);
    }

    @PermitAll
    public String revise(final String id, final String param, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        InvalidStatusException, MissingMethodParameterException, SystemException, OptimisticLockingException,
        XmlCorruptedException, InvalidContentException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.revise(id, param);
    }

    @RolesAllowed("Administrator")
    public String retrieve(final String id, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, ContentRelationNotFoundException, SystemException {
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
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, SystemException {
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
    public String retrieveContentRelations(final Map parameterMap, final SecurityContext securityContext)
        throws InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentRelations(parameterMap);
    }

    @PermitAll
    public String retrieveContentRelations(final Map parameterMap, final String authHandle, final Boolean restAccess)
        throws InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentRelations(parameterMap);
    }

    @RolesAllowed("Administrator")
    public String retrieveProperties(final String id, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, SystemException {
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
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, SystemException {
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
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException,
        OptimisticLockingException, InvalidContentException, InvalidStatusException, LockingException,
        MissingAttributeValueException, SystemException, InvalidXmlException, ReferencedResourceNotFoundException,
        RelationPredicateNotFoundException, MissingMethodParameterException {
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
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException,
        OptimisticLockingException, InvalidContentException, InvalidStatusException, LockingException,
        MissingAttributeValueException, SystemException, InvalidXmlException, ReferencedResourceNotFoundException,
        RelationPredicateNotFoundException, MissingMethodParameterException {
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
    public String assignObjectPid(final String id, final String taskParam, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        MissingMethodParameterException, OptimisticLockingException, InvalidXmlException, SystemException,
        PidAlreadyAssignedException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.assignObjectPid(id, taskParam);
    }

    @PermitAll
    public String assignObjectPid(
        final String id, final String taskParam, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, LockingException,
        MissingMethodParameterException, OptimisticLockingException, InvalidXmlException, SystemException,
        PidAlreadyAssignedException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.assignObjectPid(id, taskParam);
    }

    @RolesAllowed("Administrator")
    public String retrieveMdRecords(final String id, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMdRecords(id);
    }

    @PermitAll
    public String retrieveMdRecords(final String id, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMdRecords(id);
    }

    @RolesAllowed("Administrator")
    public String retrieveRegisteredPredicates(final SecurityContext securityContext) throws InvalidContentException,
        InvalidXmlException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveRegisteredPredicates();
    }

    @PermitAll
    public String retrieveRegisteredPredicates(final String authHandle, final Boolean restAccess)
        throws InvalidContentException, InvalidXmlException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveRegisteredPredicates();
    }

    @RolesAllowed("Administrator")
    public String retrieveMdRecord(final String id, final String name, final SecurityContext securityContext)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException,
        MdRecordNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMdRecord(id, name);
    }

    @PermitAll
    public String retrieveMdRecord(final String id, final String name, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, ContentRelationNotFoundException,
        MdRecordNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMdRecord(id, name);
    }

    @RolesAllowed("Administrator")
    public String retrieveResources(final String id, final SecurityContext securityContext)
        throws ContentRelationNotFoundException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, SystemException {
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
        throws ContentRelationNotFoundException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResources(id);
    }
}