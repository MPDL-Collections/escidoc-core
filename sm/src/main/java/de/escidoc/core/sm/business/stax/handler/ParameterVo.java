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
 * Copyright 2009 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.
 * All rights reserved.  Use is subject to license terms.
 */

package de.escidoc.core.sm.business.stax.handler;

import org.joda.time.DateTime;

/**
 * Holds parameter data.
 * 
 * @author MIH
 * @sm
 */
public class ParameterVo {

    private String name = null;
    private String stringValue = null;
    private Double decimalValue = null;
    private DateTime dateValue = null;

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * @return the stringValue
     */
    public final String getStringValue() {
        return stringValue;
    }
    /**
     * @param stringValue the stringValue to set
     */
    public final void setStringValue(final String stringValue) {
        this.stringValue = stringValue;
    }
    /**
     * @return the decimalValue
     */
    public final Double getDecimalValue() {
        return decimalValue;
    }
    /**
     * @param decimalValue the decimalValue to set
     */
    public final void setDecimalValue(final Double decimalValue) {
        this.decimalValue = decimalValue;
    }
    /**
     * @return the dateValue
     */
    public final DateTime getDateValue() {
        return dateValue;
    }
    /**
     * @param dateValue the dateValue to set
     */
    public final void setDateValue(final DateTime dateValue) {
        this.dateValue = dateValue;
    }

}
