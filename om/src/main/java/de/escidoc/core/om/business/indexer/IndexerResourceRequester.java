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
package de.escidoc.core.om.business.indexer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;

import de.escidoc.core.common.business.fedora.EscidocBinaryContent;
import de.escidoc.core.common.business.fedora.MIMETypedStream;
import de.escidoc.core.common.business.fedora.TripleStoreUtility;
import de.escidoc.core.common.exceptions.system.SystemException;
import de.escidoc.core.common.exceptions.system.TripleStoreSystemException;
import de.escidoc.core.common.servlet.invocation.BeanMethod;
import de.escidoc.core.common.servlet.invocation.MethodMapper;
import de.escidoc.core.common.servlet.invocation.exceptions.MethodNotFoundException;
import de.escidoc.core.common.util.IOUtils;
import de.escidoc.core.common.util.service.ConnectionUtility;
import de.escidoc.core.common.util.xml.XmlUtility;
import de.escidoc.core.common.util.xml.factory.FoXmlProvider;

/**
 * @author Michael Hoppe
 *         <p/>
 *         Class gets resources for indexer, 
 *         either from eSciDocCore-Framework or from external URL.
 *         Caches resources in resourcesCache.
 */
@Service
public class IndexerResourceRequester {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexerResourceRequester.class);

    private static final int BUFFER_SIZE = 0xFFFF;

    @Autowired
    @Qualifier("common.CommonMethodMapper")
    private MethodMapper methodMapper;

    @Autowired
    @Qualifier("business.TripleStoreUtility")
    private TripleStoreUtility tripleStoreUtility;

    @Autowired
    @Qualifier("escidoc.core.common.util.service.ConnectionUtility")
    private ConnectionUtility connectionUtility;

    /**
     * Get resource with given identifier.
     *
     * @param identifier identifier
     * @return Object resource-object
     * @throws SystemException e
     * @throws de.escidoc.core.common.exceptions.system.TripleStoreSystemException
     */
    @Cacheable(cacheName = "resourcesCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = { @Property(name = "includeMethod", value = "false") }))
    public Object getResource(final String identifier) throws SystemException, TripleStoreSystemException {
        final String href = getHref(identifier);
        if (identifier.startsWith("http")) {
            return getExternalResource(href);
        }
        else {
            return getInternalResource(href);
        }
    }

    /**
     * Get resource with given identifier.
     *
     * @param identifier identifier
     * @return Object resource-object
     * @throws SystemException e
     * @throws de.escidoc.core.common.exceptions.system.TripleStoreSystemException
     */
    public Object getResourceUncached(final String identifier) throws SystemException, TripleStoreSystemException {
        final String href = getHref(identifier);
        if (identifier.startsWith("http")) {
            return getExternalResource(href);
        }
        else {
            return getInternalResource(href);
        }
    }

    /**
     * Set resource with given identifier in cache.
     *
     * @param identifier identifier
     * @param resource resource-object
     * @return Object resource-object
     */
    @Cacheable(cacheName = "resourcesCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = { @Property(name = "includeMethod", value = "false") }))
    public Object setResource(@PartialCacheKey
    final String identifier, final Object resource) {
        return resource;
    }

    /**
     * delete resource with given identifier from cache.
     *
     * @param identifier identifier
     */
    @TriggersRemove(cacheName = "resourcesCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = { @Property(name = "includeMethod", value = "false") }))
    public void deleteResource(final String identifier) {
    }

    /**
     * get resource with given identifier from framework.
     *
     * @param identifier identifier
     * @throws SystemException e
     */
    private Object getInternalResource(final String identifier) throws SystemException {
        try {
            final BeanMethod method = methodMapper.getMethod(identifier, null, null, "GET", "");
            final Object content = method.invokeWithProtocol(null);
            if (content != null && "EscidocBinaryContent".equals(content.getClass().getSimpleName())) {
                final EscidocBinaryContent escidocBinaryContent = (EscidocBinaryContent) content;
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                final InputStream in = escidocBinaryContent.getContent();
                try {
                    final byte[] bytes = new byte[BUFFER_SIZE];
                    int i;
                    while ((i = in.read(bytes)) > -1) {
                        out.write(bytes, 0, i);
                    }
                    out.flush();
                    final MIMETypedStream stream =
                        new MIMETypedStream(escidocBinaryContent.getMimeType(), out.toByteArray(), null);
                    return stream;
                }
                catch (final Exception e) {
                    throw new SystemException(e);
                }
                finally {
                    IOUtils.closeStream(in);
                    IOUtils.closeStream(out);
                }
            }
            else if (content != null) {
                final String xml = (String) content;
                return xml;
            }
        }
        catch (final InvocationTargetException e) {
            if (!"AuthorizationException".equals(e.getTargetException().getClass().getSimpleName())
                && !"InvalidStatusException".equals(e.getTargetException().getClass().getSimpleName())) {
                throw new SystemException(e);
            }
        }
        catch (final MethodNotFoundException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Error on caching internal resource.");
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error on caching internal resource.", e);
            }
        }
        catch (final Exception e) {
            throw new SystemException(e);
        }
        return null;
    }

    /**
     * get resource from given URL.
     *
     * @param identifier identifier
     * @throws SystemException e
     */
    private Object getExternalResource(final String identifier) throws SystemException {
        ByteArrayOutputStream out = null;
        InputStream in = null;
        try {
            final HttpResponse httpResponse = connectionUtility.getRequestURL(new URL(identifier));

            if (httpResponse != null) {

                // TODO testen ob header mitgeschickt wird
                final Header ctype = httpResponse.getFirstHeader("Content-Type");
                final String mimeType =
                    ctype != null ? ctype.getValue() : FoXmlProvider.MIME_TYPE_APPLICATION_OCTET_STREAM;

                out = new ByteArrayOutputStream();
                in = httpResponse.getEntity().getContent();
                int byteval;
                while ((byteval = in.read()) > -1) {
                    out.write(byteval);
                }
                final MIMETypedStream stream = new MIMETypedStream(mimeType, out.toByteArray(), null);
                return stream;
            }
        }
        catch (final Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Error on caching external resource.");
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Error on caching external resource.", e);
            }
        }
        finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(out);
        }
        return null;
    }

    /**
     * generate href out of pid.
     *
     * @param identifier identifier
     * @return String href
     * @throws SystemException e
     * @throws de.escidoc.core.common.exceptions.system.TripleStoreSystemException
     */
    private String getHref(final String identifier) throws SystemException, TripleStoreSystemException {
        String href = identifier;
        if (!href.contains("/")) {
            // objectId provided, generate href
            // get object-type
            href = XmlUtility.getObjidWithoutVersion(href);
            final String objectType = tripleStoreUtility.getObjectType(href);
            if (objectType == null) {
                throw new SystemException("couldnt get objectType for object " + href);
            }

            href = this.tripleStoreUtility.getHref(objectType, identifier);
        }
        if (!href.startsWith("http") && !href.startsWith("/")) {
            href = "/" + href;
        }
        return href;
    }
}
