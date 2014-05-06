package com.zhangbin.common.cache;

/**
 * 缓存接口
 * 
 * @author zhangbinalan
 * 
 * @param <K>
 * @param <V>
 */
public interface Cache<K, V> {
	public void put(K key, V value);

	public void put(K key, V value, long expire);

	public V getValue(K key);

	/**
	 * 默认的缓存有效期
	 * 
	 * @return
	 */
	public long getDefaultExpire();

	/**
	 * 当前缓存容量
	 * 
	 * @return
	 */
	public long size();

	/**
	 * 最大的容量
	 * 
	 * @return
	 */
	public int getMaxSize();

	public boolean isFull();

	public void remove(K key);

	public void clear();

	/**
	 * 清理缓存空间，清理过期数据
	 * 
	 * @return
	 */
	public int eliminate();
}
