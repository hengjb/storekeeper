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

import com.fosun.storekeeper.entity.SkDict;
import com.fosun.storekeeper.exception.BizException;

/**
 * SkDictService接口
 * @author hengjb
 * @date 2019/08/26
 */
public interface SkDictService {

    /**
     * 新增字典
     * @param skDict
     * @return
     * @throws BizException
     */
	Integer addSkDict(SkDict skDict) throws BizException;
	
	/**
	 * 删除字典
	 * @param id
	 * @return
	 * @throws BizException
	 */
	Integer deleteSkDictByUid(Integer id) throws BizException;
	
	/**
	 * 查询字典（根据id）
	 * @param id
	 * @return
	 * @throws BizException
	 */
	SkDict findSkDictByUid(Integer id) throws BizException;
	
	/**
	 * 修改字典
	 * @param skDict
	 * @return
	 * @throws BizException
	 */
	Integer modifySkDictByUid(SkDict skDict) throws BizException;

	//List<SkDict> findAll(Integer pageNum, Integer pageSize) throws BizException;

	/**
	 * 计算字典总数
	 * @return
	 * @throws BizException
	 */
	Integer getSkDictCount() throws BizException;

}





