/**
 * ReadonlyAttributeViolationException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package de.escidoc.core.common.exceptions.remote.application.violated;

public class ReadonlyAttributeViolationException
    extends de.escidoc.core.common.exceptions.remote.application.violated.ReadonlyViolationException {

    private static final long serialVersionUID = -7422302171774338182L;

    public ReadonlyAttributeViolationException() {
    }

    public ReadonlyAttributeViolationException(int httpStatusCode, String httpStatusLine, String httpStatusMsg) {
        super(httpStatusCode, httpStatusLine, httpStatusMsg);
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ReadonlyAttributeViolationException))
            return false;
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj);
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }
}
