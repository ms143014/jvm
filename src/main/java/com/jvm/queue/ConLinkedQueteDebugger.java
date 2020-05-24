package com.jvm.queue;

import java.util.List;
import java.util.Queue;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-03-12 20:32:41
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ConLinkedQueteDebugger {
	/**
	 * com.jvm.queue.ConLinkedQueteDebugger.t0(queue)
	 */
	public static void t0(List<Integer> list) throws Exception {
		new Thread(()->{
			Integer key = new Integer(10);
			list.add(key);
			key = new Integer(20);
			list.add(key);
			key = new Integer(30);
			list.add(key);
			key = new Integer(40);
			list.add(key);
			key = new Integer(50);
			list.add(key);
		}) .start();
	}

}
