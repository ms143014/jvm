package com.jvm.source;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-25 22:35:21
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class CHM {
	public static void main(String[] args) {
		Debugger.startThread(()->{
			Map<String, Object> map = new ConcurrentHashMap<>();
			Debugger.set("map", map);
			for(int i =0; i < 11; i++ ) {
				map.put(RandomStringUtils.randomAlphabetic(6).toLowerCase(), new Object());
			}
			map.put(RandomStringUtils.randomAlphabetic(6).toLowerCase(), new Object());
			System.out.println();
		});
	}
}
