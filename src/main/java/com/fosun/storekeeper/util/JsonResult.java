/*
 * JsonResult.java
 * 2019年8月16日 下午4:03:54
 * Copyright 2017 Fosun Financial. All  Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit         
 * www.fosun.com 
 * if you need additional information or have any questions.
 * @author hengjb
 * @version 1.0
 */

package com.fosun.storekeeper.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Json返回对象类
 * @version   
 * @author hengjb 2019年8月16日下午4:03:54
 * @since 1.8
 */
public class JsonResult<T> {
    private String type;

    private String message;

    private Long id;

    private String action;

    private T data;

    public JsonResult() {
    }

    public JsonResult(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public JsonResult(String type, String message, T data) {
        this.type = type;
        this.message = message;
        this.data = data;
    }

    public JsonResult(String type, Long sid, String action, String message) {
        this.type = type;
        this.id = sid;
        this.action = action;
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
