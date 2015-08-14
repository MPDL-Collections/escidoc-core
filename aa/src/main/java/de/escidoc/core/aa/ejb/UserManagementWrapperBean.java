package de.escidoc.core.aa.ejb;

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

import de.escidoc.core.aa.ejb.interfaces.UserManagementWrapperLocal;
import de.escidoc.core.aa.ejb.interfaces.UserManagementWrapperRemote;
import de.escidoc.core.aa.service.interfaces.UserManagementWrapperInterface;
import de.escidoc.core.common.exceptions.application.security.AuthenticationException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.service.UserContext;

@Stateless(name = "UserManagementWrapper")
@Remote(UserManagementWrapperRemote.class)
@Local(UserManagementWrapperLocal.class)
@TransactionManagement(TransactionManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RunAs("Administrator")
public class UserManagementWrapperBean implements UserManagementWrapperRemote, UserManagementWrapperLocal {

    private UserManagementWrapperInterface service;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementWrapperBean.class);

    @PermitAll
    @PostConstruct
    public void create() throws CreateException {
        try {
            final BeanFactoryLocator beanFactoryLocator = SingletonBeanFactoryLocator.getInstance();
            final BeanFactory factory =
                beanFactoryLocator.useBeanFactory("UserManagementWrapper.spring.ejb.context").getFactory();
            this.service = (UserManagementWrapperInterface) factory.getBean("service.UserManagementWrapper");
        }
        catch (Exception e) {
            LOGGER.error("ejbCreate(): Exception UserManagementWrapperComponent: " + e);
            throw new CreateException(e.getMessage()); // Ignore FindBugs
        }
    }

    @RolesAllowed("Administrator")
    public void logout(final SecurityContext securityContext) throws AuthenticationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.logout();
    }

    @PermitAll
    public void logout(final String authHandle, final Boolean restAccess) throws AuthenticationException,
        SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.logout();
    }

    @RolesAllowed("Administrator")
    public void initHandleExpiryTimestamp(final String handle, final SecurityContext securityContext)
        throws AuthenticationException, SystemException {
        try {
            UserContext.setUserContext(securityContext);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.initHandleExpiryTimestamp(handle);
    }

    @PermitAll
    public void initHandleExpiryTimestamp(final String handle, final String authHandle, final Boolean restAccess)
        throws AuthenticationException, SystemException {
        try {
            UserContext.setUserContext(authHandle);
            UserContext.setRestAccess(restAccess);
        }
        catch (Exception e) {
            throw new SystemException("Initialization of security context failed.", e);
        }
        service.initHandleExpiryTimestamp(handle);
    }
}