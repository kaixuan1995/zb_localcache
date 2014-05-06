package com.zhangbin.common.local;

import com.zhangbin.common.cache.CacheConfig;
import com.zhangbin.common.cache.LFUCache;

public class TestLFUCache {

	public static void main(String[] args) {

		CacheConfig config = new CacheConfig();
		config.setDefaultExpire(10);
		config.setMaxSize(10);
		LFUCache<String, String> cache = new LFUCache<String, String>(config);
		for (int i = 0; i < 10; i++) {
			cache.put(i + "", "value" + i);
		}
		for (int i = 10; i > 0; i--) {
			cache.getValue(i + "");
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 10; i < 20; i++) {
			cache.put(i + "", "value" + i);
		}
		for (int i = 0; i < 20; i++) {
			String value = cache.getValue(i + "");
			if (value == null) {
				System.out.println("[missed] key=" + i);
			} else {
				System.out.println("[hit] key=" + i + "  value=" + value);
			}
		}
	}
}
