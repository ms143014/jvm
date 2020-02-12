package com.jvm.source.sort;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-11 17:59:38
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class SortTest {
	public static void main(String[] args) throws Exception{
		Debugger.startDaemon(()->{
			QuickSort sort = new  QuickSort(); //(QuickSort)Debugger.deserialize("./sort.dat");
			sort.data = SortUtils.initData(100);
			//Debugger.serialize(sort, "./sort.dat");
//			sort.data = new int[]{4, 21,43, 98, 54, 45 ,23, 32, 66, 86 };
//			sort.data = new int[]{4,21, 23, 32, 43, 45, 98, 54, 66, 86};
			Debugger.set("sort", sort);
			sort.rendered();
			sort.sort();
			sort.rendered();
			System.out.println();
		}).join();
	}
}
