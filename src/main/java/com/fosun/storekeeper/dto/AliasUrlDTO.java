package com.fosun.storekeeper.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AliasUrlDTO implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -482519374809280819L;

	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 地址
	 */
	private String url;

}
