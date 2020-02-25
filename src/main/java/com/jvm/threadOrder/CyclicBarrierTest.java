package com.jvm.threadOrder;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-25 21:36:34
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class CyclicBarrierTest {
	public static void main(String[] args) {
		CyclicBarrier cyclicBarrierA = new CyclicBarrier(2);
		CyclicBarrier cyclicBarrierB = new CyclicBarrier(2);
		new Thread(()->{
			try {
				System.out.print("A");
				cyclicBarrierA.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}) .start();
		new Thread(()->{
			try {
				cyclicBarrierA.await();	//无限等待，剩下最后一次 交由Thread-A控制
				System.out.print("B");
				cyclicBarrierB.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}) .start();
		new Thread(()->{
			try {
				cyclicBarrierB.await(); //无限等待，剩下最后一次 交由Thread-B控制
				System.out.print("C");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}) .start();
	}
}
