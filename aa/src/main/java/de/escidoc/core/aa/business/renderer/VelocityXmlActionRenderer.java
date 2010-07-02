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
package de.escidoc.core.aa.business.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import de.escidoc.core.aa.business.persistence.UnsecuredActionList;
import de.escidoc.core.aa.business.renderer.interfaces.ActionRendererInterface;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.xml.factory.ActionXmlProvider;

/**
 * Action renderer implementation using the velocity template engine.
 * 
 * @author TTE
 * @spring.bean id="eSciDoc.core.aa.business.renderer.VelocityXmlActionRenderer"
 * @aa
 */
public class VelocityXmlActionRenderer extends AbstractRenderer
    implements ActionRendererInterface {

    /** The logger. */
    private static final AppLogger LOG =
        new AppLogger(VelocityXmlActionRenderer.class.getName());

    /**
     * Pattern used to detect white spaces.
     */
    private static final Pattern PATTERN_WHITESPACE = Pattern.compile("\\s");

    // CHECKSTYLE:JAVADOC-OFF

    /**
     * See Interface for functional description.
     * 
     * @param actions
     * @return
     * @throws WebserverSystemException
     * @see de.escidoc.core.aa.business.renderer.interfaces.RoleRendererInterface
     *      #renderUnsecuredActionList(de.escidoc.core.aa.business.persistence.UnsecuredActionList)
     * @aa
     */
    public String renderUnsecuredActionList(final UnsecuredActionList actions)
        throws WebserverSystemException {

        final Map<String, Object> values = new HashMap<String, Object>();
        addRdfValues(values);
        values.put("contextId", actions.getContextId());

        final List<String> actionIdList;
        if (actions.getActionIds() != null) {
            String[] actionIds =
                PATTERN_WHITESPACE.split(actions.getActionIds());
            actionIdList = new ArrayList<String>(actionIds.length);
            for (int i = 0; i < actionIds.length; i++) {
                actionIdList.add(actionIds[i]);
            }
        }
        else {
            actionIdList = new ArrayList<String>(0);
        }
        values.put("actionIds", actionIdList);

        return getActionXmlProvider().getUnsecuredActionsXml(values);
    }

    // CHECKSTYLE:JAVADOC-ON

    /**
     * Gets the {@link ActionXmlProvider} object.
     * 
     * @return Returns the {@link ActionXmlProvider} object.
     * @throws WebserverSystemException
     *             Thrown in case of an internal error.
     * @aa
     */
    private ActionXmlProvider getActionXmlProvider()
        throws WebserverSystemException {

        return ActionXmlProvider.getInstance();
    }
}
