package com.fosun.storekeeper.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.fosun.storekeeper.dto.AliasUrlDTO;
import com.fosun.storekeeper.enums.ApolloConfigEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 别名处理工具类
 * 
 * @author Ji.huazhen
 *
 */
@Component
@Slf4j
public class AliasUtils {

	@Value("${file_server_url}")
	private String baseFileUrl;
	
	@Value("${storekeeper.file.server.url}")
	private String storekeeperFileServerUrl;

//	@Autowired
//	private ApolloConfigUtil apolloConfigUtil;

	/**
	 * 基于别名处理访问地址
	 * 
	 * @param aliasFlag
	 * @return
	 */
	public String convertUrlByAlias(String aliasFlag) {

		if (StringUtils.isBlank(aliasFlag)) {
			return baseFileUrl;
		}
//		String skFileServerUrlVal = apolloConfigUtil.getVal(ApolloConfigEnum.STOREKEEPER_FILE_SERVER_URL);
		String skFileServerUrlVal = storekeeperFileServerUrl;
		if (StringUtils.isBlank(skFileServerUrlVal)) {
			return baseFileUrl;
		}
		try {
			List<AliasUrlDTO> aliasUrlList = JSONArray.parseArray(skFileServerUrlVal, AliasUrlDTO.class);
			if (CollectionUtils.isEmpty(aliasUrlList)) {
				return baseFileUrl;
			}
			for (AliasUrlDTO aliasUrlDTO : aliasUrlList) {
				if (aliasFlag.equals(aliasUrlDTO.getAlias()) && StringUtils.isNotBlank(aliasUrlDTO.getUrl())) {
					log.info("[StoreKeeper别名处理]别名入参 = {},对应URL= {}", aliasFlag, aliasUrlDTO.getUrl());
					return aliasUrlDTO.getUrl();
				}
			}
			return baseFileUrl;
		} catch (Exception e) {
			log.error("转换Alias别名发生异常", e);
			return baseFileUrl;
		}
	}

}
