package com.zhangbin.common.cache;

/**
 * 本地缓存的配置类
 * 
 * @author zhangbinalan
 * 
 */
public class CacheConfig {
	/**
	 * 默认的过期时间
	 */
	private long defaultExpire;
	/**
	 * 缓存最大的数量
	 */
	private int maxSize;

	public long getDefaultExpire() {
		return defaultExpire;
	}

	public void setDefaultExpire(long defaultExpire) {
		this.defaultExpire = defaultExpire;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
