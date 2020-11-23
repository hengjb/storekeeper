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

import com.fosun.storekeeper.entity.SkUpload;
import com.fosun.storekeeper.exception.BizException;
import com.fosun.storekeeper.mapper.SkUploadMapper;
import com.fosun.storekeeper.service.SkUploadService;

/**
 * SkUploadService接口实现类
 * @author hengjb
 * @date 2019/08/26
 */
@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class SkUploadServiceImpl implements SkUploadService {

	@Autowired
    private SkUploadMapper skUploadMapper;

	@Override
	public SkUpload findSkUploadByUid(Integer id) throws BizException {
		try {
            return skUploadMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            throw new BizException(e);
        }
	}

	@Override
    public SkUpload findBySkUpload(SkUpload skUpload) throws BizException {
	    try {
            return skUploadMapper.selectBySkUpload(skUpload);
        }
        catch (Exception e) {
            throw new BizException(e);
        }
    }

    @Override
	public Integer addSkUpload(SkUpload skUpload) throws BizException {
		try {
			return skUploadMapper.insert(skUpload);
		} catch (final Exception e) {
			throw new BizException(e);
		}
	}

	@Override
	public Integer deleteSkUploadByUid(Integer id) throws BizException {
		try {
			return skUploadMapper.deleteByPrimaryKey(id);
		} catch (final Exception e) {
			throw new BizException(e);
		}
	}

	@Override
	public Integer modifySkUploadByUid(SkUpload skUpload) throws BizException {
		try {
			return skUploadMapper.updateByPrimaryKey(skUpload);
		} catch (final Exception e) {
			throw new BizException(e);
		}
	}

	@Override
	public Integer getSkUploadCount() throws BizException {
		try {
			return skUploadMapper.selectTotalCount();
		} catch (Exception e) {
			throw new BizException(e);
		}
	}

}




