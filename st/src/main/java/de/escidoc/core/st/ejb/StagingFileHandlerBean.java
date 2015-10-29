package de.escidoc.core.st.ejb;

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
import de.escidoc.core.common.exceptions.application.missing.MissingMethodParameterException;
import de.escidoc.core.common.exceptions.application.notfound.StagingFileNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.st.ejb.interfaces.StagingFileHandlerLocal;
import de.escidoc.core.st.ejb.interfaces.StagingFileHandlerRemote;
import de.escidoc.core.st.service.interfaces.StagingFileHandlerInterface;

@Stateless(name = "StagingFileHandler")
@Remote(StagingFileHandlerRemote.class)
@Local(StagingFileHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class StagingFileHandlerBean implements StagingFileHandlerRemote, StagingFileHandlerLocal {

    private StagingFileHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(StagingFileHandlerBean.class);

    @PostConstruct
    public void create() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("StagingFileHandler.spring.ejb.context").getFactory();
            this.service = (StagingFileHandlerInterface) factory.getBean("service.StagingFileHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception StagingFileHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @PermitAll
    public String create(final EscidocBinaryContent binaryContent, final SecurityContext securityContext)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(binaryContent);
    }

    @PermitAll
    public String create(final EscidocBinaryContent binaryContent, final String authHandle, final Boolean restAccess)
        throws MissingMethodParameterException, AuthenticationException, AuthorizationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.create(binaryContent);
    }

    @PermitAll
    public EscidocBinaryContent retrieve(final String stagingFileId, final SecurityContext securityContext)
        throws StagingFileNotFoundException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(stagingFileId);
    }

    @PermitAll
    public EscidocBinaryContent retrieve(final String stagingFileId, final String authHandle, final Boolean restAccess)
        throws StagingFileNotFoundException, AuthenticationException, AuthorizationException,
        MissingMethodParameterException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.retrieve(stagingFileId);
    }
}