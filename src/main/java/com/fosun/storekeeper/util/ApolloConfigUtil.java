package com.fosun.storekeeper.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.fosun.storekeeper.enums.ApolloConfigEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置管理
 * 
 * @author Ji.huazhen
 *
 */
@Component
@Slf4j
public class ApolloConfigUtil {

	@ApolloConfig
	private Config config;

	/**
	 * 获取配置值
	 * 
	 * @param creditConfigEnum
	 * @return
	 */
	public String getVal(ApolloConfigEnum configEnum) {
		if (configEnum == null || StringUtils.isBlank(configEnum.getKey())
				|| StringUtils.isBlank(configEnum.getDefaultVal())) {
			log.warn("[配置值Key或DefaultVal未存在],Key={},DefaultVal={}", configEnum.getKey(), configEnum.getDefaultVal());
			return null;
		}

		try {
			String value = config.getProperty(configEnum.getKey(), configEnum.getDefaultVal());
			if (StringUtils.isBlank(value)) {
				log.error("[配置值Key为空],Key={}", configEnum.getKey());
				return null;
			}
			return value;
		} catch (Exception e) {
			log.error("[获取配置异常],Key={}", configEnum.getKey());
			log.error("[获取配置异常],Exception = {}", e);
		}
		return null;
	}
}
