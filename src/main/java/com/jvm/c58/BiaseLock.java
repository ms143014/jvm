package com.jvm.c58;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import com.jvm.BLockObject;

public class BiaseLock {
	private static BLockObject oneFileldClass = new BLockObject();
	public static void main(String[] args) throws Exception {
		oneFileldClass.setId(123456789);
		System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
		synchronized (oneFileldClass) {
			Thread.sleep(1000);
			System.out.println("----------------");
			System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
			System.out.println("----------------");
		}
		new Thread(()->{
			synchronized (oneFileldClass) {
				System.out.println("子线程。。。");
				//偏向原来的线程，这个是新线程，所以为轻量级锁00
				System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
			}
		}) .start();
		
//		Thread t = new Thread(()->{
//			synchronized (oneFileldClass) {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				System.err.println("----------------");
//				System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
//				System.err.println("aaaaaaaaaaaaaaaa");
//				System.err.println("bbbbbbbbbbbbbbbb");
//				System.err.println("cccccccccccccccc");
//				System.err.println("----------------");
//			}
//		});
//		t.start();
//		Thread.sleep(100);
//		synchronized (oneFileldClass) {
//			System.out.println("main thread:");
//			System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
//		}
//		t.join();
	}
}
