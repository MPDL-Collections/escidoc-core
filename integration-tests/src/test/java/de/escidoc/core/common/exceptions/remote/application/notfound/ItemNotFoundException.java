package de.escidoc.core.common.exceptions.remote.application.notfound;

public class ItemNotFoundException
    extends de.escidoc.core.common.exceptions.remote.application.notfound.ResourceNotFoundException {

    private static final long serialVersionUID = 5620093691551521744L;

    public ItemNotFoundException() {
    }

    public ItemNotFoundException(int httpStatusCode, String httpStatusLine, String httpStatusMsg) {
        super(httpStatusCode, httpStatusLine, httpStatusMsg);
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ItemNotFoundException))
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
