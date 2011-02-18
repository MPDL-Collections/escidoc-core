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
package de.escidoc.core.common.util.stax.handler;

import de.escidoc.core.common.util.logger.AppLogger;
import de.escidoc.core.common.util.stax.StaxParser;
import de.escidoc.core.common.util.xml.stax.events.EndElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RelsExtContentRelationsReadHandlerForUpdate
    extends RelsExtContentRelationsReadHandler {

    private final List<String> relationsStrings = new ArrayList<String>();;

    public RelsExtContentRelationsReadHandlerForUpdate(StaxParser parser) {
        super(parser);
    }

    public final List<String> getRelationsStrings() {
        return this.relationsStrings;
    }

    public EndElement endElement(EndElement element) {
        if (this.isInRelation()) {

            String relationData = this.getPredicate() + "###" + this.getTargetId();
            relationsStrings.add(relationData);
            this.setTargetId(null);
            this.setPredicate(null);
            this.setInRelation(false);
        }
        return element;
    }

}
