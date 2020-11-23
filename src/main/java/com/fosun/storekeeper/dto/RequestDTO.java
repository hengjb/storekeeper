/*
 * RequestDTO.java
 * 2019年8月15日 下午4:47:33
 * Copyright 2017 Fosun Financial. All  Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit         
 * www.fosun.com 
 * if you need additional information or have any questions.
 * @author hengjb
 * @version 1.0
 */

package com.fosun.storekeeper.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 基础RequestDTO
 * @version   
 * @author hengjb 2019年8月15日下午4:47:33
 * @since 1.8
 */
public class RequestDTO extends BaseDTO {
    private static final long serialVersionUID = -1992900554693208027L;

    private String username;

    private String name;

    private String identity;

    private String apiToken;

    private String supplier;

    private String key;

    private String data;

    private String companyKey;

    private String companyCode;

    private String companyMethod;

    @NotNull
    private String requestCode;

    @NotNull
    private String invokerName;

    private String bizUk;

    private String purpose;

    private String systemName;

    public RequestDTO() {
    }

    public RequestDTO(String username, String apiToken, String requestCode, String invokerName) {
        this.username = username;
        this.apiToken = apiToken;
        this.requestCode = requestCode;
        this.invokerName = invokerName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getBizUk() {
        return this.bizUk;
    }

    public void setBizUk(String bizUk) {
        this.bizUk = bizUk;
    }

    public String getRequestCode() {
        return this.requestCode;
    }

    public String getInvokerName() {
        return this.invokerName;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public void setInvokerName(String invokerName) {
        this.invokerName = invokerName;
    }

    public String getApiToken() {
        return this.apiToken;
    }

    public RequestDTO(String requestCode) {
        this.requestCode = requestCode;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyKey() {
        return this.companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyMethod() {
        return this.companyMethod;
    }

    public void setCompanyMethod(String companyMethod) {
        this.companyMethod = companyMethod;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
