package com.zhangbin.common.cache;

import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ����HashMap��LFU���Ƶı��ػ��� Least Frequently Used�������ʹ��
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
	 * ��������ڵģ����û�й������ݣ����ҵ�ʹ�ô������ٵ�
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
			// ���û�й�������,�Ƴ�LFU
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
