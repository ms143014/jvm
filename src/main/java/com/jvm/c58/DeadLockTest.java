package com.jvm.c58;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockTest {
	private static class DeadLock extends Thread{
		private Lock lock1;
		private Lock lock2;
		public DeadLock(Lock lock1, Lock lock2, String name) {
			super(name);
			this.lock1 = lock1;
			this.lock2 = lock2;
		}

		@Override
		public void run() {
			try {
				lock1.lock();
				Thread.sleep(10);
				try {
					lock2.lock();
				}finally {
					lock2.unlock();
				}
			}catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock1.unlock();
			}
		}
	}
	public static void main(String[] args) {
		Lock lock1 = new ReentrantLock();
		Lock lock2 = new ReentrantLock();
		new DeadLock(lock1, lock2, "锁线程1").start();
		new DeadLock(lock2, lock1, "锁线程2").start();
	}
}
