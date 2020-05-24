package com.jvm.source;

import java.util.Map;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-27 13:18:18
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class CHMDebugger {
	/**
	 * com.jvm.source.CHMDebugger.t0()
	 */
	public static void t0() throws Exception {
		new Thread(()->{
			Map<String, Object> map = Debugger.get("map");
			map.put("hello", 666);
		}) .start();
	}
	/**
	 * com.jvm.source.CHMDebugger.t1()
	 */
	public static void t1() throws Exception {
		new Thread(()->{
			Map<String, Object> map = Debugger.get("map");
			map.put("hello", 777);
		}) .start();
	}
}
