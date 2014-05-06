package com.zhangbin.common.cache;

public class CacheConfigFactory {
	private static CacheConfig defaultConfig;
	static {
		defaultConfig = new CacheConfig();
		defaultConfig.setDefaultExpire(2 * 60 * 1000L);// 默认过期时间两分钟
		defaultConfig.setMaxSize(1000);// 默认最多1000条数据
	}

	public static CacheConfig getDefaultConfig() {
		return defaultConfig;
	}
}
