package com.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ThreadLocal0 {
	public static List<Thread> threads = new ArrayList<>();
	public static void main(String[] args) {
		ExecutorService exeService = Executors.newFixedThreadPool(100);
		IntStream.range(0, 90).forEach(i->{
			exeService.execute(()->{
				Thread curThread = Thread.currentThread();
				threads.add(curThread);
				
				new ThreadLocal<>().set(1+new Random().nextInt(10));
				new ThreadLocal<>().set(1+new Random().nextInt(10));
				new ThreadLocal<>().set(1+new Random().nextInt(10)); //??
				
				System.out.println("hello"+i);
				
				Debugger.sleep(10000000);
			});
		});
		
		exeService.execute(()->{
			boolean exit = false;
			System.out.println(threads);
			while(!exit) {
				Debugger.sleep(1000);
			}
		});
		exeService.shutdown();
	}

}
