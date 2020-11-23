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
package com.fosun.storekeeper.service; 

import com.fosun.storekeeper.entity.SkUpload;
import com.fosun.storekeeper.exception.BizException;

/**
 * SkUploadService接口
 * @author hengjb
 * @date 2019/08/26
 */
public interface SkUploadService {

    /**
     * 新增上传信息
     * @param skUpload
     * @return
     * @throws BizException
     */
	Integer addSkUpload(SkUpload skUpload) throws BizException;
	
	/**
	 * 删除上传信息
	 * @param id
	 * @return
	 * @throws BizException
	 */
	Integer deleteSkUploadByUid(Integer id) throws BizException;
	
	/**
	 * 查询上传信息（根据id）
	 * @param id
	 * @return
	 * @throws BizException
	 */
	SkUpload findSkUploadByUid(Integer id) throws BizException;
	
	/**
	 * 查询上传信息
	 * @param skUpload
	 * @return
	 * @throws BizException
	 */
	SkUpload findBySkUpload(SkUpload skUpload) throws BizException;
	
	/**
	 * 修改上传信息
	 * @param skUpload
	 * @return
	 * @throws BizException
	 */
	Integer modifySkUploadByUid(SkUpload skUpload) throws BizException;

	//List<SkUpload> findAll(Integer pageNum, Integer pageSize) throws BizException;

	/**
	 * 获取上传信息总数
	 * @return
	 * @throws BizException
	 */
	Integer getSkUploadCount() throws BizException;

}





