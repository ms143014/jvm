package com.l80830.com;

import java.math.BigDecimal;

public class RouteUtils {
	public static void main(String[] args) {
		
		check(60, 200f/60, 200);
		check(60, 200f/60 + 1, 200);
		check(60, 200f/60 + 1.5f, 200);
		check(60, 200f/60 + 2, 200);
		check(60, 200f/60 * 2 + 2, 400);
		check(60, 200f/60/2, 100);
		
		check(40, 5f, 200);
		check(40, 2.5f, 100);
		check(40, 7f, 200);
		check(40, 30f, 880);
		check(40, 7 * 10 + .33f, 200 * 10 + 40 * 0.33f);
		check(40, 7 * 10 + 5f, 200 * 11);
		check(40, 7 * 10 + 5.1f, 200 * 11);
		check(40, 7 * 10 + 7.1f, 200 * 11 + 40 * 0.1f);
		
	}
	/**
	 * <p>将时间转化为路程（已经考虑了休息）<p>
	 * <ul>
	 * 	<li>例如速度60，时间3.33分钟，则路程是200</li>
	 *  <li>例如速度60，时间4分钟，则路程仍然是200，因为200的时候休息2分钟，4分钟时还没有休息完</li>
	 * </ul>
	 * @param speed 速度
	 * @param time 时间
	 * */
	public static float timeToDistance(int speed, float time) {
		float run200Time = 200f / speed;
		int period = (int)Math.floor(time / (200f / speed + 2));
		float leftTime =  time - (200f / speed + 2) * period;
		return period * 200 + speed * Math.min(leftTime, run200Time);
	}
	/**
	 * <p>两个不同速度经历time时间后相隔多远，这个用于判断是否距离超过800米</p>
	 * <p>例如计算0.33分钟后两者的距离，如果大于800米就把当前的距离计算出来，然后除以速度差，得到时间，这个时间就是过多久后两者相差800米</p>
	 * 
	 * @param speedA 慢者的速度，这两个参数的位置是可以随意调换的
	 * @param speedB 快者的速度，这两个参数的位置是可以随意调换的
	 * @param time 时间轴
	 * */
	public static float dispart(int speedA, int speedB, float time) {
		return Math.abs(timeToDistance(speedA, time) - timeToDistance(speedB, time));
	}
	/**
	 * 检查路径
	 * 200米就休息2分钟
	 * 200 / speed = 跑200米需要的时间
	 * */
	public static void check(int speed, float time, float distance) {
		System.out.printf("速度:%d，时间:%.2f，路程:%.2f\n", speed, time, distance);
		if(scale2(distance) != scale2(timeToDistance(speed, time))) {
			throw new RuntimeException("路程不对");
		}
	}
	/**
	 * 小数精度计算
	 * @param value float类型的小数
	 * @return 保留的两位小数的float
	 * */
	public static float scale2(float value) {
		BigDecimal b = new BigDecimal(value);  
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
}
