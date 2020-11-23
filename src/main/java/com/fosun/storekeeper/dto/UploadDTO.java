package com.fosun.storekeeper.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 上传文件详细信息
 * @author hengjb
 * @date 2019/08/26
 */
public class UploadDTO extends BaseDTO {
    private static final long serialVersionUID = -7086502758296822986L;

    private String bizGroup;

    private String bizSub;

    private String docType;

    private String originName;

    private String sysName;

    private String description;

    public String getBizGroup() {
        return this.bizGroup;
    }

    public String getBizSub() {
        return this.bizSub;
    }

    public String getDocType() {
        return this.docType;
    }

    public String getOriginName() {
        return this.originName;
    }

    public String getSysName() {
        return this.sysName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setBizGroup(String bizGroup) {
        this.bizGroup = bizGroup;
    }

    public void setBizSub(String bizSub) {
        this.bizSub = bizSub;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
