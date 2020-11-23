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
package com.fosun.storekeeper.service.impl; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fosun.storekeeper.entity.SkDict;
import com.fosun.storekeeper.exception.BizException;
import com.fosun.storekeeper.mapper.SkDictMapper;
import com.fosun.storekeeper.service.SkDictService;

/**
 * SkDictService接口实现类
 * @author hengjb
 * @date 2019/08/26
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class SkDictServiceImpl implements SkDictService {

	@Autowired
    private SkDictMapper skDictMapper;

	@Override
	public SkDict findSkDictByUid(Integer id) throws BizException {
		try {
            return skDictMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            throw new BizException(e);
        }
	}

	@Override
	public Integer addSkDict(SkDict skDict) throws BizException {
		try {
			return skDictMapper.insert(skDict);
		} catch (final Exception e) {
			throw new BizException(e);
		}
	}

	@Override
	public Integer deleteSkDictByUid(Integer id) throws BizException {
		try {
			return skDictMapper.deleteByPrimaryKey(id);
		} catch (final Exception e) {
			throw new BizException(e);
		}
	}

	@Override
	public Integer modifySkDictByUid(SkDict skDict) throws BizException {
		try {
			return skDictMapper.updateByPrimaryKey(skDict);
		} catch (final Exception e) {
			throw new BizException(e);
		}
	}

	@Override
	public Integer getSkDictCount() throws BizException {
		try {
			return skDictMapper.selectTotalCount();
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	


}




