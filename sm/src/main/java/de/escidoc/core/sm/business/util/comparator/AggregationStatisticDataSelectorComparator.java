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

package de.escidoc.core.sm.business.util.comparator;

import java.util.Comparator;

import de.escidoc.core.sm.business.persistence.hibernate.AggregationStatisticDataSelector;

/**
 * Sorts AggregationStatisticDataSelector-Objects according to their listIndex.
 * 
 * @author MIH
 * @sm
 */
public class AggregationStatisticDataSelectorComparator 
        implements Comparator<AggregationStatisticDataSelector> {

    /**
     * compares listIndex.
     * 
     * @param a1 AggregationStatisticDataSelector1
     * @param a2 AggregationStatisticDataSelector2
     * @return Returns compare result.
     * 
     * @sm
     */
    public int compare(
            final AggregationStatisticDataSelector a1, 
            final AggregationStatisticDataSelector a2) {
        return Integer.toString(a1.getListIndex())
                .compareTo(Integer.toString(a2.getListIndex()));
    }
}
