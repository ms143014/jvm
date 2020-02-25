package com.jvm.threadOrder;

import java.util.concurrent.Semaphore;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-25 17:50:49
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ThreadO {
	public static void main(String[] args) {
		Semaphore semaphoreA = new Semaphore(1);
		Semaphore semaphoreB = new Semaphore(1);
		new Thread(()->{
			System.out.print("A");
			semaphoreA.release();
		}) .start();
		new Thread(()->{
			try {
				semaphoreA.acquire();
				System.out.print("B");
				semaphoreB.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}) .start();
		new Thread(()->{
			try {
				semaphoreB.acquire();
				System.out.println("C");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}) .start();
	}
}
