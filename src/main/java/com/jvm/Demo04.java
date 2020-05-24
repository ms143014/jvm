package com.jvm;

public class Demo04 {
	public static void main(String[] args) {
		Debugger.startAndJoin(()->{
			System.out.println();
		});
	}
	public static void hello(Object a) {
		System.out.println("a: " + a);
	}
}
