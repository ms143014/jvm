package jvm.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
	private AtomicInteger count = new AtomicInteger(3);
	
	public int add() {
		return count.incrementAndGet();
	}
	
	public static void main(String[] args) {
		AtomicIntegerTest test = new AtomicIntegerTest();
		System.out.println(test.add());
	}
	
}
