package com.jvm.queue;

import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-03-04 10:30:12
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class DelayQueueTest {
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor _executor = new ScheduledThreadPoolExecutor(1);
		Debugger.startThread(()->{
			ScheduledThreadPoolExecutor executor = _executor;
			
			System.out.println();
		});
//		Runnable r = ()->{
//			System.out.println("hshsh");
//		};
//		IntStream.range(0, 100).forEach(i->{
//			ScheduledFuture<?> scheduledFuture = executor.schedule(r, -1, TimeUnit.MILLISECONDS);
//		});
//		Debugger.sleep(1000);
		
//		Iterator<Runnable> queue = _executor.getQueue().iterator();
//		while(queue.hasNext()) {
//			ScheduledFuture<?> future = (ScheduledFuture<?>)queue.next();
//			future.cancel(false);
//		}
//		_executor.shutdown();
	}
}
