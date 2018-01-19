package com.cherrypicks.tcc.cms.dto;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 4817236725364974536L;

    protected Long id;

    protected Date createdTime;

    protected String createdBy;

    protected Date updatedTime;

    protected String updatedBy;

    protected Boolean isDeleted = false;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        if (this.createdTime == null) {
            return null;
        } else {
            return (Date) createdTime.clone();
        }
    }

    public void setCreatedTime(final Date createdTime) {
        if (createdTime == null) {
            this.createdTime = null;
        } else {
            this.createdTime = (Date) createdTime.clone();
        }
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        if (this.updatedTime == null) {
            return null;
        } else {
            return (Date) updatedTime.clone();
        }
    }

    public void setUpdatedTime(final Date updatedTime) {
        if (updatedTime == null) {
            this.updatedTime = null;
        } else {
            this.updatedTime = (Date) updatedTime.clone();
        }
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
