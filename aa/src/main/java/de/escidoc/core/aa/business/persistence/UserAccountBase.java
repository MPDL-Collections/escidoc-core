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
 *         table="user_account"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class UserAccountBase implements java.io.Serializable {

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
     *             column="loginname"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String loginname;

    /**
     *            @hibernate.property
     *             column="password"
     *             length="255"
     *         
     */
    private String password;

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
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="modified_by_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.EscidocRole"
     *         
     */
    private Set<EscidocRole> escidocRolesByModifiedById = new HashSet<EscidocRole>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.EscidocRole"
     *         
     */
    private Set<EscidocRole> escidocRolesByCreatorId = new HashSet<EscidocRole>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    private Set<RoleGrant> roleGrantsByUserId = new HashSet<RoleGrant>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    private Set<RoleGrant> roleGrantsByCreatorId = new HashSet<RoleGrant>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="revoker_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    private Set<RoleGrant> roleGrantsByRevokerId = new HashSet<RoleGrant>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserAccount"
     *         
     */
    private Set<UserAccount> userAccountsByCreatorId = new HashSet<UserAccount>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserGroup"
     *         
     */
    private Set<UserGroup> userGroupsByCreatorId = new HashSet<UserGroup>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="modified_by_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserGroup"
     *         
     */
    private Set<UserGroup> userGroupsByModifiedById = new HashSet<UserGroup>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserPreference"
     *         
     */
    private Set<UserPreference> userPreferencesByUserId = new HashSet<UserPreference>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserAttribute"
     *         
     */
    private Set<UserAttribute> userAttributesByUserId = new HashSet<UserAttribute>(0);

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="modified_by_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserAccount"
     *         
     */
    private Set<UserAccount> userAccountsByModifiedById = new HashSet<UserAccount>(0);

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="modified_by_id"         
     *         
     */
    private UserAccount userAccountByModifiedById;

    /**
     *            @hibernate.many-to-one
     *            @hibernate.column name="creator_id"         
     *         
     */
    private UserAccount userAccountByCreatorId;

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserLoginData"
     *         
     */
    private Set<UserLoginData> userLoginDatas = new HashSet<UserLoginData>(0);

    public UserAccountBase() {
    }

    public UserAccountBase(String name, String loginname) {
        this.name = name;
        this.loginname = loginname;
    }

    public UserAccountBase(Boolean active, String name, String loginname, String password, Date creationDate,
        Date lastModificationDate, Set<EscidocRole> escidocRolesByModifiedById,
        Set<EscidocRole> escidocRolesByCreatorId, Set<RoleGrant> roleGrantsByUserId,
        Set<RoleGrant> roleGrantsByCreatorId, Set<RoleGrant> roleGrantsByRevokerId,
        Set<UserAccount> userAccountsByCreatorId, Set<UserGroup> userGroupsByCreatorId,
        Set<UserGroup> userGroupsByModifiedById, Set<UserPreference> userPreferencesByUserId,
        Set<UserAttribute> userAttributesByUserId, Set<UserAccount> userAccountsByModifiedById,
        UserAccount userAccountByModifiedById, UserAccount userAccountByCreatorId, Set<UserLoginData> userLoginDatas) {
        this.active = active;
        this.name = name;
        this.loginname = loginname;
        this.password = password;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.escidocRolesByModifiedById = escidocRolesByModifiedById;
        this.escidocRolesByCreatorId = escidocRolesByCreatorId;
        this.roleGrantsByUserId = roleGrantsByUserId;
        this.roleGrantsByCreatorId = roleGrantsByCreatorId;
        this.roleGrantsByRevokerId = roleGrantsByRevokerId;
        this.userAccountsByCreatorId = userAccountsByCreatorId;
        this.userGroupsByCreatorId = userGroupsByCreatorId;
        this.userGroupsByModifiedById = userGroupsByModifiedById;
        this.userPreferencesByUserId = userPreferencesByUserId;
        this.userAttributesByUserId = userAttributesByUserId;
        this.userAccountsByModifiedById = userAccountsByModifiedById;
        this.userAccountByModifiedById = userAccountByModifiedById;
        this.userAccountByCreatorId = userAccountByCreatorId;
        this.userLoginDatas = userLoginDatas;
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
     *             column="loginname"
     *             unique="true"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "loginname", unique = true, nullable = false)
    public String getLoginname() {
        return this.loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    /**       
     *      *            @hibernate.property
     *             column="password"
     *             length="255"
     *         
     */

    @Column(name = "password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="modified_by_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.EscidocRole"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<EscidocRole> getEscidocRolesByModifiedById() {
        return this.escidocRolesByModifiedById;
    }

    public void setEscidocRolesByModifiedById(Set<EscidocRole> escidocRolesByModifiedById) {
        this.escidocRolesByModifiedById = escidocRolesByModifiedById;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.EscidocRole"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<EscidocRole> getEscidocRolesByCreatorId() {
        return this.escidocRolesByCreatorId;
    }

    public void setEscidocRolesByCreatorId(Set<EscidocRole> escidocRolesByCreatorId) {
        this.escidocRolesByCreatorId = escidocRolesByCreatorId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<RoleGrant> getRoleGrantsByUserId() {
        return this.roleGrantsByUserId;
    }

    public void setRoleGrantsByUserId(Set<RoleGrant> roleGrantsByUserId) {
        this.roleGrantsByUserId = roleGrantsByUserId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<RoleGrant> getRoleGrantsByCreatorId() {
        return this.roleGrantsByCreatorId;
    }

    public void setRoleGrantsByCreatorId(Set<RoleGrant> roleGrantsByCreatorId) {
        this.roleGrantsByCreatorId = roleGrantsByCreatorId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="revoker_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.RoleGrant"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "revoker_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<RoleGrant> getRoleGrantsByRevokerId() {
        return this.roleGrantsByRevokerId;
    }

    public void setRoleGrantsByRevokerId(Set<RoleGrant> roleGrantsByRevokerId) {
        this.roleGrantsByRevokerId = roleGrantsByRevokerId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserAccount"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserAccount> getUserAccountsByCreatorId() {
        return this.userAccountsByCreatorId;
    }

    public void setUserAccountsByCreatorId(Set<UserAccount> userAccountsByCreatorId) {
        this.userAccountsByCreatorId = userAccountsByCreatorId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="creator_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserGroup"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserGroup> getUserGroupsByCreatorId() {
        return this.userGroupsByCreatorId;
    }

    public void setUserGroupsByCreatorId(Set<UserGroup> userGroupsByCreatorId) {
        this.userGroupsByCreatorId = userGroupsByCreatorId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="modified_by_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserGroup"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserGroup> getUserGroupsByModifiedById() {
        return this.userGroupsByModifiedById;
    }

    public void setUserGroupsByModifiedById(Set<UserGroup> userGroupsByModifiedById) {
        this.userGroupsByModifiedById = userGroupsByModifiedById;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserPreference"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccountByUserId")
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserPreference> getUserPreferencesByUserId() {
        return this.userPreferencesByUserId;
    }

    public void setUserPreferencesByUserId(Set<UserPreference> userPreferencesByUserId) {
        this.userPreferencesByUserId = userPreferencesByUserId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserAttribute"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccountByUserId")
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserAttribute> getUserAttributesByUserId() {
        return this.userAttributesByUserId;
    }

    public void setUserAttributesByUserId(Set<UserAttribute> userAttributesByUserId) {
        this.userAttributesByUserId = userAttributesByUserId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="modified_by_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserAccount"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserAccount> getUserAccountsByModifiedById() {
        return this.userAccountsByModifiedById;
    }

    public void setUserAccountsByModifiedById(Set<UserAccount> userAccountsByModifiedById) {
        this.userAccountsByModifiedById = userAccountsByModifiedById;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="modified_by_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by_id")
    public UserAccount getUserAccountByModifiedById() {
        return this.userAccountByModifiedById;
    }

    public void setUserAccountByModifiedById(UserAccount userAccountByModifiedById) {
        this.userAccountByModifiedById = userAccountByModifiedById;
    }

    /**       
     *      *            @hibernate.many-to-one
     *            @hibernate.column name="creator_id"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    public UserAccount getUserAccountByCreatorId() {
        return this.userAccountByCreatorId;
    }

    public void setUserAccountByCreatorId(UserAccount userAccountByCreatorId) {
        this.userAccountByCreatorId = userAccountByCreatorId;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="user_id"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.aa.business.persistence.UserLoginData"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccount")
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<UserLoginData> getUserLoginDatas() {
        return this.userLoginDatas;
    }

    public void setUserLoginDatas(Set<UserLoginData> userLoginDatas) {
        this.userLoginDatas = userLoginDatas;
    }

}
