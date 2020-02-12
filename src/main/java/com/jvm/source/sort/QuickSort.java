package com.jvm.source.sort;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-12 10:13:41
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class QuickSort implements Serializable {
	private static final long serialVersionUID = -1931509797361034978L;
	public int[] data = null;
	public void sort() {
		if(data != null && data.length > 2) {
			sort(0, data.length - 1, 1);
		}
	}
	public void checkInSort() {
		for(int i = 0; i < this.data.length - 1; i++) {
			if(data[i] > data[i+1]) {
				throw new RuntimeException("顺序不对");
			}
		}
		System.out.println("检查完毕");
	}
	/**
	 * @param start 计数位置
	 * */
	private void sort(int start, int end, int depth) {
		System.out.println(start + " " + end + "\t深度:" + depth);
		/*区间小于6则使用冒泡排序*/
		if(end - start <= 6) {
			System.out.println("小于6区间 start: " + start+ " end: " +end);
			for(int i = start; i <= end; i ++) {
				boolean swaped = false;
				for(int j = 0; j <= end - i - 1 ; j++) {
					//System.out.printf("i=%d, j=%d \n", j, j + 1);
					if(data[start + j] > data[start + j + 1]) {
						SortUtils.swap(data, start + j , start + j + 1);
						swaped = true;
					}
				}
				if(!swaped) {
					break;
				}
			}
			return;
		}
		int mid = (end + start) / 2;
		if(data[start] <= data[mid] && data[mid] <= data[end]  || (
				data[start] >= data[mid] && data[mid] >= data[end] 
				)) {
			SortUtils.swap(data, start, mid);
		}else if(data[start] <= data[end] && data[end] <= data[mid] || (
				data[start] >= data[end] && data[end] >= data[mid])) {
			SortUtils.swap(data, start, end);
		}
		
		int j = start;
		for(int i = j + 1; i <= end; i++) {
			//System.out.printf("比较%d %d\n", this.data[i], this.data[start]);
			if(this.data[i] < this.data[start]) { //小于基数
				SortUtils.swap(data, i, j + 1);
				j++; 
			}else { //大于基数
				//不用管
			}
		}
		SortUtils.swap(data, start, j);
		int leftEnd = j - 1;
		int rightStart = j + 1;
		if(start < leftEnd) { //多于1个元素排序才有意义
			for(int i = start; i <= leftEnd; i++) { //往右聚集
				if(this.data[j] == this.data[i]) {
					SortUtils.swap(data, i, leftEnd--); //循环次数减-1
					System.out.println("重复元素" + this.data[j]);
				}
			}
			if(start < leftEnd) { //二次判断
				sort(start, leftEnd, depth + 1);
			}
		}
		if(end > rightStart) { //多于1个元素排序才有意义
			for(int i = end; i >= rightStart; i++) { //往左聚集
				if(this.data[j] == this.data[i]) {
					SortUtils.swap(data, i, rightStart++); //循环次数减-1
					System.out.println("重复元素" + this.data[j]);
				}
			}
			if(end > rightStart) { //二次判断
				sort(rightStart, end, depth + 1);
			}
		}
	}
	public void rendered() {
		SortUtils.print(this.data);
	}
}
