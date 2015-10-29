package de.escidoc.core.sm.business.persistence.hibernate;

// Generated 26.10.2015 15:32:54 by Hibernate Tools 3.2.2.GA

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="statistic_data"
 *        @hibernate.mapping
 *         schema="sm"
 *     
 */
@Entity
@Table(name = "statistic_data", schema = "sm")
public class StatisticData implements java.io.Serializable {

    /**
     *            @hibernate.id
     *             generator-class="uuid"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    private String id;

    /**
     *            @hibernate.property
     *             column="xml_data"
     *             length="2147483647"
     *             not-null="true"
     *         
     */
    private String xmlData;

    /**
     *            @hibernate.property
     *             column="timemarker"
     *             length="29"
     *             not-null="true"
     *         
     */
    private Date timemarker;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="scope_id"         
     *         
     */
    private Scope scope;

    public StatisticData() {
    }

    public StatisticData(String xmlData, Date timemarker) {
        this.xmlData = xmlData;
        this.timemarker = timemarker;
    }

    public StatisticData(String xmlData, Date timemarker, Scope scope) {
        this.xmlData = xmlData;
        this.timemarker = timemarker;
        this.scope = scope;
    }

    /**       
     *      *            @hibernate.id
     *             generator-class="uuid"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    @GenericGenerator(name = "generator", strategy = "uuid", parameters = @Parameter(name = "schema", value = "sm"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", nullable = false)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**       
     *      *            @hibernate.property
     *             column="xml_data"
     *             length="2147483647"
     *             not-null="true"
     *         
     */

    @Column(name = "xml_data", nullable = false, length = 2147483647)
    public String getXmlData() {
        return this.xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    /**       
     *      *            @hibernate.property
     *             column="timemarker"
     *             length="29"
     *             not-null="true"
     *         
     */

    @Column(name = "timemarker", nullable = false, length = 29)
    public Date getTimemarker() {
        return this.timemarker;
    }

    public void setTimemarker(Date timemarker) {
        this.timemarker = timemarker;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="scope_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scope_id")
    public Scope getScope() {
        return this.scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

}
