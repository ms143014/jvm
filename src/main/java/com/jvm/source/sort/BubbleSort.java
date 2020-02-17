package com.jvm.source.sort;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-10 17:24:19
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BubbleSort {
	private  static void sort(int[] data) {
		for(int i=0; i < data.length - 1; i ++) {
			for(int j = i + 1; j < data.length; j++) {
				if(data[i] > data[j]) {
					SortUtils.swap(data, i , j);
				}
			}
			SortUtils.print(data);
		}
	}
	public static void main(String[] args) {
		int[]data = SortUtils.initData(10);
		System.out.println("原来的数据: ");
		SortUtils.print(data);
		System.out.println("============");
		sort(data);
		
	}
}
