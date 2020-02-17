package com.jvm.source.sort;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-11 17:59:23
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class MergeSort implements Serializable{
	private static final long serialVersionUID = 2881450408192979730L;
	public int[] data = null;
	public MergeSort() {
		this.data = SortUtils.initData(100);
	}
	public void rendered() {
		SortUtils.print(data);
	}
	public void sort() {
		SortUtils.print(sort(0, data.length - 1, 0));
	}
	private int[] sort(int start, int end, int level) {
		if(end - start > 1) { //2,3... 就是3个元素或以上
			System.out.printf("%d %d level: %d\n", start, end, level);
			int mid = (end - start) / 2;
			int[]mergeLeft = sort(start, start + mid, level + 1);
			int[]mergeRight = sort(start + mid + 1, end, level + 1);
			
			SortUtils.print(mergeLeft);
			SortUtils.print(mergeRight);
			
			int[]merge = new int[end - start + 1];
			int leftLength = mergeLeft.length;
			int rightLength = mergeRight.length;
			int i, j, k;
			for(i = 0, j = 0, k = 0; i < leftLength && j < rightLength; k++) {
				if(mergeLeft[i] < mergeRight[j]) {
					merge[k] = mergeLeft[i];
					i++;
				}else{
					merge[k] = mergeRight[j];
					j++;
				}
			}
			if(i < leftLength) {
				for(; i < leftLength; i++, k++) {
					merge[k] = mergeLeft[i];
				}
			}
			if(j < rightLength) {
				for(; j < rightLength; j++, k++) {
					merge[k] = mergeRight[j];
				}
			}
			return merge;
		}else if(end - start == 1){
			return new int[] {Math.min(data[start], data[end]), Math.max(data[start], data[end])};
		}else {
			return new int[] {data[start]};
		}
	}
}
