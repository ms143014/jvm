package com.d.p;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2019-12-18 18:12:26
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class SingleThread {
	public static void main(String[] args) {
		new Thread(()-> {
			Thread curThd = Thread.currentThread();
			System.out.println(curThd.isInterrupted());
			curThd.interrupt();
			System.out.println(curThd.isInterrupted());
			try {
				Thread.sleep(10000);
			}catch (InterruptedException e) {
				System.err.println("线程唤醒1");
			}
			System.out.println("线程结束");
		}).start();
	}
}
