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
package com.fosun.storekeeper.mapper; 

import org.apache.ibatis.annotations.Param;

import com.fosun.storekeeper.entity.SkUpload;

/**
 * SkUploadMapper
 * @author hengjb
 * @date 2019/08/26
 */
public interface SkUploadMapper {

    /**
     * insert function
     * @param skUpload
     * @return
     */
	int insert(SkUpload skUpload);
	
	/**
	 * delete function
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(@Param("id") Integer id);
	
	/**
	 * select function, by the id
	 * @param id
	 * @return
	 */
	SkUpload selectByPrimaryKey(@Param("id") Integer id);
	
	/**
	 * select function
	 * @param skUpload
	 * @return
	 */
	SkUpload selectBySkUpload(SkUpload skUpload);
	
	/**
	 * update function
	 * @param skUpload
	 * @return
	 */
	int updateByPrimaryKey(SkUpload skUpload);
	
	/**
     * sum the count
     * @return
     */
    Integer selectTotalCount();

}