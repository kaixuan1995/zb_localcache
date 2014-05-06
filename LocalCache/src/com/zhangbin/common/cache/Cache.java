package com.zhangbin.common.cache;

/**
 * ����ӿ�
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
	 * Ĭ�ϵĻ�����Ч��
	 * 
	 * @return
	 */
	public long getDefaultExpire();

	/**
	 * ��ǰ��������
	 * 
	 * @return
	 */
	public long size();

	/**
	 * ��������
	 * 
	 * @return
	 */
	public int getMaxSize();

	public boolean isFull();

	public void remove(K key);

	public void clear();

	/**
	 * ������ռ䣬�����������
	 * 
	 * @return
	 */
	public int eliminate();
}
