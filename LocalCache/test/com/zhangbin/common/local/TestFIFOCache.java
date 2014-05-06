package com.zhangbin.common.local;

import com.zhangbin.common.cache.CacheConfig;
import com.zhangbin.common.cache.FIFOCache;

public class TestFIFOCache {

	public static void main(String[] args) {

		CacheConfig config = new CacheConfig();
		config.setDefaultExpire(3000);
		config.setMaxSize(10);
		FIFOCache<String, String> cache = new FIFOCache<String, String>(config);
		for (int i = 0; i < 20; i++) {
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
