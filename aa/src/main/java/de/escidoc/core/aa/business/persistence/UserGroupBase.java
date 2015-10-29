package de.escidoc.core.aa.business.persistence;

// Generated 26.10.2015 15:12:10 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="user_group"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class UserGroupBase implements java.io.Serializable {

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
     *             column="label"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String label;

    /**
     *            @hibernate.property
     *             column="active"
     *             length="1"
     *         
     */
    private Boolean active;

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
     *             column="description"
     *             length="2147483647"
     *         
     */
    private String description;

    /**
     *            @hibernate.property
     *             column="type"
     *             length="2147483647"
     *         
     */
    private String type;

    /**
     *            @hibernate.property
     *             column="email"
     *             length="2147483647"
     *         
     */
    private String email;

    /**
     *            @hibernate.property
     *             column="creation_date"
     *             length="29"
     *         
     */
    private Date creationDate;

    /**
     *            @hibernate.property
     *             column="last_modification_date"
     *             length="29"
     *         
     */
    private Date lastModificationDate;

    /**
     *            @hibernate.set
     *             inverse="true"
     *             lazy="true"
     *             cascade="save-update,delete,delete-orphan"
     *  
     *            @hibernate.collection-key
     *             column="user_group_id"
     *  
     *  
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserGroupMember"
     *         
     */
    private Set<UserGroupMember> members = new HashSet<UserGroupMember>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *  
     *            @hibernate.collection-key
     *             column="group_id"
     *  
     *  
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    private Set<RoleGrant> grants = new HashSet<RoleGrant>(0);

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="modified_by_id"
     *         
     */
    private UserAccount modifiedById;

    /**
     *            @hibernate.many-to-one
     *            @hibernate.column name="creator_id"
     *         
     */
    private UserAccount creatorId;

    public UserGroupBase() {
    }

    public UserGroupBase(String label, String name) {
        this.label = label;
        this.name = name;
    }

    public UserGroupBase(String label, Boolean active, String name, String description, String type, String email,
        Date creationDate, Date lastModificationDate, Set<UserGroupMember> members, Set<RoleGrant> grants,
        UserAccount modifiedById, UserAccount creatorId) {
        this.label = label;
        this.active = active;
        this.name = name;
        this.description = description;
        this.type = type;
        this.email = email;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.members = members;
        this.grants = grants;
        this.modifiedById = modifiedById;
        this.creatorId = creatorId;
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
     *             column="label"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "label", unique = true, nullable = false)
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**       
     *      *            @hibernate.property
     *             column="active"
     *             length="1"
     *         
     */

    @Column(name = "active", length = 1)
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
     *             column="description"
     *             length="2147483647"
     *         
     */

    @Column(name = "description", length = 2147483647)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**       
     *      *            @hibernate.property
     *             column="type"
     *             length="2147483647"
     *         
     */

    @Column(name = "type", length = 2147483647)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**       
     *      *            @hibernate.property
     *             column="email"
     *             length="2147483647"
     *         
     */

    @Column(name = "email", length = 2147483647)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**       
     *      *            @hibernate.property
     *             column="creation_date"
     *             length="29"
     *         
     */

    @Column(name = "creation_date", length = 29)
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**       
     *      *            @hibernate.property
     *             column="last_modification_date"
     *             length="29"
     *         
     */

    @Column(name = "last_modification_date", length = 29)
    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    /**       
     *      *            @hibernate.set
     *             inverse="true"
     *             lazy="true"
     *             cascade="save-update,delete,delete-orphan"
     *  
     *            @hibernate.collection-key
     *             column="user_group_id"
     *  
     *  
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserGroupMember"
     *         
     */
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "userGroup")
    @Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserGroupMember> getMembers() {
        return this.members;
    }

    public void setMembers(Set<UserGroupMember> members) {
        this.members = members;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *  
     *            @hibernate.collection-key
     *             column="group_id"
     *  
     *  
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<RoleGrant> getGrants() {
        return this.grants;
    }

    public void setGrants(Set<RoleGrant> grants) {
        this.grants = grants;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="modified_by_id"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id")
    public UserAccount getModifiedById() {
        return this.modifiedById;
    }

    public void setModifiedById(UserAccount modifiedById) {
        this.modifiedById = modifiedById;
    }

    /**       
     *      *            @hibernate.many-to-one
     *            @hibernate.column name="creator_id"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    public UserAccount getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(UserAccount creatorId) {
        this.creatorId = creatorId;
    }

}
