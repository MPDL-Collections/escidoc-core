/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at license/ESCIDOC.LICENSE
 * or http://www.escidoc.de/license.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at license/ESCIDOC.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright 2008 Fachinformationszentrum Karlsruhe Gesellschaft
 * fuer wissenschaftlich-technische Information mbH and Max-Planck-
 * Gesellschaft zur Foerderung der Wissenschaft e.V.
 * All rights reserved.  Use is subject to license terms.
 */
package de.escidoc.core.common.business.fedora.resources.create;

import de.escidoc.core.common.business.fedora.resources.LockStatus;
import de.escidoc.core.common.business.fedora.resources.StatusType;
import de.escidoc.core.common.exceptions.application.invalid.InvalidStatusException;
import de.escidoc.core.common.exceptions.system.WebserverSystemException;
import de.escidoc.core.common.util.service.UserContext;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Properties for Content Relation.<br/>
 *
 * @author SWA
 * 
 */
public class ContentRelationProperties implements Serializable {

    private static final long serialVersionUID = -5610276928827889108L;

    private StatusType status = StatusType.PENDING;

    private String statusComment = "Object created.";

    private String pid = null;

    private String title = "Content Relation";

    private String createdById = null;

    private String createdByName = null;

    private String modifiedById = null;

    private String modifiedByName = null;

    private DateTime lastModificationDate = null;

    private DateTime creationDate = null;

    private DateTime versionDate = null;

    private LockStatus lockStatus = LockStatus.UNLOCKED;

    private DateTime lockDate = null;

    private String lockOwnerId = null;

    private String lockOwnerName = null;

    private String description = null;


    /**
     * ContentRelationProperties.
     * 
     * @throws WebserverSystemException
     *             Thrown if obtaining UserContext failed.
     */
    public ContentRelationProperties() throws WebserverSystemException {

        // setting up some default values
        setCreatedById(UserContext.getId());
        setCreatedByName(UserContext.getRealName());
        setModifiedById(UserContext.getId());
        setModifiedByName(UserContext.getRealName());
    }

    /**
     * Set status of object.
     * 
     * @param status
     *            the status to set
     */
    public final void setStatus(final StatusType status) {
        this.status = status;
    }

    /**
     * Get Status of object.
     * 
     * @return the status
     */
    public final StatusType getStatus() {
        return status;
    }

    /**
     * @param statusComment
     *            the statusComment to set
     */
    public final void setStatusComment(final String statusComment) {
        this.statusComment = statusComment;
    }

    /**
     * @return the statusComment
     */
    public final String getStatusComment() {
        return statusComment;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public final void setPid(final String pid) {
        this.pid = pid;
    }

    /**
     * @return the pid
     */
    public final String getPid() {
        return pid;
    }

    /**
     * @param title
     *            the title to set
     */
    public final void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return the title
     */
    public final String getTitle() {
        return this.title;
    }

    /**
     * Set Id of Creator.
     * 
     * @param createdById
     *            the creator id
     */
    public final void setCreatedById(final String createdById) {
        this.createdById = createdById;
    }

    /**
     * Get Id of creator.
     * 
     * @return the creator id
     */
    public final String getCreatedById() {
        return createdById;
    }

    /**
     * Set id of modifier of this version.
     * 
     * @param modifiedById
     *            the modifiedById to set
     */
    public final void setModifiedById(final String modifiedById) {
        this.modifiedById = modifiedById;
    }

    /**
     * Get id of modifier of this version.
     * 
     * @return the modifiedById
     */
    public final String getModifiedById() {
        return modifiedById;
    }

    /**
     * @param createdByName
     *            the createdByName to set
     */
    public final void setCreatedByName(final String createdByName) {
        this.createdByName = createdByName;
    }

    /**
     * @return the createdByName
     */
    public final String getCreatedByName() {
        return createdByName;
    }

    /**
     * @param modifiedByName
     *            the modifiedByName to set
     */
    public final void setModifiedByName(final String modifiedByName) {
        this.modifiedByName = modifiedByName;
    }

    /**
     * @return the modifiedByName
     */
    public final String getModifiedByName() {
        return modifiedByName;
    }

    /**
     * 
     * @param lastModificationDate
     *            Last-modification-date of ContentRelation
     */
    final void setLastModificationDate(final DateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    /**
     * 
     * @param lastModificationDate
     *            Last-modification-date of ContentRelation
     */
    public final void setLastModificationDate(final String lastModificationDate) {
        setLastModificationDate(new DateTime(lastModificationDate));
    }

    /**
     * 
     * @return Last-modification-date of ContentRelation
     */
    public final DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    /**
     * 
     * @param creationDate
     *            Creation date of ContentRelation
     */
    final void setCreationDate(final DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * 
     * @param creationDate
     *            Creation date of ContentRelation
     */
    public final void setCreationDate(final String creationDate) {
        setCreationDate(new DateTime(creationDate));
    }

    /**
     * Get creation date.
     * 
     * @return timestamp of create
     */
    public final DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Set status of lock.
     * 
     * @param lockStatus
     *            [locked|unlocked]
     * @throws InvalidStatusException
     *             Thrown if status in invalid
     */
    public final void setLockStatus(final LockStatus lockStatus)
        throws InvalidStatusException {

        this.lockStatus = lockStatus;
    }

    /**
     * Set status of lock.
     * 
     * @param lockStatus
     *            [locked|unlocked]
     * @throws InvalidStatusException
     *             Thrown if status in invalid
     */
    public void setLockStatus(final String lockStatus)
        throws InvalidStatusException {
        this.lockStatus = LockStatus.getStatusType(lockStatus);
    }

    /**
     * Get lock status.
     * 
     * @return status of lock ([locked|unlocked]
     */
    public final LockStatus getLockStatus() {
        return lockStatus;
    }

    /**
     * Set timestamp of lock.
     * 
     * @param lockDate
     *            timestamp of lock.
     */
    public final void setLockDate(final DateTime lockDate) {
        this.lockDate = lockDate;
    }

    /**
     * Set timestamp of lock.
     * 
     * @param lockDate
     *            timestamp of lock.
     */
    public final void setLockDate(final String lockDate) {
        setLockDate(new DateTime(lockDate));
    }

    /**
     * Get timestamp of lock.
     * 
     * @return timestamp of lock.
     */
    public final DateTime getLockDate() {
        return lockDate;
    }

    /**
     * Set lock owner.
     * 
     * @param lockOwnerId
     *            Lock owner
     */
    public final void setLockOwnerId(final String lockOwnerId) {
        this.lockOwnerId = lockOwnerId;
    }

    /**
     * Get lock owner.
     * 
     * @return Owner of lock
     */
    public final String getLockOwnerId() {
        return lockOwnerId;
    }

    /**
     * Set Name (title) of lock owner.
     * 
     * @param lockOwnerName
     *            Name (title) of lock owner
     */
    public final void setLockOwnerName(final String lockOwnerName) {
        this.lockOwnerName = lockOwnerName;
    }

    /**
     * Get Name (title) of lock owner.
     * 
     * @return Name (title) of lock owner
     */
    public final String getLockOwnerName() {
        return lockOwnerName;
    }

    /**
     * Is Content Relation locked?
     * 
     * @return true if locked, false otherwise.
     */
    public final boolean isLocked() {

        return this.lockStatus == LockStatus.LOCKED;
    }

    /**
     * Lock the ContentRelation.
     * 
     * @param ownerId
     *            objid of the lock owner
     */
    public void lock(final String ownerId) {

        this.lockStatus = LockStatus.LOCKED;
        this.lockOwnerId = ownerId;
        this.lockDate = this.lastModificationDate;
    }

    /**
     * Set timestamp of resource/version.
     * 
     * @param versionDate
     *            timestamp of resource
     */
    public void setVersionDate(final DateTime versionDate) {
        this.versionDate = versionDate;
    }

    /**
     * Set timestamp of resource/version.
     * 
     * @return timestamp of resource
     */
    public final DateTime getVersionDate() {
        return versionDate;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * 
     * @param obj
     *            the reference object with which to compare.
     * 
     * @return true if this object is the same as the obj argument; false
     *         otherwise.
     */
    public final boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Set description.
     * 
     * @param description
     *            Description of ContentRelation
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get description.
     * 
     * @return Description of ContentRelation
     */
    public final String getDescription() {
        return description;
    }


}
