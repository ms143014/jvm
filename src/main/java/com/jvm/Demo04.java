package com.jvm;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.RandomStringUtils;

public class Demo04 {
	@SuppressWarnings("unused")
	private String data;
	public Demo04() {
		super();
		this.data = RandomStringUtils.randomNumeric(100000);
	}
	public static void main(String[] args) {
		List<Demo04> demos = new Vector<Demo04>();
		CountDownLatch waitForCreateFinish = new CountDownLatch(1); 
		CountDownLatch waitForRemoveFinish = new CountDownLatch(1); 
		Thread addElementthread = new Thread(()->{
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for(int i=0; i < 300; i++) { //300*100 30秒
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				demos.add(new Demo04());
			}
			waitForCreateFinish.countDown();
		});
		addElementthread.setName("Add-Demo04-Thread");
		addElementthread.start();
		try {
			waitForCreateFinish.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("开始回收");
		new Thread(()->{
			Iterator<Demo04> iterator = demos.iterator();
			while(iterator.hasNext()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				iterator.next();
				iterator.remove();
			}
			waitForRemoveFinish.countDown();
		}) .start();
		try {
			waitForRemoveFinish.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("size:" +demos.size());
		System.out.println("数组删除结束");
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
