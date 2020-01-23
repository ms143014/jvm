package com.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

public class JstatTest {
	private static boolean exit = false;
	public static void main(String[] args) {
		AtomicInteger i = new AtomicInteger();
		monitor(9948, (String line)->{
			if(i.getAndIncrement() < 3) {
				String[] datas = line.trim().split("\\s+");
				for(String data: datas) {
					System.out.print(data+"-");
				}
				System.out.println();
				return false;
			}else {
				return true;
			}
			
		}, 5000);
	}
	/**
	 * jstat监控
	 * @param pid 		进程id
	 * @param lineConsumer 一行的数据，返回boolean，false表示继续，true表示退出
	 * @param interval 	时间间隔
	 * @param times 	监控次数 0代表无限次
	 * */
	public static void monitor(int pid, Function<String, Boolean> lineConsumer, int interval) {
		try {
			Process process = Runtime.getRuntime().exec(String.format("jstat -gcutil %d %d", pid, interval));
			InputStream in = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line=null;
			while ((line=br.readLine()) != null) {
				if(lineConsumer.apply(line)) {
					break;
				}
			}
			in.close();
			process.destroy();
			System.out.println("监控已经完毕");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
