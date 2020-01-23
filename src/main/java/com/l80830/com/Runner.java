package com.l80830.com;

public class Runner{
	
	private float elasped = 0;
	
	private boolean resting = false;
	/**
	 * 已经休息了多久时间
	 * */
	private float rested = 0;
	private float ran = 0; //已经走的路程
	
	public void run(float distance) {
		this.ran += distance;
		this.resting = this.ran % 200 == 0;
		if(this.resting) {
			this.rested = 0;
		}
	}
	/**休息多久时间*/
	public void rest(float time) {
		assert resting;
		rested += time;
		if(rested >= 2) {
			this.resting = false;
		}
	}
	public void addElasped(float time) {
		this.elasped += time;
	}
	public float getElasped() {
		return elasped;
	}
	public void setElasped(float elasped) {
		this.elasped = elasped;
	}
	public boolean isResting() {
		return resting;
	}
	public void setResting(boolean resting) {
		this.resting = resting;
	}
	public float getRested() {
		return rested;
	}
	public void setRested(float rested) {
		this.rested = rested;
	}
	public float getRan() {
		return ran;
	}
	public void setRan(float ran) {
		this.ran = ran;
	}
}
