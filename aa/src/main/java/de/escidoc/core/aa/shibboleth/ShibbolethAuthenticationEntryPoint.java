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
 * Copyright 2008 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.  
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.aa.shibboleth;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AuthenticationEntryPoint;

import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.string.StringUtility;
import de.escidoc.core.common.util.xml.XmlUtility;

public class ShibbolethAuthenticationEntryPoint
    implements AuthenticationEntryPoint {

    private final static AppLogger LOG =
        new AppLogger(ShibbolethAuthenticationEntryPoint.class.getName());

    private String serviceProviderBaseUrl = null;

    private String sessionInitiatorPath = null;

    public void commence(
        final ServletRequest request, final ServletResponse response,
        final AuthenticationException authException) throws IOException,
        ServletException {

        LOG.debug("Entered");

        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        // FIXME:URL!!!
        final StringBuffer target =
            StringUtility.concatenate(serviceProviderBaseUrl, "aa/login");

        final String queryString = httpRequest.getQueryString();
        if (queryString != null) {
            target.append("?");
            target.append(queryString);
        }

        final String redirectUrl;
        if (httpRequest.getHeader(ShibbolethDetails.SHIB_SESSION_ID) == null) {
            // seems to be request to the url without protection by shibboleth
            // redirect to shibbolized url
            redirectUrl = target.toString();
        }
        else {
            // seems to be request to the url protected by shibboleth, but
            // shibboleth session has to be initiated as the user could not be
            // authenticated
            redirectUrl =
                StringUtility.concatenateToString(serviceProviderBaseUrl,
                    sessionInitiatorPath, "?target=", URLEncoder.encode(target
                        .toString(), XmlUtility.CHARACTER_ENCODING));
        }

        ((HttpServletResponse) response).sendRedirect(redirectUrl);
    }

    /**
     * Injects the base url of the service provider.
     * 
     * @param serviceProviderBaseUrl
     *            The serviceProviderBaseUrl to inject.
     * @aa
     */
    public void setServiceProviderBaseUrl(final String serviceProviderBaseUrl) {

        if (serviceProviderBaseUrl.endsWith("/")) {
            this.serviceProviderBaseUrl = serviceProviderBaseUrl;
        }
        else {
            this.serviceProviderBaseUrl =
                StringUtility.concatenateToString(serviceProviderBaseUrl, "/");
        }
    }

    /**
     * Injects the path to the session initiator. This path is relative to the
     * service provider base URL.
     * 
     * @param sessionInitiatorPath
     *            The sessionInitiatorPath to inject.
     * @aa
     */
    public void setSessionInitiatorPath(final String sessionInitiatorPath) {

        if (sessionInitiatorPath.startsWith("/")) {
            this.sessionInitiatorPath = sessionInitiatorPath.substring(1);
        }
        else {
            this.sessionInitiatorPath = sessionInitiatorPath;
        }
    }

}
