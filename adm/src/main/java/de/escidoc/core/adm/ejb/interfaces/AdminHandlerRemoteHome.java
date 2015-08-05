package de.escidoc.core.adm.ejb.interfaces;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for AdminHandler.
 */
public interface AdminHandlerRemoteHome extends EJBHome {

    String COMP_NAME = "java:comp/env/ejb/AdminHandler";

    String JNDI_NAME = "ejb/AdminHandler";

    AdminHandlerRemote create() throws CreateException, RemoteException;

}
