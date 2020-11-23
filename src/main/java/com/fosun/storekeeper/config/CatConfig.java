package com.fosun.storekeeper.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dianping.cat.servlet.CatFilter;
import com.fosun.storekeeper.plugin.CatMybatisPlugin;

/**
 * CAT 监控
 * 
 * @author Ji.huazhen
 *
 */
@Configuration
public class CatConfig {

	private static final String CAT_FILTER_NAME = "cat-filter";
	private static final String CAT_FILTER_URL_PATTERNS = "/*";

	@Bean
	public FilterRegistrationBean catFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		CatFilter filter = new CatFilter();
		registration.setFilter(filter);
		registration.addUrlPatterns(CAT_FILTER_URL_PATTERNS);
		registration.setName(CAT_FILTER_NAME);
		registration.setOrder(2);
		return registration;
	}

	@Bean
	public CatMybatisPlugin catMybatisPlugin() {
		CatMybatisPlugin catMybatisPlugin = new CatMybatisPlugin();
		return catMybatisPlugin;
	}

}
