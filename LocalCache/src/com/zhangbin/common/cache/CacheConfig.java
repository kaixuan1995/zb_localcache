package com.zhangbin.common.cache;

/**
 * ���ػ����������
 * 
 * @author zhangbinalan
 * 
 */
public class CacheConfig {
	/**
	 * Ĭ�ϵĹ���ʱ��
	 */
	private long defaultExpire;
	/**
	 * ������������
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
