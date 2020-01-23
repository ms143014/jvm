package com.jvm.source;

import java.util.HashMap;
import java.util.Map;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-16 21:34:30
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class HashMapTest {
	public static void main(String[] args) throws Exception{
		Map<String, Object> _map = new HashMap<>();
		Debugger.startDaemon(()->{
			Map<String, Object> map = _map;
			System.out.println(map);
			map.put(new String("hello"), "将会更新");
		}).join();
	}
}
