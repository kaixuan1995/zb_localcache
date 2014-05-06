package com.zhangbin.common.local;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
	// map的三个iterator都删除的是entry整体
	public static void main(String[] args) {
		Map<String, String> map = new LinkedHashMap<String, String>(10, 0.75f,
				true);
		map.put("1", "v1");
		map.put("2", "v2");
		map.put("3", "v3");
		map.put("4", "v4");
		map.put("5", "v5");

		Iterator<String> iter = map.values().iterator();
		while (iter.hasNext()) {
			String str = iter.next();
			System.out.println(str);
			// iter.remove();
		}
		System.out.println(map.size());
		Iterator<String> iter2 = map.keySet().iterator();
		while (iter2.hasNext()) {
			String str = iter2.next();
			System.out.println(str);
			iter2.remove();
		}
		System.out.println(map.size());
	}
}
