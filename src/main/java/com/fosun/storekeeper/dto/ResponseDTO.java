/*
 * ResponseDTO.java
 * 2019年8月15日 下午4:52:44
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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 基础ResponseDTO
 * @version   
 * @author hengjb 2019年8月15日下午4:52:44
 * @since 1.8
 */
public class ResponseDTO extends BaseDTO {
    private static final long serialVersionUID = -3058650155126166725L;

    private String resultCode;

    private String errMsg;

    public ResponseDTO() {
    }

    public ResponseDTO(String resultCode, String errMsg) {
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
