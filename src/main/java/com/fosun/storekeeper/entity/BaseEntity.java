/*
 * BaseEntity.java
 * 2019年8月16日 上午10:13:43
 * Copyright 2017 Fosun Financial. All  Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit         
 * www.fosun.com 
 * if you need additional information or have any questions.
 * @author hengjb
 * @version 1.0
 */

package com.fosun.storekeeper.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fosun.storekeeper.util.Clock;

/**
 * 基础entity
 * @version   
 * @author hengjb 2019年8月16日上午10:13:43
 * @since 1.8
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 8968972584033203030L;

    private static final Clock CLOCK;

    static {
        CLOCK = Clock.DEFAULT;
    }

    /** version - 乐观锁 */
    @Length(max = 50, message = " version 的长度不能超过 50 ")
    protected String version = "0";

    /** flag - 逻辑删除标志，0：未删除，1：已删除 */
    @NotEmpty(message = " flag 不能为空")
    @Length(max = 1, message = " flag 的长度不能超过 1 ")
    protected String flag = "0";

    /** created_by - 创建者 */
    @Length(max = 50, message = " createdBy 的长度不能超过 50 ")
    @CreatedBy
    protected String createdBy = "admin";

    /** created_dt - 创建时间 */
    @NotNull(message = " createdDt 不能为null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    protected Date createdDt;

    /** updated_by - 修改者 */
    @Length(max = 50, message = " updatedBy 的长度不能超过 50 ")
    @LastModifiedBy
    protected String updatedBy = "admin";

    /** updated_dt - 修改时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    protected Date updatedDt;

    public BaseEntity() {
        this.createdDt = CLOCK.getCurrentDate();
        this.updatedDt = CLOCK.getCurrentDate();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
