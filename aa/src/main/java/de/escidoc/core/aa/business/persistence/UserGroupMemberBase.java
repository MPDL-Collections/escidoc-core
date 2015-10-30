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
 *         table="user_group_member"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class UserGroupMemberBase implements java.io.Serializable {

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
     *             not-null="true"
     *         
     */
    private String name;

    /**
     *            @hibernate.property
     *             column="type"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String type;

    /**
     *            @hibernate.property
     *             column="value"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String value;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="user_group_id"
     *         
     */
    private UserGroup userGroup;

    public UserGroupMemberBase() {
    }

    public UserGroupMemberBase(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public UserGroupMemberBase(String name, String type, String value, UserGroup userGroup) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.userGroup = userGroup;
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
     *             not-null="true"
     *         
     */

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**       
     *      *            @hibernate.property
     *             column="type"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "type", nullable = false)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**       
     *      *            @hibernate.property
     *             column="value"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "value", nullable = false)
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="user_group_id"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_group_id")
    public UserGroup getUserGroup() {
        return this.userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

}
