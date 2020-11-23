/*
 * JsonResultEnum.java
 * 2019年8月16日 下午4:22:08
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
 * Json返回对象类型枚举
 * @version   
 * @author hengjb 2019年8月16日下午4:22:08
 * @since 1.8
 */
public enum JsonResultEnum {

    /**
     * 成功
     */
    SUCCESS("success", "成功"),
    /**
     * 失败
     */
    FAILURE("failure", "失败");

    /**
     * code
     */
    String code;

    /**
     * message
     */
    String message;
    
    JsonResultEnum(String code, String message) {
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
    public static JsonResultEnum getResultInfoEnumByCode(String code) {
        for (JsonResultEnum resEnum : JsonResultEnum.values()) {
            if (code.equals(resEnum.getCode())) {
                return resEnum;
            }
        }
        return null;
    }
}

