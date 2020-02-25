package com.jvm.source.hashmap;

import java.util.Properties;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-18 11:09:00
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class HashMapSource {
	public static void main(String[] args) throws Exception {
//		Debugger.startDaemon(()->{
//			Properties properties = new Properties();
//		}).join();
		new Thread(()->{
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}) .start();
	}
}
