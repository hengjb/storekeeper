/*
 * ResponseCodeEnum.java
 * 2017年7月19日 下午1:44:44
 * Copyright 2017 Fosun Financial. All  Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit         
 * www.fosun.com 
 * if you need additional information or have any questions.
 * @author hengjb
 * @version 1.0
 */

package com.fosun.storekeeper.common;

/**
 * 返回信息一览表
 * @version   
 * @author hengjb 2017年7月19日下午1:44:44
 * @since 1.8
 */
public enum ResponseCodeEnum {

    /**
     * 成功
     */
    SUCCESS("000000", "成功"),
    /**
     * 系统异常
     */
    SYS_ERROR("999999", "系统异常");

    /**
     * code
     */
    String code;

    /**
     * message
     */
    String message;

    ResponseCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据枚举code获得对应value 
     * @param code
     * @return
     * @Description: 
     * @create date 2017年8月23日上午10:30:17
     */
    public static ResponseCodeEnum getResultInfoEnumByCode(String code) {
        for (ResponseCodeEnum resEnum : ResponseCodeEnum.values()) {
            if (code.equals(resEnum.getCode())) {
                return resEnum;
            }
        }
        return null;
    }
}
