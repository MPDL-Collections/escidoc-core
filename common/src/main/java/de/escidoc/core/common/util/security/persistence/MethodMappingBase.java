package de.escidoc.core.common.util.security.persistence;

// Generated 26.10.2015 15:26:24 by Hibernate Tools 3.2.2.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *        @hibernate.class
 *         table="method_mappings"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class MethodMappingBase implements java.io.Serializable {

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
     *             column="class_name"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String className;

    /**
     *            @hibernate.property
     *             column="method_name"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String methodName;

    /**
     *            @hibernate.property
     *             column="action_name"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String actionName;

    /**
     *            @hibernate.property
     *             column="exec_before"
     *             length="1"
     *             not-null="true"
     *         
     */
    private boolean execBefore;

    /**
     *            @hibernate.property
     *             column="single_resource"
     *             length="1"
     *             not-null="true"
     *         
     */
    private boolean singleResource;

    /**
     *            @hibernate.property
     *             column="resource_not_found_exception"
     *             length="2147483647"
     *         
     */
    private String resourceNotFoundException;

    /**
     *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="method_mapping"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.common.util.security.persistence.InvocationMapping"
     *         
     */
    private Set<InvocationMapping> invocationMappings = new HashSet<InvocationMapping>(0);

    public MethodMappingBase() {
    }

    public MethodMappingBase(String className, String methodName, String actionName, boolean execBefore,
        boolean singleResource) {
        this.className = className;
        this.methodName = methodName;
        this.actionName = actionName;
        this.execBefore = execBefore;
        this.singleResource = singleResource;
    }

    public MethodMappingBase(String className, String methodName, String actionName, boolean execBefore,
        boolean singleResource, String resourceNotFoundException, Set<InvocationMapping> invocationMappings) {
        this.className = className;
        this.methodName = methodName;
        this.actionName = actionName;
        this.execBefore = execBefore;
        this.singleResource = singleResource;
        this.resourceNotFoundException = resourceNotFoundException;
        this.invocationMappings = invocationMappings;
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
     *             column="class_name"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "class_name", nullable = false)
    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**       
     *      *            @hibernate.property
     *             column="method_name"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "method_name", nullable = false)
    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**       
     *      *            @hibernate.property
     *             column="action_name"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "action_name", nullable = false)
    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**       
     *      *            @hibernate.property
     *             column="exec_before"
     *             length="1"
     *             not-null="true"
     *         
     */

    @Column(name = "exec_before", nullable = false, length = 1)
    public boolean isExecBefore() {
        return this.execBefore;
    }

    public void setExecBefore(boolean execBefore) {
        this.execBefore = execBefore;
    }

    /**       
     *      *            @hibernate.property
     *             column="single_resource"
     *             length="1"
     *             not-null="true"
     *         
     */

    @Column(name = "single_resource", nullable = false, length = 1)
    public boolean isSingleResource() {
        return this.singleResource;
    }

    public void setSingleResource(boolean singleResource) {
        this.singleResource = singleResource;
    }

    /**       
     *      *            @hibernate.property
     *             column="resource_not_found_exception"
     *             length="2147483647"
     *         
     */

    @Column(name = "resource_not_found_exception", length = 2147483647)
    public String getResourceNotFoundException() {
        return this.resourceNotFoundException;
    }

    public void setResourceNotFoundException(String resourceNotFoundException) {
        this.resourceNotFoundException = resourceNotFoundException;
    }

    /**       
     *      *            @hibernate.set
     *             lazy="false"
     *             inverse="false"
     *             cascade="all,delete-orphan"
     *            @hibernate.collection-key
     *             column="method_mapping"
     *            @hibernate.collection-one-to-many
     *             class="de.escidoc.core.common.util.security.persistence.InvocationMapping"
     *         
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "method_mapping", updatable = false)
    @Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<InvocationMapping> getInvocationMappings() {
        return this.invocationMappings;
    }

    public void setInvocationMappings(Set<InvocationMapping> invocationMappings) {
        this.invocationMappings = invocationMappings;
    }

}
