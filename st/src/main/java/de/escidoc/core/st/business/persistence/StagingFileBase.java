package de.escidoc.core.st.business.persistence;

// Generated 26.10.2015 15:34:52 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 			@hibernate.class
 * 			table="staging_file"
 * 			@hibernate.mapping
 * 			schema="st"
 * 	    
 */
@MappedSuperclass
public class StagingFileBase implements java.io.Serializable {

    /**
     * 				@hibernate.id
     * 				generator-class="de.escidoc.core.st.business.persistence.hibernate.TokenGenerator"
     * 				type="java.lang.String"
     * 				column="token"
     *     	    
     */
    private String token;

    /**
     * 				@hibernate.property
     * 				column="expiry_ts"
     * 				length="19"
     * 				not-null="true"
     *         	
     */
    private long expiryTs;

    /**
     * 				@hibernate.property
     * 				column="reference"
     * 				length="2147483647"
     *         	
     */
    private String reference;

    /**
     * 				@hibernate.property
     * 				column="mime_type"
     * 				length="255"
     *         	
     */
    private String mimeType;

    /**
     * 				@hibernate.property
     * 				column="upload"
     * 				length="1"
     * 				not-null="true"
     *         	
     */
    private boolean upload;

    public StagingFileBase() {
    }

    public StagingFileBase(long expiryTs, boolean upload) {
        this.expiryTs = expiryTs;
        this.upload = upload;
    }

    public StagingFileBase(long expiryTs, String reference, String mimeType, boolean upload) {
        this.expiryTs = expiryTs;
        this.reference = reference;
        this.mimeType = mimeType;
        this.upload = upload;
    }

    /**       
     *      * 				@hibernate.id
     * 				generator-class="de.escidoc.core.st.business.persistence.hibernate.TokenGenerator"
     * 				type="java.lang.String"
     * 				column="token"
     *     	    
     */
    @GenericGenerator(name = "generator", strategy = "de.escidoc.core.st.business.persistence.hibernate.TokenGenerator", parameters = @Parameter(name = "schema", value = "st"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "token", nullable = false)
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**       
     *      * 				@hibernate.property
     * 				column="expiry_ts"
     * 				length="19"
     * 				not-null="true"
     *         	
     */

    @Column(name = "expiry_ts", nullable = false, length = 19)
    public long getExpiryTs() {
        return this.expiryTs;
    }

    public void setExpiryTs(long expiryTs) {
        this.expiryTs = expiryTs;
    }

    /**       
     *      * 				@hibernate.property
     * 				column="reference"
     * 				length="2147483647"
     *         	
     */

    @Column(name = "reference", length = 2147483647)
    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    /**       
     *      * 				@hibernate.property
     * 				column="mime_type"
     * 				length="255"
     *         	
     */

    @Column(name = "mime_type")
    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**       
     *      * 				@hibernate.property
     * 				column="upload"
     * 				length="1"
     * 				not-null="true"
     *         	
     */

    @Column(name = "upload", nullable = false, length = 1)
    public boolean isUpload() {
        return this.upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

}
