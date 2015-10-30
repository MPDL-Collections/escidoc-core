package de.escidoc.core.common.util.security.persistence;

// Generated 26.10.2015 15:26:24 by Hibernate Tools 3.2.2.GA

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
 *         table="invocation_mappings"
 *        @hibernate.mapping
 *         schema="aa"
 *     
 */
@MappedSuperclass
public class InvocationMappingBase implements java.io.Serializable {

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
     *             column="attribute_id"
     *             length="2147483647"
     *             not-null="true"
     *         
     */
    private String attributeId;

    /**
     *            @hibernate.property
     *             column="path"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String path;

    /**
     *            @hibernate.property
     *             column="position"
     *             length="2"
     *             not-null="true"
     *         
     */
    private int position;

    /**
     *            @hibernate.property
     *             column="attribute_type"
     *             length="255"
     *             not-null="true"
     *         
     */
    private String attributeType;

    /**
     *            @hibernate.property
     *             column="mapping_type"
     *             length="2"
     *             not-null="true"
     *         
     */
    private int mappingType;

    /**
     *            @hibernate.property
     *             column="multi_value"
     *             length="1"
     *             not-null="true"
     *         
     */
    private boolean multiValue;

    /**
     *            @hibernate.property
     *             column="value"
     *             length="100"
     *         
     */
    private String value;

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="method_mapping"         
     *         
     */
    private MethodMapping methodMapping;

    public InvocationMappingBase() {
    }

    public InvocationMappingBase(String attributeId, String path, int position, String attributeType, int mappingType,
        boolean multiValue) {
        this.attributeId = attributeId;
        this.path = path;
        this.position = position;
        this.attributeType = attributeType;
        this.mappingType = mappingType;
        this.multiValue = multiValue;
    }

    public InvocationMappingBase(String attributeId, String path, int position, String attributeType, int mappingType,
        boolean multiValue, String value, MethodMapping methodMapping) {
        this.attributeId = attributeId;
        this.path = path;
        this.position = position;
        this.attributeType = attributeType;
        this.mappingType = mappingType;
        this.multiValue = multiValue;
        this.value = value;
        this.methodMapping = methodMapping;
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
     *             column="attribute_id"
     *             length="2147483647"
     *             not-null="true"
     *         
     */

    @Column(name = "attribute_id", nullable = false, length = 2147483647)
    public String getAttributeId() {
        return this.attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    /**       
     *      *            @hibernate.property
     *             column="path"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "path", nullable = false)
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**       
     *      *            @hibernate.property
     *             column="position"
     *             length="2"
     *             not-null="true"
     *         
     */

    @Column(name = "position", nullable = false, length = 2)
    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**       
     *      *            @hibernate.property
     *             column="attribute_type"
     *             length="255"
     *             not-null="true"
     *         
     */

    @Column(name = "attribute_type", nullable = false)
    public String getAttributeType() {
        return this.attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    /**       
     *      *            @hibernate.property
     *             column="mapping_type"
     *             length="2"
     *             not-null="true"
     *         
     */

    @Column(name = "mapping_type", nullable = false, length = 2)
    public int getMappingType() {
        return this.mappingType;
    }

    public void setMappingType(int mappingType) {
        this.mappingType = mappingType;
    }

    /**       
     *      *            @hibernate.property
     *             column="multi_value"
     *             length="1"
     *             not-null="true"
     *         
     */

    @Column(name = "multi_value", nullable = false, length = 1)
    public boolean isMultiValue() {
        return this.multiValue;
    }

    public void setMultiValue(boolean multiValue) {
        this.multiValue = multiValue;
    }

    /**       
     *      *            @hibernate.property
     *             column="value"
     *             length="100"
     *         
     */

    @Column(name = "value", length = 100)
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**       
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="method_mapping"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "method_mapping")
    public MethodMapping getMethodMapping() {
        return this.methodMapping;
    }

    public void setMethodMapping(MethodMapping methodMapping) {
        this.methodMapping = methodMapping;
    }

}
