/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at license/ESCIDOC.LICENSE
 * or http://www.escidoc.de/license.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at license/ESCIDOC.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright 2006-2008 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.  
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.om.business.renderer;

import de.escidoc.core.common.business.Constants;
import de.escidoc.core.common.business.PropertyMapKeys;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.business.fedora.datastream.Datastream;
import de.escidoc.core.common.exceptions.application.missing.MissingParameterException;
import de.escidoc.core.common.exceptions.application.notfound.ComponentNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ContainerNotFoundException;
import de.escidoc.core.common.exceptions.application.notfound.ItemNotFoundException;
import de.escidoc.core.common.exceptions.application.security.AuthorizationException;
import de.escidoc.core.common.exceptions.system.EncodingSystemException;
import de.escidoc.core.common.exceptions.system.FedoraSystemException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.configuration.EscidocConfiguration;
import de.escidoc.core.common.util.date.Iso8601Util;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.service.BeanLocator;
import de.escidoc.core.common.util.xml.XmlUtility;
import de.escidoc.core.common.util.xml.factory.ContextXmlProvider;
import de.escidoc.core.common.util.xml.factory.XmlTemplateProvider;
import de.escidoc.core.om.business.fedora.container.FedoraContainerHandler;
import de.escidoc.core.om.business.fedora.context.Context;
import de.escidoc.core.om.business.fedora.context.FedoraContextHandler;
import de.escidoc.core.om.business.fedora.item.FedoraItemHandler;
import de.escidoc.core.om.business.renderer.interfaces.ContextRendererInterface;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * 
 * @author SWA
 * 
 */
public class VelocityXmlContextRenderer implements ContextRendererInterface {

    private static AppLogger log = new AppLogger(
        VelocityXmlContextRenderer.class.getName());

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.escidoc.core.om.business.renderer.interfaces.ContextRendererInterface
     * #render(de.escidoc.core.om.business.fedora.resources.Context)
     */
    public String render(final FedoraContextHandler contextHandler)
        throws SystemException {

        Context context = contextHandler.getContext();
        Map<String, Object> values = new HashMap<String, Object>();

        addCommonValues(context, values);
        addPropertiesValues(context, values);
        addResourcesValues(context, values);
        renderAdminDescriptors(contextHandler, values);

        values.put(XmlTemplateProvider.IS_ROOT_SUB_RESOURCE,
            XmlTemplateProvider.FALSE);

        return (ContextXmlProvider.getInstance().getContextXml(values));
    }

    /**
     * Render AdminDescriptors.
     * 
     * @param contextHandler
     *            ContextHandler.
     * @param values
     *            Context value map.
     * @return XML representation of admin-descriptors
     * @throws FedoraSystemException
     *             Thrown if retrieving admin-descriptors datastream from Fedora
     *             failed.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @throws EncodingSystemException
     *             Thrown if character encoding failed.
     */
    public String renderAdminDescriptors(
        final FedoraContextHandler contextHandler,
        final Map<String, Object> values) throws FedoraSystemException,
        WebserverSystemException, EncodingSystemException {

        addCommonValues(contextHandler.getContext(), values);

        HashMap<String, Datastream> admDescs =
            contextHandler.getContext().getAdminDescriptorsMap();

        Set<String> keys = admDescs.keySet();

        if (admDescs.size() > 0) {
            Iterator<String> it = keys.iterator();
            Vector<String> admDescriptors = new Vector<String>();

            while (it.hasNext()) {
                String name = it.next();
                Datastream adm = admDescs.get(name);
                admDescriptors.add(renderAdminDescriptor(contextHandler, name,
                    adm, false));
            }

            values.put("admsContent", admDescriptors);
        }

        Context context = contextHandler.getContext();
        values.put("admsHref", XmlUtility.getContextHref(context.getId())
            + "/admin-descriptors");
        values.put("admsTitle", "Admin Descriptors");
        values.put(XmlTemplateProvider.IS_ROOT_SUB_RESOURCE,
            XmlTemplateProvider.TRUE);

        return ContextXmlProvider.getInstance().getAdminDescriptorsXml(values);
    }

    /**
     * Render one admin-descriptor datastream.
     * 
     * @param contextHandler
     *            The contextHandler.
     * @param name
     *            Name of the datastream (unique).
     * @param admDesc
     *            The datastream.
     * @param isRoot
     *            Set true if admin-descriptor is to render as root element.
     * @return XML representation of one admin-descriptor datastream.
     * @throws EncodingSystemException
     *             Thrown if encoding convertion fails.
     * @throws WebserverSystemException
     *             Thrown if anything else fails.
     */

    public String renderAdminDescriptor(
        final FedoraContextHandler contextHandler, final String name,
        final Datastream admDesc, final boolean isRoot)
        throws EncodingSystemException, WebserverSystemException {
        if (admDesc.isDeleted()) {
            return "";
        }
        Map<String, Object> values = new HashMap<String, Object>();
        addCommonValues(contextHandler.getContext(), values);
        values.put("admHref",
            XmlUtility.getContextHref(contextHandler.getContext().getId())
                + "/admin-descriptors/admin-descriptor/" + name);
        values.put("admName", name);
        values.put("admRecordTitle", name + " admin descriptor.");
        values.put(XmlTemplateProvider.IS_ROOT_RESOURCES, isRoot);

        String admContent = null;
        try {
            admContent =
                new String(admDesc.getStream(), XmlUtility.CHARACTER_ENCODING);
        }
        catch (UnsupportedEncodingException e) {
            throw new EncodingSystemException(e);
        }
        values.put("admRecordContent", admContent);

        String result =
            ContextXmlProvider.getInstance().getAdminDescriptorXml(values);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.escidoc.core.om.business.renderer.interfaces.ContextRendererInterface
     * #renderProperties(de.escidoc.core.om.business.fedora.resources.Context)
     */
    public String renderProperties(final FedoraContextHandler contextHandler)
        throws WebserverSystemException {

        Context context = contextHandler.getContext();
        Map<String, Object> values = new HashMap<String, Object>();

        addCommonValues(context, values);
        addNamespaceValues(values);
        values.put(XmlTemplateProvider.IS_ROOT_PROPERTIES,
            XmlTemplateProvider.TRUE);
        try {
            addPropertiesValues(context, values);
        }
        catch (SystemException e) {
            throw new WebserverSystemException(e);
        }

        return (ContextXmlProvider.getInstance().getPropertiesXml(values));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.escidoc.core.om.business.renderer.interfaces.ContextRendererInterface
     * #renderResources(de.escidoc.core.om.business.fedora.resources.Context)
     */
    public String renderResources(final FedoraContextHandler contextHandler)
        throws WebserverSystemException {

        Context context = contextHandler.getContext();
        Map<String, Object> values = new HashMap<String, Object>();

        addCommonValues(context, values);
        values.put(XmlTemplateProvider.IS_ROOT_SUB_RESOURCE,
            XmlTemplateProvider.TRUE);
        addNamespaceValues(values);
        addResourcesValues(context, values);

        return ContextXmlProvider.getInstance().getResourcesXml(values);
    }

    /**
     * Render Context MemberList.
     * 
     * @param contextHandler
     *            FedoraContextHandler
     * @param memberList
     *            List of members.
     * @return XML representation with list of Context members.
     * @throws SystemException
     *             If anything fails.
     * @throws AuthorizationException
     *             Thrown if access to origin Item is restricted.
     */
    public String renderMemberList(
        final FedoraContextHandler contextHandler, final List<String> memberList)
        throws SystemException, AuthorizationException {

        Map<String, Object> values = new HashMap<String, Object>();
        Context context = contextHandler.getContext();
        addCommonValues(context, values);
        addMemberListValues(context, values, memberList);

        return (ContextXmlProvider.getInstance().getMemberListXml(values));
    }

    /**
     * See Interface for functional description.
     * 
     * @param contextHandler
     *            The ContextHandler
     * @param memberList
     *            The list with members
     * @return XML representation of member reference list
     * @throws SystemException
     *             Thrown if internal error occurs.
     * @throws AuthorizationException
     *             Thrown if access to origin Item is restricted.
     * @see de.escidoc.core.om.business.renderer.interfaces.ContextRendererInterface#renderMemberRefList(de.escidoc.core.om.business.fedora.context.FedoraContextHandler,
     *      java.util.List)
     */
    public String renderMemberRefList(
        final FedoraContextHandler contextHandler, final List<String> memberList)
        throws SystemException, AuthorizationException {

        Map<String, Object> values = new HashMap<String, Object>();
        Context context = contextHandler.getContext();
        addCommonValues(context, values);
        addMemberListValues(context, values, memberList);

        return (ContextXmlProvider.getInstance().getMemberRefListXml(values));
    }

    /**
     * Adds the common values to the provided map.
     * 
     * @param context
     *            The Context for that data shall be created.
     * @param values
     *            The map to add values to.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @oum
     */
    private void addCommonValues(
        final Context context, final Map<String, Object> values)
        throws WebserverSystemException {

        String lastModDate = null;
        try {
            lastModDate = context.getLastModificationDate();
            values.put(XmlTemplateProvider.VAR_LAST_MODIFICATION_DATE,
                Iso8601Util.getIso8601(Iso8601Util.parseIso8601(lastModDate)));
        }
        catch (Exception e) {
            throw new WebserverSystemException(
                "Unable to parse last-modification-date '" + lastModDate
                    + "' of context '" + context.getId() + "'!", e);
        }
        values.put("contextId", context.getId());
        values.put("contextTitle", context.getTitle());
        values.put("contextHref", context.getHref());

        addXlinkValues(values);
        addNamespaceValues(values);
    }

    /**
     * Add xlink values to value Map.
     * 
     * @param values
     *            Map where parameter to add.
     * @throws WebserverSystemException
     */
    private void addXlinkValues(final Map<String, Object> values) {

        values.put(XmlTemplateProvider.VAR_ESCIDOC_BASE_URL,
            System.getProperty(EscidocConfiguration.ESCIDOC_CORE_BASEURL));
        values.put(XmlTemplateProvider.VAR_XLINK_NAMESPACE_PREFIX,
            Constants.XLINK_NS_PREFIX);
        values.put(XmlTemplateProvider.VAR_XLINK_NAMESPACE,
            Constants.XLINK_NS_URI);
    }

    /**
     * Add namespace values to Map.
     * 
     * @param values
     *            Map where parameter to add.
     */
    private void addNamespaceValues(final Map<String, Object> values) {

        values.put("contextNamespacePrefix",
            Constants.CONTEXT_PROPERTIES_PREFIX);
        values.put("contextNamespace", Constants.CONTEXT_NAMESPACE_URI);

        addPropertiesNamespaceValues(values);
        addStructuralRelationsNamespaceValues(values);
    }

    /**
     * Add name relations spaces.
     * 
     * @param values
     *            Value Map for Velocity
     */
    protected void addStructuralRelationsNamespaceValues(
        final Map<String, Object> values) {

        values.put(XmlTemplateProvider.ESCIDOC_SREL_NS_PREFIX,
            Constants.STRUCTURAL_RELATIONS_NS_PREFIX);
        values.put(XmlTemplateProvider.ESCIDOC_SREL_NS,
            Constants.STRUCTURAL_RELATIONS_NS_URI);
    }

    /**
     * Add name properties spaces.
     * 
     * @param values
     *            Value Map for Velocity
     */
    protected void addPropertiesNamespaceValues(final Map<String, Object> values) {
        values.put(XmlTemplateProvider.ESCIDOC_PROPERTIES_NS_PREFIX,
            Constants.PROPERTIES_NS_PREFIX);
        values.put(XmlTemplateProvider.ESCIDOC_PROPERTIES_NS,
            Constants.PROPERTIES_NS_URI);
    }

    /**
     * Adds the properties values to the provided map.
     * 
     * @param context
     *            .
     * @param values
     *            Map with property values. New values are added to this Map.
     * @throws SystemException
     *             If anything fails.
     * @oum
     */
    private void addPropertiesValues(
        final Context context, final Map<String, Object> values)
        throws SystemException {

        Map<String, String> properties = context.getResourceProperties();

        values.put(XmlTemplateProvider.VAR_PROPERTIES_TITLE, "Properties");
        values.put(XmlTemplateProvider.VAR_PROPERTIES_HREF,
            XmlUtility.getContextPropertiesHref(context.getId()));

        values.put("contextName", context.getTitle());
        String description =
        // properties.get(PropertyMapKeys.LATEST_VERSION_DESCRIPTION);
            TripleStoreUtility.getInstance().getPropertiesElements(
                context.getId(), Constants.DC_NS_URI + "description");
        if (description != null) {
            values.put("contextDescription", description);
        }
        values.put("contextCreationDate", context.getCreationDate());

        values.put("contextStatus",
            properties.get(PropertyMapKeys.PUBLIC_STATUS));
        values.put("contextStatusComment",
            properties.get(PropertyMapKeys.PUBLIC_STATUS_COMMENT));
        values.put("contextType", properties.get(PropertyMapKeys.CONTEXT_TYPE));
        values.put("contextObjid", context.getId());

        values.put("contextCreatedById", context.getCreatedBy());
        values.put("contextCreatedByHref", Constants.USER_ACCOUNT_URL_BASE
            + context.getCreatedBy());
        values.put("contextCreatedByTitle",
            properties.get(PropertyMapKeys.CREATED_BY_TITLE));

        values
            .put("contextCurrentVersionModifiedById", context.getModifiedBy());
        values.put("contextCurrentVersionModifiedByHref",
            Constants.USER_ACCOUNT_URL_BASE + context.getModifiedBy());
        values.put("contextCurrentVersionModifiedByTitle",
            properties.get(PropertyMapKeys.LATEST_VERSION_MODIFIED_BY_TITLE));

        values
            .put("organizational-units", getOrganizationalUnitsContext(context
                .getOrganizationalUnitObjids()));
    }

    /**
     * Maybe a bad play. Better use renderOrganizationalUntitsRefs()
     * 
     * @param ouids
     *            Vector with IDs of OUs.
     * @return Vector with OU description (id, title, href)
     * @throws SystemException
     *             Thrown if retrieving OU context failed.
     */
    public Vector<HashMap<String, String>> getOrganizationalUnitsContext(
        final Vector<String> ouids) throws SystemException {

        Vector<HashMap<String, String>> ousContext =
            new Vector<HashMap<String, String>>();

        Iterator<String> it = ouids.iterator();
        while (it.hasNext()) {
            ousContext.add(getOrganizationalUnitContext(it.next()));
        }

        return (ousContext);
    }

    /**
     * OU context (id, title, href).
     * 
     * @param id
     *            The Id of the Organizational Unit.
     * @return HashMap with (id, title, href)
     * @throws SystemException
     *             Thrown if instance of TripleStore failed.
     */
    public HashMap<String, String> getOrganizationalUnitContext(final String id)
        throws SystemException {
        HashMap<String, String> ouContext = new HashMap<String, String>();

        ouContext.put("id", id);
        ouContext.put(
            "title",
            TripleStoreUtility.getInstance().getPropertiesElements(id,
                TripleStoreUtility.PROP_DC_TITLE));
        ouContext.put("href", XmlUtility.getOrganizationalUnitHref(id));
        return (ouContext);
    }

    /**
     * Adds the resource values to the provided map.
     * 
     * @param context
     *            The context for that data shall be created.
     * @param values
     *            The map to add values to.
     */
    private void addResourcesValues(
        final Context context, final Map<String, Object> values) {

        values.put(XmlTemplateProvider.RESOURCES_TITLE, "Resources");
        values.put("resourcesHref",
            XmlUtility.getContextResourcesHref(context.getId()));
        values.put("membersHref", XmlUtility.getContextHref(context.getId())
            + "/resources/members");
        values.put("membersTitle", "Members ");
    }

    /**
     * Add the namespace values to the provided map.
     * 
     * @param values
     *            The map to add values to.
     */
    private void addListNamespaceValues(final Map<String, Object> values) {

        values.put("organizationalUnitsNamespacePrefix",
            Constants.ORGANIZATIONAL_UNIT_LIST_PREFIX);
        values.put("organizationalUnitsNamespace",
            Constants.ORGANIZATIONAL_UNIT_LIST_NAMESPACE_URI);
    }

    /**
     * Add the values to render the member list.
     * 
     * @param context
     *            The Context.
     * @param values
     * @param memberList
     * @throws SystemException
     * @throws AuthorizationException
     *             Thrown if access to origin Item is restricted.
     */
    private void addMemberListValues(
        final Context context, final Map<String, Object> values,
        final List<String> memberList) throws SystemException,
        AuthorizationException {

        FedoraItemHandler itemHandler =
            (FedoraItemHandler) BeanLocator.getBean("Om.spring.ejb.context",
                "business.FedoraItemHandler");
        FedoraContainerHandler containerHandler =
            (FedoraContainerHandler) BeanLocator.getBean(
                "Om.spring.ejb.context", "business.FedoraContainerHandler");

        values.put("memberListNamespacePrefix", Constants.MEMBER_LIST_PREFIX);
        values.put("memberListNamespace", Constants.MEMBER_LIST_NAMESPACE_URI);
        values.put("memberListPrefix", Constants.XLINK_PREFIX);

        StringBuffer sb = new StringBuffer();

        Iterator<String> it = memberList.iterator();
        while (it.hasNext()) {
            String objectId = it.next();
            final String objectType =
                TripleStoreUtility.getInstance().getObjectType(objectId);
            try {
                if (Constants.ITEM_OBJECT_TYPE.equals(objectType)) {
                    sb.append(itemHandler.retrieve(objectId));
                }
                else if (Constants.CONTAINER_OBJECT_TYPE.equals(objectType)) {
                    sb.append(containerHandler.retrieve(objectId));
                }
                else {
                    String msg =
                        "FedoraContextHandler.retrieveMemberRefs:"
                            + " can not return object with unknown type: "
                            + objectId + ". Write comment.";
                    sb.append("<!-- " + msg + " -->");
                    log.error(msg);
                }
            }
            catch (ItemNotFoundException e) {
                String msg =
                    "FedoraContextHandler.retrieveMemberRefs:"
                        + " can not retrieve object";
                Map<String, Object> extValues = new HashMap<String, Object>();
                addXlinkValues(extValues);
                addListNamespaceValues(extValues);
                extValues.put("href", "/ir/" + objectType + "/" + objectId);
                extValues.put("objid", objectId);
                extValues.put("msg", msg);
                sb.append(ContextXmlProvider
                    .getInstance().getWithdrawnMessageXml(extValues));

                log.error(msg);
            }
            catch (ComponentNotFoundException e) {
                String msg =
                    "FedoraContextHandler.retrieveMemberRefs:can not retrieve object";
                Map<String, Object> extValues = new HashMap<String, Object>();
                addXlinkValues(extValues);
                addListNamespaceValues(extValues);
                extValues.put("href", "/ir/" + objectType + "/" + objectId);
                extValues.put("objid", objectId);
                extValues.put("msg", msg);
                sb.append(ContextXmlProvider
                    .getInstance().getWithdrawnMessageXml(extValues));

                log.error(msg);
            }
            catch (MissingParameterException e) {
                throw new SystemException(
                    "Should not occure in FedoraContextHandler.retrieveMembers",
                    e);
            }
            catch (ContainerNotFoundException e) {
                String msg =
                    "FedoraContextHandler.retrieveMembers:can not retrieve object";
                Map<String, Object> extValues = new HashMap<String, Object>();
                addXlinkValues(extValues);
                addListNamespaceValues(extValues);
                extValues.put("href", "/ir/" + objectType + "/" + objectId);
                extValues.put("objid", objectId);
                extValues.put("msg", msg);
                sb.append(ContextXmlProvider
                    .getInstance().getWithdrawnMessageXml(extValues));
                log.error(msg);
            }
            catch (EncodingSystemException e) {
                throw new SystemException(
                    "Should not occure in FedoraContextHandler.retrieveMembers",
                    e);
            }

        }

        values.put("memberList", sb.toString());
    }

    /**
     * 
     * @param id
     *            The id of the context.
     * @return Returns the name of a context.
     * @throws SystemException
     *             Thrown in case of an internal error.
     * @oum
     */
    public String getName(final String id) throws SystemException {
        return getProperty(id, TripleStoreUtility.PROP_NAME);
    }

    /**
     * 
     * @param id
     *            The id of the context.
     * @param property
     *            The name of the property.
     * @return Returns a value of a property of an organizational unit.
     * @throws SystemException
     *             Thrown in case of an internal error.
     */
    private String getProperty(final String id, final String property)
        throws SystemException {

        return TripleStoreUtility.getInstance().getPropertiesElements(id,
            property);
    }
}
