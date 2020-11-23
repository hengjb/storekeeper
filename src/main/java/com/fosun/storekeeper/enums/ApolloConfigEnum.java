package com.fosun.storekeeper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置枚举
 * 
 * @author Ji.huazhen
 *
 */
@Getter
@AllArgsConstructor
public enum ApolloConfigEnum {

//	STOREKEEPER_DEFAULT_CONFIG("storekeeper.default.config", "storekeeper.default.config", "StoreKeeper默认测试配置"),
	
	/**
	 * StoreKeeper文件服务器访问地址 
	 */
//	STOREKEEPER_FILE_SERVER_URL("storekeeper.file.server.url", "[{\"alias\": \"default\",\"url\": \"http://alias.sk.sit.fosuntech.cn/\"},{\"alias\": \"liangfu\",\"url\": \"http://alias.sk.sit.fosuntech.cn/\"}]", "StoreKeeper文件服务器访问地址");
	DEFAULT("","","");

	/**
	 * 配置KEY
	 */
	private String key;
	/**
	 * 配置默认值
	 */
	private String defaultVal;
	/**
	 * 配置描述
	 */
	private String desc;

}