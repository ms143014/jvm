package com.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadGC {
	private static List<byte[]> escapeList = new ArrayList<>();
	public static void main(String[] args) {
		new Thread(()->{
			IntStream.range(0, 100).forEach(i->{
				escapeList.add(new byte[512 * 1024]);
				ThreadUtils.sleep(1000);
			});
		}) .start();
		/*new Thread(() ->{
			ThreadUtils.sleep(2000);
			System.gc();
		}) .start();*/
		ThreadUtils.sleep(10*1000*1000);
	}
}
