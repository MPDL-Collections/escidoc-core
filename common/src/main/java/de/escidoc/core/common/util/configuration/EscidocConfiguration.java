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
package de.escidoc.core.common.util.configuration;

import de.escidoc.core.common.exceptions.EscidocException;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

/**
 * Handles properties.
 * 
 * @author Michael Hoppe
 * @common
 * 
 */
public final class EscidocConfiguration {

    public static final String SEARCH_PROPERTIES_DIRECTORY =
        "search.properties.directory";

    public static final String GSEARCH_URL = "gsearch.url";

    public static final String GSEARCH_PASSWORD = "gsearch.fedoraPass";

    public static final String FEDORA_URL = "fedora.url";

    public static final String FEDORA_USER = "fedora.user";

    public static final String BUILD_NUMBER = "escidoc-core.build";

    public static final String ADMIN_EMAIL = "escidoc-core.admin-email";

    public static final String ESCIDOC_REPOSITORY_NAME =
        "escidoc-core.repository-name";

    public static final String FEDORA_PASSWORD = "fedora.password";

    public static final String ESCIDOC_CORE_NOTIFY_INDEXER_ENABLED =
        "escidoc-core.notify.indexer.enabled";

    public static final String ESCIDOC_CORE_BASEURL = "escidoc-core.baseurl";

    public static final String ESCIDOC_CORE_SELFURL = "escidoc-core.selfurl";

    public static final String ESCIDOC_CORE_PROXY_HOST =
        "escidoc-core.proxyHost";

    public static final String ESCIDOC_CORE_PROXY_PORT =
        "escidoc-core.proxyPort";

    public static final String ESCIDOC_CORE_NON_PROXY_HOSTS =
        "escidoc-core.nonProxyHosts";

    public static final String ESCIDOC_CORE_XSD_PATH = "escidoc-core.xsd-path";

    public static final String ESCIDOC_CORE_OM_CONTENT_CHECKSUM_ALGORITHM =
        "escidoc-core.om.content.checksum-algorithm";

    public static final String ESCIDOC_CORE_XSLT_STD = "escidoc-core.xslt.std";

    public static final String ESCIDOC_CORE_IDENTIFIER_PREFIX =
        "escidoc-core.identifier.prefix";

    public static final String ESCIDOC_CORE_PID_SYSTEM_FACTORY =
        "escidoc-core.PidSystemFactory";

    public static final String ESCIDOC_CORE_DUMMYPID_GLOBALPREFIX =
        "escidoc-core.dummyPid.globalPrefix";

    public static final String ESCIDOC_CORE_DUMMYPID_LOCALPREFIX =
        "escidoc-core.dummyPid.localPrefix";

    public static final String ESCIDOC_CORE_DUMMYPID_NAMESPACE =
        "escidoc-core.dummyPid.pidNamespace";

    public static final String ESCIDOC_CORE_DUMMYPID_SEPARATOR =
        "escidoc-core.dummyPid.separator";

    public static final String ESCIDOC_CORE_USERHANDLE_LIFETIME =
        "escidoc-core.userHandle.lifetime";

    public static final String ESCIDOC_CORE_USERHANDLE_COOKIE_LIFETIME =
        "escidoc-core.userHandle.cookie.lifetime";

    public static final String ESCIDOC_CORE_USERHANDLE_COOKIE_VERSION =
        "escidoc-core.userHandle.cookie.version";

    public static final String ESCIDOC_CORE_PID_SERVICE_HOST =
        "escidoc-core.PidSystemRESTService.host";

    public static final String ESCIDOC_CORE_PID_RESTSERVICE_USER =
        "escidoc-core.PidSystemRESTService.user";

    public static final String ESCIDOC_CORE_PID_RESTSERVICE_PASSWORD =
        "escidoc-core.PidSystemRESTService.password";

    public static final String ESCIDOC_CORE_PID_GLOBALPREFIX =
        "escidoc-core.PidSystem.globalPrefix";

    public static final String ESCIDOC_CORE_PID_LOCALPREFIX =
        "escidoc-core.PidSystem.localPrefix";

    public static final String ESCIDOC_CORE_PID_NAMESPACE =
        "escidoc-core.PidSystem.namespace";

    public static final String ESCIDOC_CORE_PID_SEPARATOR =
        "escidoc-core.PidSystem.separator";

    public static final String DE_FIZ_ESCIDOC_OM_SERVICE_PROVIDER_URL =
        "de.escidoc.core.om.service.provider.url";

    public static final String DE_FIZ_ESCIDOC_SM_SERVICE_PROVIDER_URL =
        "de.escidoc.core.sm.service.provider.url";

    public static final String ESCIDOC_CORE_DEFAULT_JNDI_URL =
        "escidoc-core.default.jndi.url";

    public static final String SM_FRAMEWORK_SCOPE_ID = "sm.framework.scope.id";

    public static final String ESCIDOC_CORE_QUEUE_USER =
        "escidoc-core.queue.user";

    public static final String ESCIDOC_CORE_QUEUE_PASSWORD =
        "escidoc-core.queue.password";

    public static final String ESCIDOC_CORE_DATASOURCE_INDEX_PREFIX_LENGTH =
        "escidoc-core.datasource.index.prefix.length";

    public static final String CONTENT_RELATIONS_URL =
        "escidoc-core.ontology.url";

    public static final String SRW_URL = "srw.url";

    private static final String TRUE = "true";

    private static final String ONE = "1";

    /**
     * This property should be set to the number of policy sets of roles that
     * should be cached.
     */
    public static final String ESCIDOC_CORE_AA_CACHE_ROLES_SIZE =
        "escidoc-core.aa.cache.roles.size";

    /**
     * This property should be set to the number of expected concurrent users.
     */
    public static final String ESCIDOC_CORE_AA_CACHE_USERS_SIZE =
        "escidoc-core.aa.cache.users.size";

    /**
     * This property should be set to the number of expected concurrent users.
     */
    public static final String ESCIDOC_CORE_AA_CACHE_GROUPS_SIZE =
        "escidoc-core.aa.cache.groups.size";

    /**
     * This property should be set to the number of expected resources, for that
     * the result of @ link XacmlFunctionRoleIsGranted} shall be cached (for
     * each user and role).
     */
    public static final String ESCIDOC_CORE_AA_CACHE_RESOURCES_IN_ROLE_IS_GRANTED_SIZE =
        "escidoc-core.aa.cache.resources-in-role-is-granted.size";

    /**
     * This property should be set to the number of system objects that should
     * be cached for a request at one point of time.
     */
    public static final String ESCIDOC_CORE_AA_CACHE_ATTRIBUTES_SIZE =
        "escidoc-core.aa.cache.attributes.size";

    /**
     * This property should be set to the name of the user-attribute that
     * defines the organizational unit the user belongs to .
     */
    public static final String ESCIDOC_CORE_AA_OU_ATTRIBUTE_NAME =
        "escidoc-core.aa.attribute-name.ou";

    /**
     * This property should be set to the name of the user-attribute that
     * defines the common name of the user.
     */
    public static final String ESCIDOC_CORE_AA_COMMON_NAME_ATTRIBUTE_NAME =
        "escidoc-core.aa.attribute-name.common-name";

    /**
     * This property should be set to the name of the user-attribute that
     * defines the unique loginname of the user.
     */
    public static final String ESCIDOC_CORE_AA_PERSISTENT_ID_ATTRIBUTE_NAME =
        "escidoc-core.aa.attribute-name.persistent-id";

    /**
     * This property should be set to the number of system objects that should
     * be cached for the indexer.
     */
    public static final String ESCIDOC_CORE_INDEXER_CACHE_SIZE =
        "escidoc-core.om.indexer.cache.size";

    /**
     * Digilib Server (URL).
     */
    public static final String DIGILIB_SCALER = "digilib.scaler";

    /**
     * Digilib Client (URL).
     */
    public static final String DIGILIB_CLIENT = "digilib.digimage";

    private static final AppLogger LOG = new AppLogger(
        EscidocConfiguration.class.getName());

    private static EscidocConfiguration instance = null;

    private final Properties properties;

    private static final String PROPERTIES_FILENAME =
        "escidoc-core.custom.properties";

    private static final String PROPERTIES_DEFAULT_FILENAME =
        "escidoc-core.properties";

    private static final String PROPERTIES_CONSTANT_FILENAME =
        "escidoc-core.constant.properties";

    /**
     * Private Constructor, in order to prevent instantiation of this utility
     * class. read the Properties and fill it in properties attribute.
     * 
     * @throws EscidocException
     *             e
     * 
     * @common
     */
    private EscidocConfiguration() throws EscidocException {
        System.setProperty("java.awt.headless", "true");
        this.properties = loadProperties();
    }

    /**
     * Returns and perhaps initializes Object.
     * 
     * @return EscidocConfiguration self
     * @throws IOException
     *             Thrown if properties loading fails.
     * 
     * @common
     */
    public static synchronized EscidocConfiguration getInstance()
        throws IOException {
        if (instance == null) {
            try {
                instance = new EscidocConfiguration();
            }
            catch (EscidocException e) {
                StringWriter w = new StringWriter();
                PrintWriter pw = new PrintWriter(w);
                e.printStackTrace(pw);
                throw new IOException(
                    "Problem while loading properties! Caused by:\n"
                        + w.toString(), e);
            }
        }
        return instance;
    }

    /**
     * Returns the property with the given name or null if property was not
     * found.
     * 
     * @param name
     *            The name of the Property.
     * @return Value of the given Property as String.
     * @common
     */
    public String get(final String name) {
        return (String) properties.get(name);
    }

    /**
     * Returns the property with the given name or the second parameter as
     * default value if property was not found.
     * 
     * @param name
     *            The name of the Property.
     * @param defaultValue
     *            The default vaule if property isn't given.
     * @return Value of the given Property as String.
     * @common
     */
    public String get(final String name, final String defaultValue) {
        String prop = (String) properties.get(name);

        if (prop == null) {
            prop = defaultValue;
        }
        return prop;
    }

    /**
     * Returns the property with the given name as a boolean value. The result
     * is set to true if the property value as String has the value "true" or
     * "1".
     * 
     * @param name
     *            The name of the Property.
     * @return Value of the given Property as boolean.
     * @common
     */
    public boolean getAsBoolean(final String name) {
        boolean result = false;
        String prop = ((String) properties.get(name)).toLowerCase();

        if ((prop != null) && (TRUE.equals(prop) || ONE.equals(prop))) {
            result = true;
        }
        return result;
    }

    /**
     * Returns the property with the given name as a long value.
     * 
     * @param name
     *            The name of the Property.
     * @return Value of the given Property as long value.
     * @common
     */
    public long getAsLong(final String name) {

        return Long.parseLong(properties.getProperty(name));
    }

    /**
     * Loads the Properties from the possible files. First loads properties from
     * the file escidoc-core.properties. Afterwards tries to load specific
     * properties from the file escidoc-core.custom.properties and merges them
     * with the default properties. If any key is included in default and
     * specific properties, the value of the specific property will overwrite
     * the default property.
     * 
     * @return The properties
     * @throws SystemException
     *             If the loading of the default properties (file
     *             escidoc-core.properties) fails.
     * 
     * @common
     */
    private synchronized Properties loadProperties() throws SystemException {
        Properties result;
        try {
            result = getProperties(PROPERTIES_DEFAULT_FILENAME);
        }
        catch (IOException e) {
            throw new SystemException("properties not found.", e);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Default properties: " + result);
        }
        Properties specific;
        try {
            specific = getProperties(PROPERTIES_FILENAME);
        }
        catch (IOException e) {
            specific = new Properties();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error on loading specific properties.");
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Specific properties: " + specific);
        }
        result.putAll(specific);

        // Load constant properties
        Properties constant = new Properties();
        try {
            constant = getProperties(PROPERTIES_CONSTANT_FILENAME);
        }
        catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error on loading contant properties. "
                    + e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Constant properties: " + constant);
        }
        result.putAll(constant);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Merged properties: " + result);
        }
        // set Properties as System-Variables
        for (Object o : result.keySet()) {
            String key = (String) o;
            String value = result.getProperty(key);
            value = replaceEnvVariables(value);
            System.setProperty(key, value);
        }
        return result;
    }

    /**
     * Get the properties from a file.
     * 
     * @param filename
     *            The name of the properties file.
     * @return The properties.
     * @throws IOException
     *             If access to the specified file fails.
     */
    private synchronized Properties getProperties(final String filename)
        throws IOException {

        Properties result = new Properties();
        InputStream propertiesStream = getInputStream(filename);
        result.load(propertiesStream);
        return result;
    }

    /**
     * Get an InputStream for the given file.
     * 
     * @param filename
     *            The name of the file.
     * @return The InputStream or null if the file could not be located.
     * @throws FileNotFoundException
     *             If access to the specified file fails.
     */
    private synchronized InputStream getInputStream(final String filename)
        throws IOException {
        final ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext(new String[] {});
        final Resource[] resource =
            applicationContext.getResources("classpath*:**/" + filename);
        if (resource.length == 0) {
            throw new FileNotFoundException("Unable to find file '" + filename
                + "' in classpath.");
        }
        return resource[0].getInputStream();
    }

    /**
     * Retrieves the Properties from File.
     * 
     * @param property
     *            value of property with env-variable-syntax (e.g. ${java.home})
     * @return String replaced env-variables
     * 
     * @common
     */
    private synchronized String replaceEnvVariables(final String property) {
        String replacedProperty = property;
        if (property.contains("${")) {
            String[] envVariables = property.split("\\}.*?\\$\\{");
            if (envVariables != null) {
                for (int i = 0; i < envVariables.length; i++) {
                    envVariables[i] =
                        envVariables[i].replaceFirst(".*?\\$\\{", "");
                    envVariables[i] = envVariables[i].replaceFirst("\\}.*", "");
                    if (System.getProperty(envVariables[i]) != null
                        && System.getProperty(envVariables[i]).length() != 0) {
                        String envVariable =
                            System.getProperty(envVariables[i]);
                        envVariable = envVariable.replaceAll("\\\\", "/");
                        replacedProperty =
                            replacedProperty.replaceAll("\\$\\{"
                                + envVariables[i] + '}', envVariable);
                    }
                }
            }
        }
        return replacedProperty;
    }

    /**
     * Get the full URL to the eSciDoc Infrastructure itself extend with the
     * provided path. E.g. path = "/xsd/schema1.xsd" leads to
     * http://localhost:8080/xsd/schema1.xsd.
     * 
     * @param path
     *            path which is to append on the eSciDoc selfUrl.
     * @return baseUrl with appended path
     */
    public String appendToSelfURL(final String path) {

        String selfUrl = get(ESCIDOC_CORE_SELFURL);

        if (selfUrl != null) {
            if (selfUrl.endsWith("/")) {
                selfUrl = selfUrl.substring(0, selfUrl.length() - 1);
            }
            selfUrl = selfUrl + path;
        }
        return selfUrl;
    }
}
