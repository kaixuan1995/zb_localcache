package com.zhangbin.common.cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractCache<K, V> implements Cache<K, V> {
	private long defaultExpire;// 0表示永不过期
	private int maxSize;// 0表示不限制容量

	private final ReentrantReadWriteLock cacheLock;
	protected final Lock readLock;
	protected final Lock writeLock;

	public AbstractCache() {
		this(CacheConfigFactory.getDefaultConfig());
	}

	public AbstractCache(CacheConfig config) {
		this.defaultExpire = config.getDefaultExpire();
		this.maxSize = config.getMaxSize();
		cacheLock = new ReentrantReadWriteLock();
		readLock = cacheLock.readLock();
		writeLock = cacheLock.writeLock();
	}

	@Override
	public void put(K key, V value) {
		put(key, value, getDefaultExpire());
	}

	@Override
	public long getDefaultExpire() {

		return defaultExpire;
	}

	@Override
	public int getMaxSize() {
		return maxSize;
	}

	@Override
	public boolean isFull() {
		readLock.lock();
		try {
			if (maxSize == 0) {
				return false;
			}
			return size() >= getMaxSize();
		} finally {
			readLock.unlock();
		}
	}

	public abstract int eliminate();

}
