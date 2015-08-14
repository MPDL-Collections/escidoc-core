package de.escidoc.core.statistic;

import java.math.BigDecimal;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Represents a parameter in a {@link StatisticRecord}.
 */
public interface Parameter {

    /**
     * Returns the name of the parameter.
     *
     * @return the name of the parameter
     */
    String getName();

    /**
     * Returns the value of the paramter as {@link String}.
     *
     * @return the value of the paramter as {@link String}.
     */
    String getStringValue();

    /**
     * Returns the value of the paramter as {@link String}.
     *
     * @return the value of the paramter as {@link String}.
     */
    BigDecimal getDecimalValue();

    /**
     * Returns the value of the paramter as {@link XMLGregorianCalendar}.
     *
     * @return the value of the paramter as {@link XMLGregorianCalendar}.
     */
    XMLGregorianCalendar getDateValue();

}
