/*
 * 2019-8-13 14:05:38
 * Copyright 2017 Fosun Financial. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit 
 * www.fosun.com 
 * if you need additional information or have any questions.
 * @author hengjb
 * @version 1.0
 */
package com.fosun.storekeeper.entity; 

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


/**
 * SkDict实体类
 * @author hengjb
 * @date 2019/08/26
 */
public class SkDict extends BaseEntity {

    private static final long serialVersionUID = 8907058633014710070L;

    /** ID - 主键 */
    @NotNull(message=" id 不能为null")
    private Integer id;

    /** KEY1 - 定义key1值 */
    @Length(max=1000, message=" key1 的长度不能超过 1000 ")
    private String key1;

    /** VALUE1 - 定义value1值 */
    @Length(max=1000, message=" value1 的长度不能超过 1000 ")
    private String value1;

    /** KEY2 - 定义key2值 */
    @Length(max=1000, message=" key2 的长度不能超过 1000 ")
    private String key2;

    /** VALUE2 - 定义value2值 */
    @Length(max=1000, message=" value2 的长度不能超过 1000 ")
    private String value2;

    /** CATEGORY - 字典分类识别码 */
    @NotEmpty(message=" category 不能为空")
    @Length(max=1000, message=" category 的长度不能超过 1000 ")
    private String category;

    /** START_DT - 开始日期 */
    @NotNull(message=" startDt 不能为null")
    private Date startDt;

    /** END_DT - 结束日期 */
    @NotNull(message=" endDt 不能为null")
    private Date endDt;

    /** MEMO - 备注信息 */
    @Length(max=500, message=" memo 的长度不能超过 500 ")
    private String memo;

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey1() {
        return this.key1;
    }
    
    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getValue1() {
        return this.value1;
    }
    
    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getKey2() {
        return this.key2;
    }
    
    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getValue2() {
        return this.value2;
    }
    
    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStartDt() {
        return this.startDt;
    }
    
    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return this.endDt;
    }
    
    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

}