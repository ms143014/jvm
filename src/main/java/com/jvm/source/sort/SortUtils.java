package com.jvm.source.sort;

import java.util.Random;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-10 20:32:45
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface SortUtils {
	public static int[] initData(int size) {
		int[]data = new int[size];
		for(int i=0; i < size; i ++) {
			data[i] = new Random().nextInt(100);
		}
		return data;
	}
	public static void swap(int[] data, int i, int j) {
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp; 
	}
	public static void print(int[] data) {
		for(int i=0; i < data.length; i ++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}
	public static void print(int[] data, int index, String token) {
		for(int i=0; i < data.length; i ++) {
			System.out.print(data[i] + (index == i ? token:" "));
		}
		System.out.println();
	}
}
