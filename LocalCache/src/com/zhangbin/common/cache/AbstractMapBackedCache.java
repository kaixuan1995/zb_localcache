package com.zhangbin.common.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMapBackedCache<K, V> extends AbstractCache<K, V> {

	private Logger log = LoggerFactory.getLogger(AbstractMapBackedCache.class);

	protected Map<K, CacheEntity<K, V>> backendMap;

	public AbstractMapBackedCache() {
		super();
	}

	public AbstractMapBackedCache(CacheConfig config) {
		super(config);
		initMap();
	}

	/**
	 * 初始化用于存储数据的map
	 */
	protected abstract void initMap();

	@Override
	public void put(K key, V value, long expire) {
		writeLock.lock();
		try {
			CacheEntity<K, V> entity = new CacheEntity<K, V>(key, value, expire);
			if (isFull()) {
				log.debug("cache is full,try to eliminate,when put key=" + key);
				eliminate();
			}
			backendMap.put(key, entity);
			log.debug("key=" + key + ",is put into cache");
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public V getValue(K key) {
		readLock.lock();
		try {
			CacheEntity<K, V> cacheEntity = backendMap.get(key);
			if (cacheEntity == null) {
				log.debug("missed key=" + key);
				return null;
			}
			if (cacheEntity.isExpire()) {
				log.debug("find value but has expired,key=" + key);
				backendMap.remove(key);
				log.debug("remove key=" + key);
				return null;
			}
			log.debug("find and return key=" + key + ",value="
					+ cacheEntity.getValue());
			cacheEntity.access();
			
			return cacheEntity.getValue();
		} finally {
			readLock.unlock();
		}

	}

	@Override
	public long size() {
		readLock.lock();
		try {
			return backendMap.size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public void remove(K key) {
		writeLock.lock();
		try {
			backendMap.remove(key);
			log.debug("remove key=" + key);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			backendMap.clear();
		} finally {
			writeLock.unlock();
		}
	}
}
