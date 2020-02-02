package com.jvm.source.sort;

import java.util.Random;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-31 18:13:11
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class InsertOrder {
	public static int size = 0;
	public static int data[] = new int[1024];
	public static void insert(int num) {
		boolean inserted = false;
		for(int i = 0; i < size; i++) {
			if(num < data[i]) {
				inserted = true;
				for(int j = size; j> i; j--) { //后移从后开始
					data[j] = data[j - 1]; 
				}
				data[i] = num;
				break;
			}else if(num == data[i]) { //已经存在
				return;
			}
		}
		if(!inserted) { //最大
			data[size] = num; 
		}
		size++;
	}
	public static void print() {
		for(int i=0; i < size; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		for(int i=0; i < 100; i++) {
			insert(1 + new Random().nextInt(1000));
		}
		/*insert(8);
		insert(10);
		insert(13);
		insert(15);
		insert(5);*/
		System.out.println();
	}
}
