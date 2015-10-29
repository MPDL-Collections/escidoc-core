package de.escidoc.core.aa.business.persistence;

// Generated 26.10.2015 15:12:10 by Hibernate Tools 3.2.2.GA

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
 *         table="escidoc_policies"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@Entity
@Table(name = "escidoc_policies", schema = "aa")
public class EscidocPolicy implements java.io.Serializable {

    /**
     *            @hibernate.id
     *             generator-class="de.escidoc.core.common.persistence.EscidocIdGenerator"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    private String id;

    /**
     *            @hibernate.property
     *             column="xml"
     *             length="2147483647"
     *         
     */
    private String xml;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="role_id"         
     *         
     */
    private EscidocRole escidocRole;

    public EscidocPolicy() {
    }

    public EscidocPolicy(String xml, EscidocRole escidocRole) {
        this.xml = xml;
        this.escidocRole = escidocRole;
    }

    /**       
     *      *            @hibernate.id
     *             generator-class="de.escidoc.core.common.persistence.EscidocIdGenerator"
     *             type="java.lang.String"
     *             column="id"
     *         
     */
    @GenericGenerator(name = "generator", strategy = "de.escidoc.core.common.persistence.EscidocIdGenerator", parameters = @Parameter(name = "schema", value = "aa"))
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
     *             column="xml"
     *             length="2147483647"
     *         
     */

    @Column(name = "xml", length = 2147483647)
    public String getXml() {
        return this.xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="role_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    public EscidocRole getEscidocRole() {
        return this.escidocRole;
    }

    public void setEscidocRole(EscidocRole escidocRole) {
        this.escidocRole = escidocRole;
    }

}
