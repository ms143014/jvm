package com.jvm.source;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-26 22:25:18
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class SingletonMode {
	static class LazyLoad{
		private Object lock = new Object();
		private Object object = null;
		public Object initObject() {
			if(object == null) {
				synchronized (lock) {
					if(object == null) {
						Debugger.sleep(10000);
						object = new Object();
						System.out.println("init " + Thread.currentThread().getName());
					}
				}
			}
			return object;
		}
	}
	
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "2");
		map.put("1", "3");
		System.out.println(map.get("1"));
		/*Executor executor = Executors.newFixedThreadPool(10);
		LazyLoad lazyLoad = new LazyLoad();
		for(int i=0; i < 10; i++) {
			executor.execute(()->{
				System.out.println(lazyLoad.initObject());
			});
		}*/
	}
}
