package de.escidoc.core.oai.ejb.interfaces;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for SetDefinitionHandler.
 */
public interface SetDefinitionHandlerRemoteHome extends EJBHome {

    String COMP_NAME = "java:comp/env/ejb/SetDefinitionHandler";

    String JNDI_NAME = "ejb/SetDefinitionHandler";

    SetDefinitionHandlerRemote create() throws CreateException, RemoteException;

}
