package com.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;

@SuppressWarnings("unused")
public class ThreadLocal1 {
	
	private static ThreadLocal<Integer> TRANSACTION = new ThreadLocal<>();
	private static ThreadLocal<String> CONNECTIONS = new ThreadLocal<>();
	private static ThreadLocal<Boolean> COMMIT = new ThreadLocal<>();
	
	
	public static void main(String[] args) {
		ThreadLocal1 local1 = new ThreadLocal1();
		
		local1.doSomeThing0();
		local1.doSomeThing1();
		local1.doSomeThing2();
		
		Debugger.set(99, TRANSACTION.hashCode());
		Debugger.set(101, local1.getClass().getClassLoader());
		Debugger.set(102, local1);
		
		Debugger.set(100, (Consumer<Integer>)((Integer i)->{
			System.out.println("s"+i);
		}));
		
		TRANSACTION.get();
	}
	private void doSomeThing0() {
		System.out.println("设置值");
		TRANSACTION.set(-1);
		CONNECTIONS.set(RandomStringUtils.randomNumeric(10));
	}
	private void doSomeThing1() {
		System.out.println(TRANSACTION.get());
		System.out.println(CONNECTIONS.get());
	}
	private void doSomeThing2() {
		System.out.println(TRANSACTION.get());
		System.out.println(CONNECTIONS.get());
	}
}
