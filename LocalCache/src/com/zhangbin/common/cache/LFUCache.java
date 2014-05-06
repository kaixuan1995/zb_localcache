package com.zhangbin.common.cache;

import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于HashMap的LFU机制的本地缓存 Least Frequently Used，最不经常使用
 * 
 * @author zhangbinalan
 * 
 * @param <K>
 * @param <V>
 */
public class LFUCache<K, V> extends AbstractMapBackedCache<K, V> {
	private Logger log = LoggerFactory.getLogger(LFUCache.class);

	public LFUCache() {
		super();
	}

	public LFUCache(CacheConfig config) {
		super(config);
	}

	@Override
	protected void initMap() {
		backendMap = new HashMap<K, CacheEntity<K, V>>();
	}

	/**
	 * 先清理过期的，如果没有过期数据，则找到使用次数最少的
	 */
	@Override
	public int eliminate() {
		writeLock.lock();
		try {
			int removeCount = 0;
			K minKey = null;
			long maxCountPerTime = Long.MAX_VALUE;
			Iterator<CacheEntity<K, V>> iterator = backendMap.values()
					.iterator();
			while (iterator.hasNext()) {
				CacheEntity<K, V> entity = iterator.next();
				if (entity.isExpire()) {
					iterator.remove();
					removeCount++;
				}
				long last = entity.getLastAccessTime();
				int accessCount = entity.getAccessCount();
				long countPerTime = accessCount
						/ (System.currentTimeMillis() - last);
				if (maxCountPerTime > countPerTime) {
					maxCountPerTime = countPerTime;
					minKey = entity.getKey();
				}
			}
			// 如果没有过期数据,移除LFU
			if (removeCount == 0 && minKey != null) {
				log.debug("not find expire item,to remove LFU item,key="
						+ minKey);
				remove(minKey);
			}
			return removeCount > 0 ? removeCount : 1;
		} finally {
			writeLock.unlock();
		}
	}
}
