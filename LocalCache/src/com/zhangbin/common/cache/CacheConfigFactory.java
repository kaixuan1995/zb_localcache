package com.zhangbin.common.cache;

public class CacheConfigFactory {
	private static CacheConfig defaultConfig;
	static {
		defaultConfig = new CacheConfig();
		defaultConfig.setDefaultExpire(2 * 60 * 1000L);// Ĭ�Ϲ���ʱ��������
		defaultConfig.setMaxSize(1000);// Ĭ�����1000������
	}

	public static CacheConfig getDefaultConfig() {
		return defaultConfig;
	}
}
