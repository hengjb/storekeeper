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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


/**
 * SkUpload实体类
 * @author hengjb
 * @date 2019/08/26
 */
public class SkUpload extends BaseEntity {

    private static final long serialVersionUID = -237751924379154241L;

    /** id - 主键 */
    @NotNull(message=" id 不能为null")
    private Integer id;

    /** code - 原因编码 */
    @NotEmpty(message=" code 不能为空")
    @Length(max=50, message=" code 的长度不能超过 50 ")
    private String code;

    /** doc_type - 文件类型 */
    @NotEmpty(message=" docType 不能为空")
    @Length(max=50, message=" docType 的长度不能超过 50 ")
    private String docType;

    /** origin_name - 文件原名 */
    @NotEmpty(message=" originName 不能为空")
    @Length(max=500, message=" originName 的长度不能超过 500 ")
    private String originName;

    /** encode_name - 加密后文件名 */
    @NotEmpty(message=" encodeName 不能为空")
    @Length(max=500, message=" encodeName 的长度不能超过 500 ")
    private String encodeName;

    /** sys_name - 来源业务系统名称 */
    @NotEmpty(message=" sysName 不能为空")
    @Length(max=200, message=" sysName 的长度不能超过 200 ")
    private String sysName;

    /** file_group -  */
    @NotEmpty(message=" fileGroup 不能为空")
    @Length(max=100, message=" fileGroup 的长度不能超过 100 ")
    private String fileGroup;

    /** file_path - 文件路径 */
    @NotEmpty(message=" filePath 不能为空")
    @Length(max=500, message=" filePath 的长度不能超过 500 ")
    private String filePath;

    /** description - 文件描述 */
    @Length(max=500, message=" description 的长度不能超过 500 ")
    private String description;

    /** memo - 备注信息 */
    @Length(max=500, message=" memo 的长度不能超过 500 ")
    private String memo;

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getDocType() {
        return this.docType;
    }
    
    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getOriginName() {
        return this.originName;
    }
    
    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getEncodeName() {
        return this.encodeName;
    }
    
    public void setEncodeName(String encodeName) {
        this.encodeName = encodeName;
    }

    public String getSysName() {
        return this.sysName;
    }
    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getFileGroup() {
        return this.fileGroup;
    }
    
    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }

    public String getFilePath() {
        return this.filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

}