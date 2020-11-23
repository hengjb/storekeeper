package com.fosun.storekeeper.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 下载文件详细信息
 * @author hengjb
 * @date 2019/08/26
 */
public class DownloadDTO extends BaseDTO {
    private static final long serialVersionUID = -1374327829778392389L;

    private String bizGroup;

    private String bizSub;

    private String docType;

    private String originName;

    private String encodeName;

    private String sysName;

    private String filePath;

    private String description;

    private String realPath;

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

    public String getEncodeName() {
        return this.encodeName;
    }

    public String getSysName() {
        return this.sysName;
    }

    public String getFilePath() {
        return this.filePath;
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

    public void setEncodeName(String encodeName) {
        this.encodeName = encodeName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
