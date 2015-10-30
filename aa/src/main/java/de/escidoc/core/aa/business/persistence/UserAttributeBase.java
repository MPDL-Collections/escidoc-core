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
 *         table="user_attribute"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class UserAttributeBase implements java.io.Serializable {

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
     *             column="name"
     *             length="255"
     *         
     */
    private String name;

    /**
     *            @hibernate.property
     *             column="value"
     *             length="255"
     *         
     */
    private String value;

    /**
     *            @hibernate.property
     *             column="internal"
     *             length="1"
     *         
     */
    private Boolean internal;

    /**
     *            @hibernate.many-to-one
     *            @hibernate.column name="user_id"
     *         
     */
    private UserAccount userAccountByUserId;

    public UserAttributeBase() {
    }

    public UserAttributeBase(String name, String value, Boolean internal, UserAccount userAccountByUserId) {
        this.name = name;
        this.value = value;
        this.internal = internal;
        this.userAccountByUserId = userAccountByUserId;
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
     *             column="name"
     *             length="255"
     *         
     */

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**       
     *      *            @hibernate.property
     *             column="value"
     *             length="255"
     *         
     */

    @Column(name = "value")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**       
     *      *            @hibernate.property
     *             column="internal"
     *             length="1"
     *         
     */

    @Column(name = "internal", length = 1)
    public Boolean getInternal() {
        return this.internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    /**       
     *      *            @hibernate.many-to-one
     *            @hibernate.column name="user_id"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserAccount getUserAccountByUserId() {
        return this.userAccountByUserId;
    }

    public void setUserAccountByUserId(UserAccount userAccountByUserId) {
        this.userAccountByUserId = userAccountByUserId;
    }

}
