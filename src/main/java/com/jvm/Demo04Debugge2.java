package com.jvm;

public class Demo04Debugge2 {
	/**
	 * com.jvm.Demo04Debugge2.t0()
	 */
	public static void t0() throws Exception {
		new Thread(()->{
			System.out.println("hello123456");
		}) .start();
	}
	/**
	 * com.jvm.Demo04Debugge1.t1()
	 */
	public static void t1() throws Exception {
		System.out.println("新方法");
		
	}
}
