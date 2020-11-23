/*
 * SKServiceImpl.java 2019年8月15日 下午5:02:59 Copyright 2017 Fosun Financial. All Rights Reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit www.fosun.com if you need additional information or have any questions.
 * 
 * @author hengjb
 * 
 * @version 1.0
 */

package com.fosun.storekeeper.service.impl;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fosun.storekeeper.common.Constants;
import com.fosun.storekeeper.common.JsonResultEnum;
import com.fosun.storekeeper.dto.FileRequestDTO;
import com.fosun.storekeeper.dto.FileResponseDTO;
import com.fosun.storekeeper.entity.SkUpload;
import com.fosun.storekeeper.exception.BizException;
import com.fosun.storekeeper.exception.StoreKeeperServiceException;
import com.fosun.storekeeper.service.SkService;
import com.fosun.storekeeper.service.SkUploadService;
import com.fosun.storekeeper.util.AliasUtils;
import com.fosun.storekeeper.util.Encodes;
import com.fosun.storekeeper.util.FastDFSFile;
import com.fosun.storekeeper.util.FileManager;

/**
 * 文件处理主服务
 * 
 * @version
 * @author hengjb 2019年8月15日下午5:02:59
 * @since 1.8
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class SkServiceImpl implements SkService {

	private static Logger logger = LoggerFactory.getLogger(SkServiceImpl.class);

	@Value("${file_server_url}")
	private String baseFileUrl;

	/**
	 * 别名地址
	 */
	@Value("${alias_server_url:''}")
	private String aliasFileUrl;

	@Autowired
	private AliasUtils aliasUtils;

	@Autowired
	SkUploadService skUploadService;

	@Value("${storekeeper.env}")
	public String configl;

	@Value("${storekeeper.conf}")
	public String confl;

	public static String CONFIG;
	public static String CONF;

	@PostConstruct
	private void getConfig() {
		CONFIG = this.configl;
		CONF = this.confl;
	}

	/**
	 * the "."
	 */
	private final String point = ".";

	@Override
	public FileResponseDTO upload(FileRequestDTO requetsDTO, MultipartFile file)
			throws IOException, MyException, BizException {
		FileResponseDTO responseDTO = new FileResponseDTO();
		if (file.isEmpty()) {
			logger.error("file original name:" + file.getOriginalFilename() + "  is empty.");
			throw new StoreKeeperServiceException("file original name:" + file.getOriginalFilename() + "  is empty.");
		}
		if (StringUtils.isBlank(requetsDTO.getBizSub())) {
			// 如果BizSub为空，则值从fileName中获取
			requetsDTO.setBizSub(file.getOriginalFilename());
		}
		/*
		 * if (requetsDTO.getBizSub() == null || "".equals(requetsDTO.getBizSub())) {
		 * //如果BizSub为空，则值从fileName中获取 requetsDTO.setBizSub(file.getOriginalFilename());
		 * }
		 */
		if (requetsDTO.getBizSub() != null && requetsDTO.getBizSub().indexOf(point) > 0) {
			requetsDTO.setBizSub(requetsDTO.getBizSub().substring(0, requetsDTO.getBizSub().lastIndexOf(point)));
		}
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(point) + 1);
		// 文件流传输文件名称会出现乱码情况，originName从requetsDTO中获取
		String originName = requetsDTO.getBizSub();

		FastDFSFile dfsFile = new FastDFSFile(file.getBytes(), originName, ext);
		NameValuePair[] metaList = new NameValuePair[4];
		metaList[0] = new NameValuePair("fileName", originName);
		metaList[1] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
		metaList[2] = new NameValuePair("fileExt", ext);
		metaList[3] = new NameValuePair("randomCode", requetsDTO.getRandomCode());
		String[] filePath = FileManager.upload(dfsFile, metaList);

		SkUpload tSkUpload = new SkUpload();
		BeanUtils.copyProperties(requetsDTO, tSkUpload);
		tSkUpload.setCode(UUID.randomUUID().toString());
		tSkUpload.setDocType(getSuffix(file));
		tSkUpload.setOriginName(originName);
		tSkUpload.setSysName(requetsDTO.getSysName());
		tSkUpload.setFileGroup(filePath[0]);
		tSkUpload.setFilePath(filePath[1]);
		tSkUpload.setEncodeName(encodeBase64(filePath[0] + "/" + filePath[1]));
		skUploadService.addSkUpload(tSkUpload);
		/*
		 * try { skUploadService.addSkUpload(tSkUpload); } catch (BizException e) {
		 * logger.error("upload file service find the db error", e); //throw new
		 * StoreKeeperServiceException("文件上传异常"); }
		 */
		responseDTO.setUploadStatus(JsonResultEnum.SUCCESS.getCode());
		responseDTO.setEncodeName(tSkUpload.getEncodeName());
		// 使用别名逻辑
		String baseTargetUrl = aliasUtils.convertUrlByAlias(requetsDTO.getUseAlias());
		// responseDTO.setRealPath(baseFileUrl + filePath[0] + "/" + filePath[1]);
		responseDTO.setRealPath(baseTargetUrl + filePath[0] + "/" + filePath[1]);
		logger.info("success to persistent table:t_sk_upload for file name:" + tSkUpload.getOriginName()
				+ " EncodeName：" + tSkUpload.getEncodeName() + " file path:" + tSkUpload.getFilePath());
		return responseDTO;
	}

	@Override
	public FileResponseDTO compressUpload(FileRequestDTO requetsDTO, String originalFilename, byte[] contents)
			throws IOException, MyException, BizException {
		FileResponseDTO responseDTO = new FileResponseDTO();
		if (StringUtils.isBlank(requetsDTO.getBizSub())) {
			// 如果BizSub为空，则值从fileName中获取
			requetsDTO.setBizSub(originalFilename);
		}
		/*
		 * if (requetsDTO.getBizSub() == null || "".equals(requetsDTO.getBizSub())) {
		 * //如果文件名称为空，从file中获取 requetsDTO.setBizSub(originalFilename); }
		 */
		if (requetsDTO.getBizSub() != null && requetsDTO.getBizSub().indexOf(point) > 0) {
			requetsDTO.setBizSub(requetsDTO.getBizSub().substring(0, requetsDTO.getBizSub().lastIndexOf(point)));
		}
		String ext = originalFilename.substring(originalFilename.lastIndexOf(point) + 1);
		// 文件流传输文件名称会出现乱码情况，所以该值从requetsDTO中获取
		String originName = requetsDTO.getBizSub();
		FastDFSFile dfsFile = new FastDFSFile(contents, originName, ext);
		NameValuePair[] metaList = new NameValuePair[4];
		metaList[0] = new NameValuePair("fileName", originName);
		metaList[1] = new NameValuePair("fileLength", String.valueOf(contents.length));
		metaList[2] = new NameValuePair("fileExt", ext);
		metaList[3] = new NameValuePair("randomCode", requetsDTO.getRandomCode());
		String[] filePath = FileManager.upload(dfsFile, metaList);

		SkUpload tSkUpload = new SkUpload();
		BeanUtils.copyProperties(requetsDTO, tSkUpload);
		tSkUpload.setCode(UUID.randomUUID().toString());
		tSkUpload.setDocType(originalFilename.substring(originalFilename.lastIndexOf(point)));
		tSkUpload.setOriginName(originName);
		tSkUpload.setSysName(requetsDTO.getSysName());
		tSkUpload.setFileGroup(filePath[0]);
		tSkUpload.setFilePath(filePath[1]);
		tSkUpload.setEncodeName(encodeBase64(filePath[0] + "/" + filePath[1]));
		skUploadService.addSkUpload(tSkUpload);
		/*
		 * try { skUploadService.addSkUpload(tSkUpload); } catch (BizException e) {
		 * logger.error("upload compress pic error", e); }
		 */
		responseDTO.setUploadStatus(JsonResultEnum.SUCCESS.getCode());
		responseDTO.setEncodeName(tSkUpload.getEncodeName());
		// 使用别名逻辑
		String baseTargetUrl = aliasUtils.convertUrlByAlias(requetsDTO.getUseAlias());
		// responseDTO.setRealPath(baseFileUrl + filePath[0] + "/" + filePath[1]);
		responseDTO.setRealPath(baseTargetUrl + filePath[0] + "/" + filePath[1]);
		logger.info("successful to persistent table:t_sk_upload for file name:" + tSkUpload.getOriginName()
				+ " file path:" + tSkUpload.getFilePath());

		return responseDTO;
	}

	@Override
	public String getFilePath(String encodeName, String useAlias) throws BizException {
		SkUpload params = new SkUpload();
		params.setEncodeName(encodeName);
		params.setFlag(Constants.FLAG_0);
		SkUpload skUpload = skUploadService.findBySkUpload(params);
		if (skUpload == null) {
			throw new StoreKeeperServiceException("can not find file with fileId[" + encodeName + "]");
		}
		String baseTargetUrl = aliasUtils.convertUrlByAlias(useAlias);
		String realPath = baseTargetUrl + skUpload.getFileGroup() + "/" + skUpload.getFilePath();
		return realPath;
	}

	@Override
	public FastDFSFile downloadFile(String encodeName) throws IOException, MyException, BizException {
		SkUpload params = new SkUpload();
		params.setEncodeName(encodeName);
		params.setFlag(Constants.FLAG_0);
		SkUpload skUpload = skUploadService.findBySkUpload(params);
		if (skUpload == null) {
			throw new StoreKeeperServiceException("can not find file with fileId[" + encodeName + "]");
		}
		String filePath = skUpload.getFilePath();
		byte[] fileContent = FileManager.getByte(skUpload.getFileGroup(), filePath);
		String ext = filePath.substring(filePath.lastIndexOf(point) + 1);
		String fileName = skUpload.getOriginName();
		if (fileName == null) {
			fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(point));
		}
		FastDFSFile dfsFile = new FastDFSFile(fileContent, fileName, ext);
		return dfsFile;
	}

	@Override
	public int deleteFile(String encodeName) throws IOException, MyException, BizException {
		SkUpload params = new SkUpload();
		params.setEncodeName(encodeName);
		params.setFlag(Constants.FLAG_0);
		SkUpload skUpload = skUploadService.findBySkUpload(params);
		// 逻辑删除
		skUpload.setFlag(Constants.FLAG_1);
		int result = skUploadService.modifySkUploadByUid(skUpload);
		/*
		 * if (skUpload == null) { throw new
		 * StoreKeeperServiceException("can not find file with fileId[" + encodeName +
		 * "]"); } String filePath = skUpload.getFilePath(); int result =
		 * FileManager.delete(skUpload.getFileGroup(), filePath);
		 */
		return result;
	}

	/**
	 * 获取扩展名
	 * 
	 * @param file
	 * @return
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月16日下午4:31:23
	 */
	private String getSuffix(MultipartFile file) {
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(point));
		return suffix;
	}

	/**
	 * Base64 encode
	 * 
	 * @param input
	 * @return
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月16日下午4:34:42
	 */
	private String encodeBase64(String input) {
		return Encodes.encodeBase64(input.getBytes());
	}

}
