package com.zhangbin.common.cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * �������
 * 
 * @author zhangbinalan
 * 
 * @param <K>
 * @param <V>
 */
public class CacheEntity<K, V> {

	private K key;
	private V value;
	private long lastAccessTime;// ������ʱ��
	private long ttl;// ��Ч��
	private int accessCount;// ���ʴ���
	private Lock lock;

	public CacheEntity(K key, V value, long ttl) {
		this.key = key;
		this.value = value;
		this.ttl = ttl;
		this.lastAccessTime = System.currentTimeMillis();
		lock = new ReentrantLock();
	}

	public K getKey() {
		return key;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public long getTtl() {
		return ttl;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public V getValue() {
		
		return value;
	}
	public void access(){
		lock.lock();
		try {
			accessCount++;
			lastAccessTime = System.currentTimeMillis();
		} finally {
			lock.unlock();
		}
	}
	public boolean isExpire() {
		if (ttl == 0) {
			return false;
		}
		return System.currentTimeMillis() > (lastAccessTime + ttl);
	}
}
