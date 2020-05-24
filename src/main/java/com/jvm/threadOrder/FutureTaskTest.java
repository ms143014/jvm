package com.jvm.threadOrder;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-28 12:21:16
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class FutureTaskTest {
	public static void main(String[] args) {
		FutureTask<Integer> futureTask = new FutureTask<>(()->{
			Debugger.sleep(10000);
			System.out.println("Feign调用");
			return 10;
		});
		new Thread(futureTask).start();
		
		try {
			System.out.println("future i: "+ futureTask.get(5000, TimeUnit.MILLISECONDS)); //阻塞
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
