package com.jvm.source;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-16 12:04:49
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class HashCodeTest {
	public static void main(String[] args) throws Exception {
		Map<Integer, List<Object>> _data = new HashMap<>();
		Thread t = Debugger.startDaemon(()->{
			Map<Integer, List<Object>> data = _data;
			System.out.println();
			checkMap(_data);
		});
		t.join();
	}
	public static void checkMap(Map<Integer, List<Object>> data) {
		System.out.println("开始遍历");
		java.util.Iterator<Map.Entry<Integer, List<Object>>> it = data.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, List<Object>> entry = it.next();
			if(entry.getValue().size() > 1) {
				System.out.println(entry);
			}
		}
		System.out.println("遍历完毕");
	}
}
