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

import com.fosun.storekeeper.entity.SkDict;

/**
 * SkDictMapper
 * @author hengjb
 * @date 2019/08/26
 */
public interface SkDictMapper {

    /**
     * insert funciton
     * @param skDict
     * @return
     */
    int insert(SkDict skDict);

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
    SkDict selectByPrimaryKey(@Param("id") Integer id);

    /**
     * update function
     * @param skDict
     * @return
     */
    int updateByPrimaryKey(SkDict skDict);

    /**
     * sum the count
     * @return
     */
    Integer selectTotalCount();

}