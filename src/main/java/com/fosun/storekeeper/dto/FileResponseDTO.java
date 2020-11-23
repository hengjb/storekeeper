package com.fosun.storekeeper.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 文件返回信息
 * @author hengjb
 * @date 2019/08/26
 */
public class FileResponseDTO extends ResponseDTO {
    private static final long serialVersionUID = 8132958161164846671L;

    public FileResponseDTO() {
    }

    public FileResponseDTO(String resultCode, String errMsg) {
        super(resultCode, errMsg);
    }

    private String uploadStatus = "failure";

    private String bizGroup;

    private String bizSub;

    private String docType;

    private String originName;

    private String encodeName;

    private String sysName;

    private String filePath;

    private String description;

    private String realPath;

    private String errorDetail;

    private List<DownloadDTO> downloadDtos;

    public String getUploadStatus() {
        return this.uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

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

    public String getRealPath() {
        return this.realPath;
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

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
    
    public List<DownloadDTO> getDownloadDtos() {
        return downloadDtos;
    }

    public void setDownloadDtos(List<DownloadDTO> downloadDtos) {
        this.downloadDtos = downloadDtos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
