package com.pattern.command1;

public class AudioPlayer implements Action {

	@Override
	public void play() {
		System.out.println("播放");
	}

	@Override
	public void accelerate() {
		System.out.println("加速");
	}

	@Override
	public void decelerate() {
		System.out.println("减速");
	}

	@Override
	public void pause() {
		System.out.println("暂停");
	}

	@Override
	public void rewind() {
		System.out.println("拖动时间");
	}

	@Override
	public void stop() {
		System.out.println("停止");
	}

}
