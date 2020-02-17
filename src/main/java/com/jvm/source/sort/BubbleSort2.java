package com.jvm.source.sort;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-10 17:24:19
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BubbleSort2 {
	private  static void sort(int[] data) {
		for(int i= 0; i < data.length - 1; i ++) {
			boolean swaped = false;
			for(int j = 0; j < data.length - i - 1 ; j++) {
				System.out.printf("%d %d \n", j, j+1);
				if(data[j] > data[j + 1]) {
					SortUtils.swap(data, j , j + 1);
					swaped = true;
				}
			}
			if(!swaped) { //如果没有一次交换，证明全部都符合，不再判断
				break;
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
