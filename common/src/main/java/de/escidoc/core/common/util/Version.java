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
 * Copyright 2006-2009 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.  
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.common.util;

/**
 * Container object for version information.
 * 
 * @author Andr&eacute; Schenk
 */
public class Version implements Comparable<Version> {

    private final int majorNumber;

    private final int minorNumber;

    private final int revisionNumber;

    /**
     * Create a new Version object.
     * 
     * @param majorNumber
     *            major number (first digit)
     * @param minorNumber
     *            minor number (second digit)
     * @param revisionNumber
     *            revision number (third digit)
     */
    public Version(final int majorNumber, final int minorNumber,
        final int revisionNumber) {
        this.majorNumber = majorNumber;
        this.minorNumber = minorNumber;
        this.revisionNumber = revisionNumber;
    }

    /**
     * Create a new version object.
     * 
     * @param version
     *            version number as string of the form "major.minor.revision"
     */
    public Version(final String version) {
        String[] parts = version.split("\\.");

        majorNumber = Integer.parseInt(parts[0]);
        minorNumber = Integer.parseInt(parts[1]);
        revisionNumber = Integer.parseInt(parts[2]);
    }

    /**
     * Compares this object with the specified object for order.
     * 
     * @param o
     *            the Object to be compared
     * 
     * @return -1, 0 or 1 as this object is less than, equal to, or greater than
     *         the specified object.
     */
    public final int compareTo(final Version o) {
        int result;

        if (o.majorNumber > majorNumber) {
            result = -1;
        }
        else if (o.majorNumber == majorNumber) {
            if (o.minorNumber > minorNumber) {
                result = -1;
            }
            else if (o.minorNumber == minorNumber) {
                if (o.revisionNumber > revisionNumber) {
                    result = -1;
                }
                else if (o.revisionNumber == revisionNumber) {
                    result = 0;
                }
                else {
                    result = 1;
                }
            }
            else {
                result = 1;
            }
        }
        else {
            result = 1;
        }
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj
     *            the reference object with which to compare.
     * 
     * @return true if this object is the same as the obj argument; false
     *         otherwise
     */
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj == null) {
            return false;
        }
        else if (getClass() != obj.getClass()) {
            return false;
        }

        Version other = (Version) obj;

        if (majorNumber != other.majorNumber) {
            return false;
        }
        else if (minorNumber != other.minorNumber) {
            return false;
        }
        else if (revisionNumber != other.revisionNumber) {
            return false;
        }
        return true;
    }

    /**
     * Return a hash code value for the object. This method is supported for the
     * benefit of hash tables such as those provided by
     * {@link java.util.Hashtable}.
     * 
     * @return a hash code value for this object
     */
    public final int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + majorNumber;
        result = prime * result + minorNumber;
        result = prime * result + revisionNumber;
        return result;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     */
    public final String toString() {
        return majorNumber + "." + minorNumber + '.' + revisionNumber;
    }
}
