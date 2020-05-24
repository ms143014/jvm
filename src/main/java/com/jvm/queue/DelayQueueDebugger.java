package com.jvm.queue;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-03-04 17:45:12
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class DelayQueueDebugger {
	/**
	 for(int i=0; i < 100; i++){com.jvm.queue.DelayQueueDebugger.t0(executor, -1, "立刻执行");}
	 com.jvm.queue.DelayQueueDebugger.t0(executor, 0, "任务1", 10000);
	 com.jvm.queue.DelayQueueDebugger.t0(executor, 1, "任务2", 0);
	 java.util.concurrent.ScheduledFuture<?> future = Debugger.get("future1");
	 System.out.println("任务取消结果："+future.cancel(false));
	 
	 */
	public static void t0(ScheduledThreadPoolExecutor executor, 
			long time,
			String message, 
			int sleep) throws Exception {
		ScheduledFuture<?> future = executor.schedule(()->{
			System.out.println("runnable: "+message);
			Debugger.sleep(sleep);
			System.out.println(Thread.currentThread().getName() + " Finish");
		}, time, TimeUnit.MILLISECONDS);
		
		System.out.println("future" + time);
		Debugger.set("future" + time, future);
		
		System.out.println("任务添加成功");
	}
}
