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

import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.service.UserContext;
import de.escidoc.core.om.ejb.interfaces.FedoraDescribeDeviationHandlerLocal;
import de.escidoc.core.om.ejb.interfaces.FedoraDescribeDeviationHandlerRemote;
import de.escidoc.core.om.service.interfaces.ContentRelationHandlerInterface;
import de.escidoc.core.om.service.interfaces.FedoraDescribeDeviationHandlerInterface;

@Stateless(name = "FedoraDescribeDeviationHandler")
@Remote(FedoraDescribeDeviationHandlerRemote.class)
@Local(FedoraDescribeDeviationHandlerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
@RunAs("Administrator")
public class FedoraDescribeDeviationHandlerBean
    implements FedoraDescribeDeviationHandlerRemote, FedoraDescribeDeviationHandlerLocal {

    private FedoraDescribeDeviationHandlerInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(FedoraDescribeDeviationHandlerBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {

            this.service =
                (FedoraDescribeDeviationHandlerInterface) BeanLocator.getBean(
                    "FedoraDescribeDeviationHandler.spring.ejb.context", "service.FedoraDescribeDeviationHandler");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception FedoraDescribeDeviationHandlerComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public String getFedoraDescription(final Map parameters, final SecurityContext securityContext) throws Exception,
        SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getFedoraDescription(parameters);
    }

    @PermitAll
    public String getFedoraDescription(final Map parameters, final String authHandle, final Boolean restAccess)
        throws Exception, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        return service.getFedoraDescription(parameters);
    }
}