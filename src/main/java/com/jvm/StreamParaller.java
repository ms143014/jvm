package com.jvm;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class StreamParaller {
	public static void main(String[] args) {
		Arrays.asList(
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000),
				new Random().nextInt(1000), 
				new Random().nextInt(1000), 
				new Random().nextInt(1000)).parallelStream().forEach(i->{
			System.out.println("i: " + i);
		});
		new Thread(()->{
			String s = RandomStringUtils.randomNumeric(10);
			while(true) {
				Debugger.sleep(5000);
			}
		}).start();
		
	}
}
