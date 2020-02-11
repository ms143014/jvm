package com.jvm.source.sort;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-10 20:32:32
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class MinSort {
	public static void main(String[] args) {
		int[]data = SortUtils.initData(10);
		SortUtils.print(data);
		System.out.println("=====================");
		for(int i=0; i < data.length; i++) {
			//记录最小值的下标
			int sentinel = i;
			for(int j = i+1; j < data.length; j++) {
				if(data[sentinel] > data[j]) {
					sentinel = j;
				}
			}
			SortUtils.swap(data, i, sentinel);
			SortUtils.print(data);
		}
		
	}
}
