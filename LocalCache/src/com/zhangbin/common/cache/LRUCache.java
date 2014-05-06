package com.zhangbin.common.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于LinkedHashMap的LRU机制的本地缓存 Least Recently Used ，最近最少使用
 * 
 * @author zhangbinalan
 * 
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> extends AbstractMapBackedCache<K, V> {
	private Logger log = LoggerFactory.getLogger(LRUCache.class);

	public LRUCache() {
		super();
	}

	public LRUCache(CacheConfig config) {
		super(config);
	}

	@Override
	protected void initMap() {
		backendMap = new LinkedHashMap<K, CacheEntity<K, V>>(getMaxSize(),
				0.75f, true) {
			private static final long serialVersionUID = -2182834482274219756L;

			/**
			 * 每次put时，都会调用这个方法判断是否要移除最近最少访问的数据
			 */
			@Override
			protected boolean removeEldestEntry(
					Entry<K, CacheEntity<K, V>> eldest) {
				boolean delete = LRUCache.this.isFull();
				if (delete) {
					log.debug("decide to delete item LRU,key="
							+ eldest.getKey());
				}
				return delete;
			}
		};
	}

	/**
	 * 仅仅清理过期数据
	 */
	@Override
	public int eliminate() {
		writeLock.lock();
		try {
			int removeCount = 0;
			Iterator<CacheEntity<K, V>> iterator = backendMap.values()
					.iterator();
			while (iterator.hasNext()) {
				CacheEntity<K, V> entity = iterator.next();
				if (entity.isExpire()) {
					iterator.remove();
					removeCount++;
					log.debug("find expire item,key=" + entity.getKey());
				}
			}
			// 如果没有过期数据,移除第一条
			if (removeCount == 0) {
				log.debug("not find expire item,to find item that last recently used");
			}
			return removeCount;
		} finally {
			writeLock.unlock();
		}
	}
}
