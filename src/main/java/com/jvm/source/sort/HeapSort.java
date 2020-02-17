package com.jvm.source.sort;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-11 22:09:22
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class HeapSort implements Serializable {
	private static final long serialVersionUID = 2186521658325596211L;
	public int[] data = null;
	public HeapSort() {
		this.data = SortUtils.initData(100);
	}
	public void rendered() {
		SortUtils.print(data);
	}
	public void sort() {
		createHeap();
	}
	public void insert(int key) {
		int[]newDatas = new int[this.data.length + 1]; //扩容
		int i;
		for(i = 0; i < this.data.length; i++) {
			newDatas[i] = this.data[i];
		}
		newDatas[i] = key; //末尾插入
		while((i-1)/2 >=0) {
			if(newDatas[i] > newDatas[(i-1)/2]) {
				SortUtils.swap(newDatas, i, (i-1)/2);
				i = (i-1)/2;
			}else {
				break;
			}
		}
		this.data = newDatas;
	}
	public int pop() {
		if(this.data.length == 0) {
			throw new RuntimeException("没有元素了");
		}
		int maximum = this.data[0];
		this.data[0] = this.data[this.data.length - 1]; //末尾补到头上
		this.data = Arrays.copyOfRange(this.data, 0, this.data.length - 1);
		int index = 0;
		while(index < this.data.length) {
			int left = 2 * index + 1;
			int right = 2 * index + 2;
			if(left < this.data.length) { //有左孩子
				if(right < this.data.length) { //左右孩子都有
					if(this.data[left] > this.data[right]) {
						if(this.data[left] > this.data[index]) {
							SortUtils.swap(this.data, index, left);
							index = left;
						}else {
							break;
						}
					}else {
						if(this.data[right] > this.data[index]) {
							SortUtils.swap(this.data, index, right);
							index = right;
						}else { //不用调整退出
							break;
						}
					}
				}else { //只有左孩子
					if(this.data[left] > this.data[index]) {
						if(this.data[left] > this.data[index]) {
							SortUtils.swap(this.data, index, left);
							index = left;
						}else {
							break;
						}
					}else {
						break;
					}
				}
			}else {
				if(right < this.data.length) { //只有右孩子
					if(this.data[right] > this.data[index]) {
						SortUtils.swap(this.data, index, right);
						index = right;
					}
				}else {
					break; //都没有
				}
			}
		}
		return maximum;
	}
	private void createHeap() {
		int n = this.data.length;
		for(int i = n/2 - 1; i >=0; i--) { //从最后一个非叶子节点开始调整
			createHeap(i);
		}
	}
	private void createHeap(int index) {
		int n = this.data.length;
		int leftIndex = 2 * index + 1;
		int rightIndex = 2 * index + 2;
		if(leftIndex <= n-1) { //如果有左孩子
			if(this.data[leftIndex] > this.data[index]) { //是否要交换
				SortUtils.swap(this.data, leftIndex, index);
				createHeap(leftIndex); //递归调整儿子
			}
		}
		if(rightIndex <= n-1) { //如果有右孩子
			if(this.data[rightIndex] > this.data[index]) { //是否要交换
				SortUtils.swap(this.data, rightIndex, index);
				createHeap(rightIndex); //递归调整儿子
			}
		}
	}
}
