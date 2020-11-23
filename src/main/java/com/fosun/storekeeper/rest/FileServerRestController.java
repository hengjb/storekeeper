/*
 * FileServerRestController.java 2019年8月13日 下午3:27:38 Copyright 2017 Fosun Financial. All Rights Reserved. DO NOT ALTER
 * OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact Fosun Corporation or visit www.fosun.com if you need additional information or have any questions.
 * 
 * @author hengjb
 * 
 * @version 1.0
 */

package com.fosun.storekeeper.rest;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ProgressSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.fosun.storekeeper.common.Constants;
import com.fosun.storekeeper.common.JsonResultEnum;
import com.fosun.storekeeper.dto.FileRequestDTO;
import com.fosun.storekeeper.dto.FileResponseDTO;
import com.fosun.storekeeper.dto.ProgressResponseDTO;
import com.fosun.storekeeper.dto.ImageUploadResponseDTO;
import com.fosun.storekeeper.exception.BizException;
import com.fosun.storekeeper.exception.StoreKeeperServiceException;
import com.fosun.storekeeper.service.SkService;
import com.fosun.storekeeper.util.FastDFSFile;
import com.fosun.storekeeper.util.ImageUtil;
import com.fosun.storekeeper.util.JsonResult;

/**
 * 文件存储服务
 * 
 * @version
 * @author hengjb 2019年8月13日下午3:27:38
 * @since 1.8
 */
@RestController
@RequestMapping({ "/api" })
public class FileServerRestController {

	private static Logger logger = LoggerFactory.getLogger(FileServerRestController.class);

	@Autowired
	private SkService skService;

	@Value("${temp_file_dir}")
	private String tempFileDir;

	/**
	 * 2M字节数
	 */
	private final long twoSize = 2097152;

	/**
	 * 10M字节数
	 */
	private final long tenSize = 10485760;

	/**
	 * 20M字节数
	 */
	private final long twentySize = 20971520;

	/**
	 * zip扩展名
	 */
	private final String zipExt = ".zip";

	/**
	 * 文件上传（重要接口）
	 * 
	 * @param requetsDTO
	 * @param model
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:20:44
	 */
	@ResponseBody
	@RequestMapping(value = { "/upload" }, method = { RequestMethod.POST }, produces = { Constants.APPLICATION_JSON })
	public ResponseEntity<JsonResult<FileResponseDTO>> uploadFile(@Valid FileRequestDTO requetsDTO, Model model,
			@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("start uploading files from system. request params are: {}" + JSON.toJSONString(requetsDTO));
		JsonResult<FileResponseDTO> jsonResult = new JsonResult(JsonResultEnum.FAILURE.getCode(), "文件上传失败");
		try {
			FileResponseDTO responseDTO = this.skService.upload(requetsDTO, file);
			logger.info("uploading files having done.");
			if (StringUtils.equals(responseDTO.getUploadStatus(), JsonResultEnum.SUCCESS.getCode())) {
				jsonResult.setType(JsonResultEnum.SUCCESS.getCode());
				jsonResult.setData(responseDTO);
				jsonResult.setMessage("文件上传成功");
			} else {
				jsonResult.setMessage(responseDTO.getErrorDetail());
			}
		} catch (Exception e) {
			logger.error("uploadFile 文件上传系统异常", e);
		} finally {
			if (StringUtils.isNoneBlank(requetsDTO.getRandomCode())) {
				ProgressSingleton.remove(requetsDTO.getRandomCode());
			}
		}
		logger.info("upload file return jsonResult: {}", JSON.toJSONString(jsonResult));
		return new ResponseEntity(jsonResult, HttpStatus.OK);
	}

	/**
	 * 图片上传（重要接口）
	 * 
	 * @param file
	 * @param randomCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:21:11
	 */
	@ResponseBody
	@RequestMapping(value = { "/imageUpload" }, method = { RequestMethod.POST }, produces = {
			Constants.APPLICATION_JSON })
	public ImageUploadResponseDTO imageUpload(@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "randomCode", required = true) String randomCode,
			@RequestParam(value = "useAlias", required = false) String useAlias, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (file == null || file.isEmpty()) {
			logger.error("imageUpload 没有要上传的文件!");
			return null;
		}
		// 文件大小
		long fileSize = file.getSize();
		logger.info("imageUpload 开始上传，randomCode为：{}, 文件大小为：{}", randomCode, fileSize);
		FileResponseDTO responseDTO = new FileResponseDTO();
		ImageUploadResponseDTO fileResponseDTO = new ImageUploadResponseDTO();
		BufferedImage bi = ImageIO.read(file.getInputStream());
		// 设置图片宽高供前台使用
		fileResponseDTO.setImgWidth(bi.getWidth());
		fileResponseDTO.setImgHeight(bi.getHeight());
		try {
			FileRequestDTO requetsDTO = new FileRequestDTO();
			requetsDTO.setBizGroup(Constants.YUNTONG_GROUP);
			requetsDTO.setBizSub(file.getOriginalFilename());
			requetsDTO.setSysName(Constants.YUNTONG_SYS_CODE);
			// 唯一标识，用于查询进度
			requetsDTO.setRandomCode(randomCode);
			// 使用别名逻辑
			requetsDTO.setUseAlias(useAlias);
			responseDTO = skService.upload(requetsDTO, file);
			fileResponseDTO.setFileName(file.getOriginalFilename());
			fileResponseDTO.setFileSize(fileSize);
			fileResponseDTO.setRealPath(responseDTO.getRealPath());
			fileResponseDTO.setId(responseDTO.getEncodeName());
			fileResponseDTO.setUploadStatus(responseDTO.getUploadStatus());
		} catch (Exception e) {
			logger.error("imageUpload 文件上传系统异常", e);
		} finally {
			ProgressSingleton.remove(randomCode);
		}
		return fileResponseDTO;
	}

	/**
	 * 图片压缩上传
	 * 
	 * @param file
	 * @param randomCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:21:25
	 */
	@ResponseBody
	@RequestMapping(value = { "/imageCompressUpload" }, method = { RequestMethod.POST }, produces = {
			Constants.APPLICATION_JSON })
	public ImageUploadResponseDTO imageCompressUpload(@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "randomCode", required = true) String randomCode,
			@RequestParam(value = "useAlias", required = false) String useAlias, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (file == null || file.isEmpty()) {
			logger.error("imageCompressUpload 没有要上传的文件!");
			return null;
		}
		// 文件大小
		long fileSize = file.getSize();
		logger.info("imageCompressUpload 图片压缩上传开始，randomCode为：{}, 文件大小为：{}", randomCode, fileSize);
		// 文件名称
		String originalFilename = file.getOriginalFilename();
		FileResponseDTO responseDTO = new FileResponseDTO();
		ImageUploadResponseDTO fileResponseDTO = new ImageUploadResponseDTO();
		BufferedImage bi = ImageIO.read(file.getInputStream());
		// 设置图片宽高供前台使用
		fileResponseDTO.setImgWidth(bi.getWidth());
		fileResponseDTO.setImgHeight(bi.getHeight());

		// 文件内容（上传文件【压缩或不压缩的文件】）初始化32字节
		byte[] contents = new byte[32];
		double comBase = 2000;
		double scale = 0.9d;

		if (fileSize <= this.twoSize) {
			contents = file.getBytes();
		} else if (fileSize > this.twoSize && fileSize <= this.tenSize) {
			comBase = bi.getWidth();
			contents = ImageUtil.saveMinPhoto(bi, originalFilename, comBase, scale);
		} else if (fileSize > this.tenSize && fileSize <= this.twentySize) {
			comBase = bi.getWidth() * 2 / 3;
			contents = ImageUtil.saveMinPhoto(bi, originalFilename, comBase, scale);
		} else {
			logger.error("imageCompressUpload 文件超限20M，禁止上传！");
			fileResponseDTO.setUploadStatus(JsonResultEnum.FAILURE.getCode());
			return fileResponseDTO;
		}

		try {
			FileRequestDTO requetsDTO = new FileRequestDTO();
			requetsDTO.setBizGroup(Constants.YUNTONG_GROUP);
			requetsDTO.setBizSub(file.getOriginalFilename());
			requetsDTO.setSysName(Constants.YUNTONG_SYS_CODE);
			// 唯一标识，用于查询进度
			requetsDTO.setRandomCode(randomCode);
			// 使用别名逻辑
			requetsDTO.setUseAlias(useAlias);
			responseDTO = skService.compressUpload(requetsDTO, originalFilename, contents);
			fileResponseDTO.setFileName(file.getOriginalFilename());
			fileResponseDTO.setFileSize(fileSize);
			fileResponseDTO.setRealPath(responseDTO.getRealPath());
			fileResponseDTO.setId(responseDTO.getEncodeName());
			fileResponseDTO.setUploadStatus(responseDTO.getUploadStatus());
		} catch (Exception e) {
			logger.error("imageCompressUpload 文件上传异常！", e);
			fileResponseDTO.setUploadStatus(JsonResultEnum.FAILURE.getCode());
			return fileResponseDTO;
		} finally {
			ProgressSingleton.remove(randomCode);
		}
		return fileResponseDTO;
	}

	/**
	 * 文件上传进度查询（使用场景少，已知服务的应用有“客多售”）
	 * 
	 * @param randomCode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:21:46
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryProgress" }, method = { RequestMethod.POST }, produces = {
			Constants.APPLICATION_JSON })
	public ProgressResponseDTO queryProgress(String randomCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("文件上传进度查询，randomCode：{}", randomCode);
		String progress = (String) ProgressSingleton.get(randomCode);
		progress = progress == null ? "0.00%" : progress;
		logger.info("文件上传进度，progress：{}", progress);
		ProgressResponseDTO dto = new ProgressResponseDTO();
		dto.setProgress(progress);
		return dto;
	}

	/**
	 * 文件下载（重要接口）
	 * 
	 * @param requetsDTO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:22:02
	 */
	@ResponseBody
	@RequestMapping(value = { "/download" }, method = { RequestMethod.GET }, produces = { Constants.APPLICATION_JSON })
	public ResponseEntity<JsonResult<FileResponseDTO>> download(@Valid FileRequestDTO requetsDTO, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("download 请求参数：{}", JSON.toJSONString(requetsDTO));
			JsonResult<FileResponseDTO> jsonResult = new JsonResult(JsonResultEnum.SUCCESS.getCode(), "文件下载成功");
			String filePath = skService.getFilePath(requetsDTO.getEncodeName(), requetsDTO.getUseAlias());
			FileResponseDTO responseDTO = new FileResponseDTO();
			responseDTO.setRealPath(filePath);
			jsonResult.setData(responseDTO);
			logger.info("get the file path:" + responseDTO.getRealPath() + ",from system:" + requetsDTO.getSysName());
			return new ResponseEntity(jsonResult, HttpStatus.OK);
		} catch (BizException e) {
			logger.error("download error", e);
			return new ResponseEntity(new JsonResult(JsonResultEnum.FAILURE.getCode(), "文件下载失败"), HttpStatus.OK);
		}
	}

	/**
	 * 文件批量下载（重要接口）
	 * 
	 * @param encodeNames
	 * @param zipName
	 * @param response
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:22:15
	 */
	@ResponseBody
	@RequestMapping(value = { "/batchDownload" }, method = { RequestMethod.GET })
	public void batchDownloadZip(String encodeNames, String zipName, HttpServletResponse response) {
		logger.info("batchDownload 文件批量下载请求参数，encodeNames:{};  zipName:{}", encodeNames, zipName);
		// 初始化返回
		// JsonResult<FileResponseDTO> jsonResult = new
		// JsonResult<FileResponseDTO>(JsonResultEnum.SUCCESS.getCode(), "文件下载成功");
		if (encodeNames == null || "".equals(encodeNames)) {
			// 影像ID不能为空
			throw new StoreKeeperServiceException("影像编号不能为空");
		}
		if (zipName == null || "".equals(zipName)) {
			// 压缩文件名称不能为空
			throw new StoreKeeperServiceException("压缩文件名称不能为空");
		}
		// 影像ID
		String[] imageIds = encodeNames.split(",");
		if (!zipName.endsWith(zipExt)) {
			// 如果zip文件客户端只传了名称，没有后缀，此处添加
			zipName = zipName + zipExt;
		}
		// zip文件全路径
		String zipFilePath = tempFileDir + zipName;
		File zipFile = null;
		try {
			// 初始化zipfile
			zipFile = new File(zipFilePath);
			// zip文件生成
			initFilesZip(zipFile, imageIds);
			// 直接返回文件流
			responseFileStream(zipFile, response);
		} catch (Exception e) {
			logger.error("将zip文件打包并返回前端发生异常", e);
		} finally {
			if (zipFile != null && zipFile.isFile() && zipFile.exists()) {
				// 删除临时zip文件
				zipFile.delete();
			}
		}
	}

	/**
	 * 生成zip文件
	 * 
	 * @param zipFilePath
	 * @param zipFile
	 * @param imageIds
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午4:15:06
	 */
	private void initFilesZip(File zipFile, String[] imageIds) {
		// zip输出流
		ZipOutputStream zos = null;
		List<File> fileList = new ArrayList<File>();
		try {
			// zipFile = new File(zipFilePath);
			zos = new ZipOutputStream(new FileOutputStream(zipFile));
			// 先批量下载文件，并放入临时目录
			for (int i = 0; i < imageIds.length; i++) {
				logger.info("zip包添加第" + (i + 1) + "个附件");
				// 影像ID
				String imageId = imageIds[i];
				// 输入字节流
				ByteArrayInputStream bais = null;
				try {
					FastDFSFile file = skService.downloadFile(imageId);
					if (file != null) {
						// 创建临时文件
						File tempFile = new File(tempFileDir + file.getName() + "." + file.getExt());
						// 将文件转成文件流
						bais = new ByteArrayInputStream(file.getContent());
						// 将文件落地
						FileUtils.copyInputStreamToFile(bais, tempFile);
						// 放入文件列表
						fileList.add(tempFile);
					} else {
						logger.info("根据影像ID找不到对应的附件，imageId:{}", imageId);
					}
				} catch (Exception e) {
					// 一个文件有问题，不直接抛出异常
					logger.error("文件下载并落地异常，imageId:" + imageId, e);
				} finally {
					if (bais != null) {
						bais.close();
					}

				}
			}
			// 将临时文件放入zip
			byte[] buffer = new byte[1024];
			if (fileList != null && !fileList.isEmpty()) {
				for (File tempFile : fileList) {
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(tempFile);
						zos.putNextEntry(new ZipEntry(tempFile.getName()));
						int len;
						// 读入需要下载的文件的内容，打包到zip文件
						while ((len = fis.read(buffer)) > 0) {
							zos.write(buffer, 0, len);
						}
						zos.closeEntry();
					} catch (Exception e) {
						logger.error("将文件放入zip异常", e);
					} finally {
						if (fis != null) {
							fis.close();
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("文件批量下载打包处理异常", e);
			throw new StoreKeeperServiceException("文件批量下载打包处理异常");
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				logger.error("zip输出流关闭异常", e);
			}
			if (fileList != null && !fileList.isEmpty()) {
				for (File tempFile : fileList) {
					if (tempFile != null && tempFile.isFile() && tempFile.exists()) {
						// 删除临时临时文件
						tempFile.delete();
					}
				}
			}
		}
	}

	/**
	 * 文件删除
	 * 
	 * @param encodeName
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:22:37
	 */
	@ResponseBody
	@RequestMapping(value = { "/deletefile" }, method = { RequestMethod.GET })
	public int deletefile(String encodeName) throws Exception {
		logger.info("deletefile 逻辑删除文件，encodeName：{}", encodeName);
		int stat = skService.deleteFile(encodeName);
		return stat;
	}

	/**
	 * 文件下载核心方法（放入到HttpServletResponse）
	 * 
	 * @param file
	 * @param response
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月21日下午5:25:11
	 */
	private void responseFileStream(File file, HttpServletResponse response) {
		logger.info("responseFileStream 下载附件开始 ···");
		InputStream ins = null;
		BufferedInputStream bins = null;
		OutputStream outs = null;
		BufferedOutputStream bouts = null;
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			// 设置文件MIME类型，设置强制下载不打开
			response.setContentType("application/force-download");
			// 设置Content-Disposition
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			// 读取文件
			ins = new FileInputStream(file);
			// 放到缓冲流里面
			bins = new BufferedInputStream(ins);
			// 获取文件输出IO流
			// 读取目标文件，通过response将目标文件写到客户端
			outs = response.getOutputStream();
			bouts = new BufferedOutputStream(outs);
			// 写文件
			int bytesRead = 0;
			// bufferSum
			int bufferSum = 8192;
			byte[] buffer = new byte[bufferSum];
			// 开始向网络传输文件流
			while ((bytesRead = bins.read(buffer, 0, bufferSum)) != -1) {
				bouts.write(buffer, 0, bytesRead);
			}
			// 调用flush()方法
			bouts.flush();
		} catch (Exception e) {
			logger.error("下载附件异常", e);
			throw new StoreKeeperServiceException("下载附件异常");
		} finally {
			logger.info("下载附件结束···");
			if (bouts != null) {
				try {
					bouts.close();
				} catch (IOException e) {
					logger.error("下载附件异常-BufferedOutputStream关闭异常", e);
				}
			}
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException e) {
					logger.error("下载附件异常-OutputStream关闭异常", e);
				}
			}
			if (bins != null) {
				try {
					bins.close();
				} catch (IOException e) {
					logger.error("下载附件异常-BufferedInputStream关闭异常", e);
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					logger.error("下载附件异常-InputStream关闭异常", e);
				}
			}
		}
	}

	/**
	 * 批量文件上传 旧
	 * 
	 * @param files
	 * @param randomCodes
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月19日下午6:22:48
	 */
	@ResponseBody
	@RequestMapping(value = { "/batchUpload" }, method = { RequestMethod.POST }, produces = {
			Constants.APPLICATION_JSON })
	public List<FileResponseDTO> uploadFileBatch(
			@RequestParam(value = "file", required = true) List<MultipartFile> files,
			@RequestParam(value = "randomCode", required = false) List<String> randomCodes, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (files == null || files.isEmpty()) {
			logger.info("没有要上传的文件!");
			return null;
		}
		// 返回消息
		String rtnMsg = null;
		// 上传总文件数
		int total = files.size();
		// 上传成功数
		int succussNum = 0;
		logger.info("开始上传，总共文件个数:" + total);
		List<FileResponseDTO> respList = new ArrayList<FileResponseDTO>();
		for (MultipartFile file : files) {
			try {
				logger.info("上传文件=" + file.getOriginalFilename());
				// 附件上传
				FileRequestDTO requetsDTO = new FileRequestDTO();
				requetsDTO.setBizGroup(Constants.YUNTONG_GROUP);
				requetsDTO.setBizSub(file.getOriginalFilename());
				requetsDTO.setSysName(Constants.YUNTONG_SYS_CODE);
				FileResponseDTO responseDTO = this.skService.upload(requetsDTO, file);
				respList.add(responseDTO);
				succussNum++;
			} catch (Exception e) {
				// 因为批量上传，所以不抛出异常
				logger.error("batchUpload 文件上传系统异常", e);
			}
		}
		if (succussNum < total) {
			// 如果成功数量<总文件数，说明有异常情况
			rtnMsg = "文件上传完成，需要上传文件" + total + "份，失败" + (total - succussNum) + "份";
			logger.error(rtnMsg);
		}
		return respList;
	}

	/**
	 * 文件下载 旧
	 * 
	 * @param encodeName
	 * @param response
	 * @throws Exception
	 * @Description:
	 * @author hengjb
	 * @create date 2019年8月19日下午6:20:35
	 */
	@RequestMapping(value = { "/downfile" }, method = { RequestMethod.GET })
	public void downfile(String encodeName, HttpServletResponse response) throws Exception {
		FastDFSFile file = skService.downloadFile(encodeName);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName() + "." + file.getExt());
		response.getOutputStream().write(file.getContent());
	}

}
