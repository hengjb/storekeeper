package com.fosun.storekeeper.service;

import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;

import com.fosun.storekeeper.dto.FileRequestDTO;
import com.fosun.storekeeper.dto.FileResponseDTO;
import com.fosun.storekeeper.exception.BizException;
import com.fosun.storekeeper.util.FastDFSFile;

import java.io.IOException;

/**
 * storekeeper主服务接口
 * 
 * @author hengjb
 * @date 2019/08/22
 */
public interface SkService {

	/**
	 * 上传核心方法
	 * 
	 * @param requetsDTO
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws MyException
	 * @throws BizException
	 */
	FileResponseDTO upload(FileRequestDTO requetsDTO, MultipartFile file) throws IOException, MyException, BizException;

	/**
	 * 压缩上传核心方法
	 * 
	 * @param requetsDTO
	 * @param originalFilename
	 * @param contents
	 * @return
	 * @throws IOException
	 * @throws MyException
	 * @throws BizException
	 */
	FileResponseDTO compressUpload(FileRequestDTO requetsDTO, String originalFilename, byte[] contents)
			throws IOException, MyException, BizException;

	/**
	 * 获取文件下载路径
	 * 
	 * @param encodeName
	 * @return
	 * @throws BizException
	 */
	String getFilePath(String encodeName, String useAlias) throws BizException;

	/**
	 * 获取文件字节流
	 * 
	 * @param encodeName
	 * @return
	 * @throws IOException
	 * @throws MyException
	 * @throws BizException
	 */
	FastDFSFile downloadFile(String encodeName) throws IOException, MyException, BizException;

	/**
	 * 删除文件（逻辑删除）
	 * 
	 * @param encodeName
	 * @return
	 * @throws IOException
	 * @throws MyException
	 * @throws BizException
	 */
	int deleteFile(String encodeName) throws IOException, MyException, BizException;
}
