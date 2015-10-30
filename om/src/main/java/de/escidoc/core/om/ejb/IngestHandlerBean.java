package de.escidoc.core.om.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.CreateException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
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

import de.escidoc.core.common.exceptions.EscidocException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.ejb.interfaces.IngestHandlerLocal;
import de.escidoc.core.om.ejb.interfaces.IngestHandlerRemote;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import de.escidoc.core.om.service.interfaces.IngestHandlerInterface;

@Stateless(name = "IngestHandler")
@Remote(IngestHandlerRemote.class)
@Local(IngestHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class IngestHandlerBean implements IngestHandlerRemote, IngestHandlerLocal {

    private IngestHandlerInterface service;

    private SessionContext sessionCtx;

    private static final Logger LOGGER = LoggerFactory.getLogger(IngestHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (IngestHandlerInterface) BeanLocator.getBean("IngestHandler.spring.ejb.context",
                    "service.IngestHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception IngestHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String ingest(final String xmlData, final SecurityContext securityContext) throws EscidocException,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.ingest(xmlData);
    }

    @PermitAll
    public String ingest(final String xmlData, final String authHandle, final Boolean restAccess)
        throws EscidocException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.ingest(xmlData);
    }
}