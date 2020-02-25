package com.jvm.source.hashmap;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-20 23:20:23
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class CollectionTestDebugger {
	/**
	 * com.jvm.source.hashmap.CollectionTestDebugger.linkedHashMap()
	 */
	public static void linkedHashMap() throws Exception {
		new Thread(()->{
			LinkedHashMap<String, Object> map = Debugger.get("map");
			map.put("id", 12L);
			map.put("hello", "world");
			map.put("name", "张三");
			map.put("version", 1);
			
		}) .start();
		
	}
}
