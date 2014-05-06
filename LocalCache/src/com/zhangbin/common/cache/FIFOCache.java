package com.zhangbin.common.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FIFO ���ڲ�������LinkedHashMap��cacheʵ�� First In First Out ���Ƚ��ȳ�
 * 
 * @author zhangbinalan
 * 
 * @param <K>
 * @param <V>
 */
public class FIFOCache<K, V> extends AbstractMapBackedCache<K, V> {
	private Logger log = LoggerFactory.getLogger(FIFOCache.class);

	public FIFOCache() {
		super();
	}

	public FIFOCache(CacheConfig config) {
		super(config);
	}

	@Override
	protected void initMap() {
		backendMap = new LinkedHashMap<K, CacheEntity<K, V>>();
	}

	/**
	 * ����������ݣ����û�й������ݣ������Ƚ��ȳ��Ĺ���ɾ��һ������
	 */
	@Override
	public int eliminate() {
		writeLock.lock();
		try {
			int removeCount = 0;
			CacheEntity<K, V> firstEntity = null;
			Iterator<CacheEntity<K, V>> iterator = backendMap.values()
					.iterator();
			while (iterator.hasNext()) {
				CacheEntity<K, V> entity = iterator.next();
				if (entity.isExpire()) {
					iterator.remove();
					removeCount++;
					log.debug("find expire item,key=" + entity.getKey());
				}
				if (firstEntity == null) {
					firstEntity = entity;
				}
			}
			// ���û�й�������,�Ƴ���һ��
			if (removeCount == 0) {
				remove(firstEntity.getKey());
				log.debug("not find expire item,to remove first input item,key="
						+ firstEntity.getKey());
			}
			return removeCount > 0 ? removeCount : 1;
		} finally {
			writeLock.unlock();
		}
	}
}
