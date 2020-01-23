package com.thread;

import java.util.Timer;
import java.util.TimerTask;

public class Timer0 {
	public static void main(String[] args) {
		Timer timer = new Timer("我的闹钟");
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(System.currentTimeMillis() + " 5,1");
			}
		}, 5000, 1000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(System.currentTimeMillis() + " 3,1.5");
			}
		}, 3000, 1500);
	}
}

