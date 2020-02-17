package com.d.p;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class AImpl implements AInterface{
	public static void main(String[] args) throws Exception {
		/*StringBuilder sb = new StringBuilder();
		IntStream.range(0, 1024).forEach(i->{
			sb.append("long a"+i+" = 0;\n");
		});
		System.out.println(sb);*/
		Lock _lock = new ReentrantLock(true);
		Debugger.set("lock", _lock);
		
		Debugger.startDaemon(()->{
			Lock lock = _lock;
			lock.lock();
			
			System.out.println("round");
			Map<String, Object> m = new HashMap<>();
			m.put("sss", 1);
		}).join();
	}
}
