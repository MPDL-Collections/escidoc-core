package de.escidoc.core.aa.business.persistence;

// Generated 26.10.2015 15:12:10 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="scope_def"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class ScopeDefBase implements java.io.Serializable {

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
     *             column="object_type"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String objectType;

    /**
     *            @hibernate.property
     *             column="attribute_id"
     *             length="255"
     *         
     */
    private String attributeId;

    /**
     *            @hibernate.property
     *             column="attribute_object_type"
     *             length="255"
     *         
     */
    private String attributeObjectType;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="role_id"         
     *         
     */
    private EscidocRole escidocRole;

    public ScopeDefBase() {
    }

    public ScopeDefBase(String objectType) {
        this.objectType = objectType;
    }

    public ScopeDefBase(String objectType, String attributeId, String attributeObjectType, EscidocRole escidocRole) {
        this.objectType = objectType;
        this.attributeId = attributeId;
        this.attributeObjectType = attributeObjectType;
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
     *             column="object_type"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "object_type", nullable = false)
    public String getObjectType() {
        return this.objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**       
     *      *            @hibernate.property
     *             column="attribute_id"
     *             length="255"
     *         
     */

    @Column(name = "attribute_id")
    public String getAttributeId() {
        return this.attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**       
     *      *            @hibernate.property
     *             column="attribute_object_type"
     *             length="255"
     *         
     */

    @Column(name = "attribute_object_type")
    public String getAttributeObjectType() {
        return this.attributeObjectType;
    }

    public void setAttributeObjectType(String attributeObjectType) {
        this.attributeObjectType = attributeObjectType;
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
