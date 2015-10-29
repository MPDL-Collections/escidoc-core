package de.escidoc.core.cmm.ejb;

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

import de.escidoc.core.cmm.ejb.interfaces.ContentModelHandlerLocal;
import de.escidoc.core.cmm.ejb.interfaces.ContentModelHandlerLocalHome;
import de.escidoc.core.cmm.ejb.interfaces.ContentModelHandlerRemote;
import de.escidoc.core.cmm.ejb.interfaces.ContentModelHandlerRemoteHome;
import de.escidoc.core.cmm.service.interfaces.ContentModelHandlerInterface;
import de.escidoc.core.common.business.fedora.EscidocBinaryContent;
import de.escidoc.core.common.exceptions.EscidocException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidContentException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidSearchQueryException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.application.invalid.InvalidXmlException;
import de.escidoc.core.common.exceptions.application.invalid.XmlCorruptedException;
import de.escidoc.core.common.exceptions.application.invalid.XmlSchemaValidationException;
import de.escidoc.core.common.exceptions.application.missing.MissingAttributeValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingElementValueException;
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ContentModelNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContentStreamNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ResourceNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.application.violated.LockingException;
import de.escidoc.core.common.exceptions.application.violated.OptimisticLockingException;
import de.escidoc.core.common.exceptions.application.violated.ReadonlyVersionException;
import de.escidoc.core.common.exceptions.application.violated.ResourceInUseException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;

@Stateless(name = "ContentModelHandler")
@RemoteHome(ContentModelHandlerRemoteHome.class)
@LocalHome(ContentModelHandlerLocalHome.class)
@Remote(ContentModelHandlerRemote.class)
@Local(ContentModelHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class ContentModelHandlerBean implements ContentModelHandlerRemote, ContentModelHandlerLocal {

    private ContentModelHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentModelHandlerBean.class);

    @PostConstruct
    @PermitAll
    public void ejbCreate() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("ContentModelHandler.spring.ejb.context").getFactory();
            this.service = (ContentModelHandlerInterface) factory.getBean("service.ContentModelHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception ContentModelHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String ingest(final String xmlData, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException, SystemException, MissingAttributeValueException,
        MissingElementValueException, ContentModelNotFoundException, InvalidXmlException, XmlCorruptedException,
        InvalidStatusException, EscidocException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.ingest(xmlData);
    }

    @RolesAllowed("Administrator")
    public String ingest(final String xmlData, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException, SystemException,
        MissingAttributeValueException, MissingElementValueException, ContentModelNotFoundException,
        InvalidXmlException, XmlCorruptedException, InvalidStatusException, EscidocException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.ingest(xmlData);
    }

    @RolesAllowed("Administrator")
    public String create(final String xmlData, final SecurityContext securityContext) throws InvalidContentException,
        MissingAttributeValueException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, XmlCorruptedException, XmlSchemaValidationException {
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
        throws InvalidContentException, MissingAttributeValueException, SystemException, AuthenticationException,
        AuthorizationException, MissingMethodParameterException, XmlCorruptedException, XmlSchemaValidationException {
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
    public void delete(final String id, final SecurityContext securityContext) throws SystemException,
        ContentModelNotFoundException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, LockingException, InvalidStatusException, ResourceInUseException {

        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.delete(id);
    }

    @PermitAll
    public void delete(final String id, final String authHandle, final Boolean restAccess) throws SystemException,
        ContentModelNotFoundException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, LockingException, InvalidStatusException, ResourceInUseException {
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
    public String retrieve(final String id, final SecurityContext securityContext)
        throws ContentModelNotFoundException, SystemException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException {
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
        throws ContentModelNotFoundException, SystemException, MissingMethodParameterException,
        AuthenticationException, AuthorizationException {
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
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
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
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
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
    public String retrieveContentStreams(final String id, final SecurityContext securityContext)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentStreams(id);
    }

    @PermitAll
    public String retrieveContentStreams(final String id, final String authHandle, final Boolean restAccess)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentStreams(id);
    }

    @RolesAllowed("Administrator")
    public String retrieveContentStream(final String id, final String name, final SecurityContext securityContext)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentStream(id, name);
    }

    @PermitAll
    public String retrieveContentStream(
        final String id, final String name, final String authHandle, final Boolean restAccess)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentStream(id, name);
    }

    @RolesAllowed("Administrator")
    public EscidocBinaryContent retrieveContentStreamContent(
        final String id, final String name, final SecurityContext securityContext)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, ContentStreamNotFoundException, InvalidStatusException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentStreamContent(id, name);
    }

    @PermitAll
    public EscidocBinaryContent retrieveContentStreamContent(
        final String id, final String name, final String authHandle, final Boolean restAccess)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, ContentStreamNotFoundException, InvalidStatusException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentStreamContent(id, name);
    }

    @RolesAllowed("Administrator")
    public String retrieveResources(final String id, final SecurityContext securityContext)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
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
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
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
    public String retrieveVersionHistory(final String id, final SecurityContext securityContext)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveVersionHistory(id);
    }

    @PermitAll
    public String retrieveVersionHistory(final String id, final String authHandle, final Boolean restAccess)
        throws ContentModelNotFoundException, SystemException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveVersionHistory(id);
    }

    @RolesAllowed("Administrator")
    public String retrieveContentModels(final Map parameterMap, final SecurityContext securityContext)
        throws InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentModels(parameterMap);
    }

    @PermitAll
    public String retrieveContentModels(final Map parameterMap, final String authHandle, final Boolean restAccess)
        throws InvalidSearchQueryException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveContentModels(parameterMap);
    }

    @RolesAllowed("Administrator")
    public String update(final String id, final String xmlData, final SecurityContext securityContext)
        throws InvalidXmlException, ContentModelNotFoundException, OptimisticLockingException, SystemException,
        AuthenticationException, AuthorizationException, MissingMethodParameterException, ReadonlyVersionException,
        MissingAttributeValueException, InvalidContentException {
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
        throws InvalidXmlException, ContentModelNotFoundException, OptimisticLockingException, SystemException,
        AuthenticationException, AuthorizationException, MissingMethodParameterException, ReadonlyVersionException,
        MissingAttributeValueException, InvalidContentException {
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
    public EscidocBinaryContent retrieveMdRecordDefinitionSchemaContent(
        final String id, final String name, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException, ContentModelNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMdRecordDefinitionSchemaContent(id, name);
    }

    @PermitAll
    public EscidocBinaryContent retrieveMdRecordDefinitionSchemaContent(
        final String id, final String name, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException,
        ContentModelNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveMdRecordDefinitionSchemaContent(id, name);
    }

    @RolesAllowed("Administrator")
    public EscidocBinaryContent retrieveResourceDefinitionXsltContent(
        final String id, final String name, final SecurityContext securityContext) throws AuthenticationException,
        AuthorizationException, MissingMethodParameterException, ContentModelNotFoundException,
        ResourceNotFoundException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResourceDefinitionXsltContent(id, name);
    }

    @PermitAll
    public EscidocBinaryContent retrieveResourceDefinitionXsltContent(
        final String id, final String name, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, AuthorizationException, MissingMethodParameterException,
        ContentModelNotFoundException, ResourceNotFoundException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieveResourceDefinitionXsltContent(id, name);
    }
}