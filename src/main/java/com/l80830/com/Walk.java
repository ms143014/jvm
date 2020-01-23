package com.l80830.com;

public class Walk {
	public static void main(String[] args) {
		Runner runA = new Runner();
		while(!run(40, 60, runA, 200f/60,  false)) {
			run(40, 60, runA, 2,  true);
		}
		System.out.println("耗时: " + runA.getElasped());
		System.out.println(RouteUtils.scale2(RouteUtils.timeToDistance(40, runA.getElasped())));
		System.out.println(RouteUtils.scale2(RouteUtils.timeToDistance(60, runA.getElasped())));
	}
	/**
	 * @param restAfterRun 默认200米，每跑200米就休息
	 * @param restTime 休息时间是2分钟
	 * */
	public static float run(int speed0, int speed1, float restAfterRun, float restTime, float dispart) {
		if(speed0 < 0 || speed1 < 0) {
			throw new RuntimeException("速度必须大于0");
		}
		if(speed0 == speed1) {
			if(dispart > 0) {
				throw new RuntimeException("速度一样，无法拉开距离");
			}else {
				return 0;
			}
		}
		Runner runner = new Runner();
		if(speed0 > speed1) {
			while(!run(speed1, speed0, runner, restAfterRun/speed0, false)) {
				run(speed1, speed0, runner, restTime,  true);
			}
		}else {
			while(!run(speed0, speed1, runner, restAfterRun/speed0, false)) {
				run(speed0, speed1, runner, restTime,  true);
			}
		}
		return runner.getElasped();
	}
	/**
	 * <p>本次奔跑，里面有递归的调用，例如快者速度60跑200米需要3.33分钟，慢者再跑0.33就开始休息，那本次只计算0.33分钟，剩下的3分钟就递归计算了</p>
	 * <p>里面默认了跑200米休息2分钟，距离800米就停止计算。暂不作为参数传递了</p>
	 * 
	 * <div>只有以下两种情况会出现距离超过800米</div>
	 * <ul>
	 * 	<li>慢者正在休息，快者正在跑</li>
	 *  <li>慢者正在跑，快者也在跑</li>
	 * </ul>
	 * <div>如果快者在休息，不会出现距离超过800米</div>
	 * 
	 * @param speedA 慢者的速度
	 * @param speedB 快者的速度
	 * @param runner 中间的数据
	 * @param time 本次能用的时间
	 * @param isRestingB time时间内B是否在休息
	 *  
	 * */
	private static boolean run(int speedA, int speedB, Runner runner, float time, boolean isRestingB) {
		int speed = speedA;
		boolean rest = runner.isResting();
		float rested = runner.getRested(); //A休息时间
		if(rest) { //A在休息
			if(time > 2 - rested) { //足够休息时间， 可以休息完继续跑
				if(isRestingB) { //如果B也在休息，不用管800米距离
					runner.rest(2 - rested);
					runner.addElasped(2 - rested);
					RouteUtils.check(speedA, runner.getElasped(), runner.getRan());
					
					return run(speedA, speedB, runner, time - (2 - rested), isRestingB);
				}else {
					if(RouteUtils.dispart(speed, speedB, runner.getElasped() + (2 - rested)) >= 800) {
						//A在休息，B在跑
						//最后的时间是 是距离/B的速度  （time > 2 - rested代表，这个时间段内B是一直奔跑的，所以不用理会B是否会休息）
						float lastTime = (800 - Math.abs(RouteUtils.timeToDistance(speed, runner.getElasped()) -
								RouteUtils.timeToDistance(speedB, runner.getElasped()))) / speedB;
						runner.addElasped(lastTime);
						return true;
					}else {
						runner.rest(2 - rested);
						runner.addElasped(2 - rested);
						RouteUtils.check(speedA, runner.getElasped(), runner.getRan());
						
						return run(speedA, speedB, runner, time - (2 - rested), isRestingB);
					}
				}
			}else { //正在休息，休息时间不足，仍然休息
				if(isRestingB) { //如果B在跑步，就需要判断 2 - rested后是否达到800米
					runner.rest(time);
					runner.addElasped(time);
					return false;
				}else {
					if(RouteUtils.dispart(speed, speedB, runner.getElasped() + time) >= 800) {
						float lastTime = (800 - Math.abs(RouteUtils.timeToDistance(speed, runner.getElasped()) -
								RouteUtils.timeToDistance(speedB, runner.getElasped()))) / speedB;
						runner.addElasped(lastTime);
						return true;
					}else {
						runner.rest(time);
						runner.addElasped(time);
						return false;
					}
				}
				
			}
		}else { //A在跑
			float canRunTime = (200 - runner.getRan() % 200) / speedA;
			if(time > canRunTime) { //跑够200米 并且准备休息 ,这个是两个人都在跑步，距离只会越来越远
				float distance = speed * canRunTime;
				if(RouteUtils.dispart(speed, speedB, runner.getElasped() + canRunTime) >= 800) {
					//距离大于800米
					//最终时间计算
					float lastTime = (800 - Math.abs(RouteUtils.timeToDistance(speed, runner.getElasped()) -
							RouteUtils.timeToDistance(speedB, runner.getElasped()))) / Math.abs(speed - speedB);
					runner.run(speed * lastTime);
					runner.addElasped(lastTime);
					return true;
					
				}else {
					//距离小于800米
					runner.run(distance); //跑够了，准备休息
					runner.addElasped(canRunTime);
					RouteUtils.check(speedA, runner.getElasped(), runner.getRan());
					return run(speedA, speedB, runner, time - canRunTime/*剩余的睡眠时间*/, isRestingB);
				}
			}else { //全部时间都能用于奔跑
				float distance = speed * time;
				if(RouteUtils.dispart(speed, speedB, runner.getElasped() + time) >= 800){
					//距离大于800米
					//距离大于800米
					//最终时间计算
					float lastTime = (800 - Math.abs(RouteUtils.timeToDistance(speed, runner.getElasped()) -
							RouteUtils.timeToDistance(speedB, runner.getElasped()))) / Math.abs(speed - speedB);
					runner.run(speed * lastTime);
					runner.addElasped(lastTime);
					return true;
					
				}else {
					//距离小于800米
					runner.run(distance);
					runner.addElasped(time);
					RouteUtils.check(speedA, runner.getElasped(), runner.getRan());
					return false;
				}
			}
		}
	}
}
