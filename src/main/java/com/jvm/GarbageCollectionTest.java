package com.jvm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import org.openjdk.jol.info.ClassLayout;

import sun.misc.Unsafe;

public class GarbageCollectionTest {
	private static Unsafe unsafe;
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static class A{
		private B b;
		private byte[] bs = new byte[30*1024*1024]; //30MB
		public B getB() {
			return b;
		}
		public void setB(B b) {
			this.b = b;
		}
	}
	static class B{
		private A a;
		private byte[] bs = new byte[1*1024*1024]; //30MB
		public A getA() {
			return a;
		}
		public void setA(A a) {
			this.a = a;
		}
	}
	private static volatile boolean exitObjectCreateThread = false; 
	public static void main(String[] args) {
		/*java.util.List<byte[]> bytes = new ArrayList<>();
		byte[] byteOne = new byte[10*1024];
		bytes.add(byteOne);*/
		
		com.jvm.User user = new User();
		user.setI(1);
		user.setId(10L);
		user.setL(66);
		user.setName("zhangsan");
		user.setUsername("zs");
		System.out.println(user);
		
		/*打印所有线程运行信息*/
		Thread jStackThread = new Thread(()->{
			Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
			for(Map.Entry<Thread, StackTraceElement[]>entry: stacks.entrySet()) {
				System.out.println("线程:"+entry.getKey().getName());
				for(StackTraceElement stackTraceElement: entry.getValue()) {
					System.out.println("\t"+stackTraceElement);
				}
			}
			
		});
		jStackThread.setName("jstack打印信息");
		jStackThread.start();
		
		List<byte[]> byteKeeper = new ArrayList<>();
		int addInterval = 250;
		new Thread(()->{
			while(!exitObjectCreateThread) {
				try {
					Thread.sleep(addInterval);
					byteKeeper.add(new byte[20*1024]);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"memory_used") .start();
		
		new Thread(()->{
			while(!exitObjectCreateThread) {
				try {
					Thread.sleep(addInterval + 50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				byteKeeper.remove(0);
			}
		}, "memory_free").start();
		
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		new Thread(()->{
			try {
				lock.lock();
				condition.await();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
			
		}, "LockThread").start(); 
		
		try(Scanner scanner = new Scanner(System.in);) {
			whileLabel: 
			while(true) {
				String line = scanner.nextLine();
				switch (line) {
				case "exit":
					exitObjectCreateThread = true;
					System.out.println("准备退出对象创建线程");
					break whileLabel;
				case "notify":
					try {
						lock.lock();
						condition.signal();
						System.out.println("准备退出锁线程");
					}finally {
						lock.unlock();
					}
					
					break;
				default:
					System.out.println("您输入的是:" + line);
				}
			}
		}
	}
	public static void printBytes() {
		System.out.println(new byte[10*1024]);
		try(Scanner scanner = new Scanner(System.in);) {
			if(scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
		}
	}
}
