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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
import org.springframework.security.core.context.SecurityContext;

import de.escidoc.core.common.business.fedora.EscidocBinaryContent;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.ejb.interfaces.FedoraRestDeviationHandlerLocal;
import de.escidoc.core.om.ejb.interfaces.FedoraRestDeviationHandlerRemote;
import de.escidoc.core.om.service.interfaces.FedoraRestDeviationHandlerInterface;

@Stateless(name = "FedoraRestDeviationHandler")
@Remote(FedoraRestDeviationHandlerRemote.class)
@Local(FedoraRestDeviationHandlerLocal.class)
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RunAs("Administrator")
public class FedoraRestDeviationHandlerBean
    implements FedoraRestDeviationHandlerRemote, FedoraRestDeviationHandlerLocal {

    private FedoraRestDeviationHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(FedoraRestDeviationHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("FedoraRestDeviationHandler.spring.ejb.context").getFactory();
            this.service = (FedoraRestDeviationHandlerInterface) factory.getBean("service.FedoraRestDeviationHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception FedoraRestDeviationHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public EscidocBinaryContent getDatastreamDissemination(
        final String pid, final String dsID, final Map parameters, final SecurityContext securityContext)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getDatastreamDissemination(pid, dsID, parameters);
    }

    @PermitAll
    public EscidocBinaryContent getDatastreamDissemination(
        final String pid, final String dsID, final Map parameters, final String authHandle, final Boolean restAccess)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getDatastreamDissemination(pid, dsID, parameters);
    }

    @RolesAllowed("Administrator")
    public String export(final String pid, final Map parameters, final SecurityContext securityContext)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.export(pid, parameters);
    }

    @PermitAll
    public String export(final String pid, final Map parameters, final String authHandle, final Boolean restAccess)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.export(pid, parameters);
    }

    @RolesAllowed("Administrator")
    public void cache(final String pid, final String xml, final SecurityContext securityContext) throws Exception,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.cache(pid, xml);
    }

    @PermitAll
    public void cache(final String pid, final String xml, final String authHandle, final Boolean restAccess)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.cache(pid, xml);
    }

    @RolesAllowed("Administrator")
    public void removeFromCache(final String pid, final SecurityContext securityContext) throws Exception,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.removeFromCache(pid);
    }

    @PermitAll
    public void removeFromCache(final String pid, final String authHandle, final Boolean restAccess) throws Exception,
        SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.removeFromCache(pid);
    }

    @RolesAllowed("Administrator")
    public void replaceInCache(final String pid, final String xml, final SecurityContext securityContext)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.replaceInCache(pid, xml);
    }

    @PermitAll
    public void replaceInCache(final String pid, final String xml, final String authHandle, final Boolean restAccess)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.replaceInCache(pid, xml);
    }
}