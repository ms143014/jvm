package com.jvm.source.sort;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-11 17:59:38
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class MergeSortTest {
	public static void main(String[] args) throws Exception{
		Debugger.startDaemon(()->{
			HeapSort sort = /*(HeapSort)Debugger.deserialize("./sort.dat"); */new HeapSort();
			sort.data = new int[] {49, 38, 65, 97, 76, 13, 27, 49};
			Debugger.set("sort", sort);
			sort.rendered();
			sort.sort();
			System.out.println();
		}).join();
	}
}
