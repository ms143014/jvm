package com.d.p;

public class AImpl implements AInterface{
	public static void main(String[] args) throws Exception {
		Debugger.startAndJoin(()->{
			System.out.println();
			System.out.println("ok");
		});
	}
}
