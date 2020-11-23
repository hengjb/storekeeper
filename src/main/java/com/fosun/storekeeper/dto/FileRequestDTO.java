package com.fosun.storekeeper.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 文件请求信息
 * 
 * @author hengjb
 * @date 2019/08/26
 */
public class FileRequestDTO extends RequestDTO {
	private static final long serialVersionUID = 6760719973133408462L;

	private String bizGroup;

	private String bizSub;

	private String encodeName;

	@NotNull
	private String sysName;

	private String docType;

	private String originName;

	private String description;

	private List<UploadDTO> uploadDtos;

	private String randomCode;

	/**
	 * 是否使用别名
	 */
	private String useAlias;

	public FileRequestDTO() {
	}

	public FileRequestDTO(String username, String apiToken, String requestCode, String invokerName) {
		super(username, apiToken, requestCode, invokerName);
	}

	public String getBizGroup() {
		return this.bizGroup;
	}

	public String getBizSub() {
		return this.bizSub;
	}

	public String getEncodeName() {
		return this.encodeName;
	}

	public String getSysName() {
		return this.sysName;
	}

	public void setBizGroup(String bizGroup) {
		this.bizGroup = bizGroup;
	}

	public void setBizSub(String bizSub) {
		this.bizSub = bizSub;
	}

	public void setEncodeName(String encodeName) {
		this.encodeName = encodeName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getDocType() {
		return this.docType;
	}

	public String getOriginName() {
		return this.originName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRandomCode() {
		return randomCode;
	}

	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}

	public List<UploadDTO> getUploadDtos() {
		return uploadDtos;
	}

	public void setUploadDtos(List<UploadDTO> uploadDtos) {
		this.uploadDtos = uploadDtos;
	}

	public String getUseAlias() {
		return useAlias;
	}

	public void setUseAlias(String useAlias) {
		this.useAlias = useAlias;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
