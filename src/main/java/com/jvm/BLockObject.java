package com.jvm;

import java.util.function.Supplier;

import org.apache.commons.lang3.RandomStringUtils;

public class BLockObject {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static void main(String[] args) throws Exception {
		BLockObject bobject = new BLockObject();
		Supplier<?> getId = bobject::getId; 
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(getId);
		System.out.println("hellloworld");
		
		System.out.println(RandomStringUtils.randomNumeric(10));
		
		
	}
}
