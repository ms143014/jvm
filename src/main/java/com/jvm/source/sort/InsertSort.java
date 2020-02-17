package com.jvm.source.sort;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-11 12:28:13
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class InsertSort {
	public static void main(String[] args) {
		int[] datas = {12,15,9,20,6,31,24};
		//[0]号元素一开始不用排
		for(int i = 1; i < datas.length; i++) {
			int j, compareElem = datas[i]; //暂存要比较的元素，因为右移会覆盖，所以暂存起来
			for(j = i ; j > 0; j--) {
				System.out.printf("%d %s %d\n", datas[j-1] , datas[j-1]<compareElem?"<":">=", compareElem);
				if(compareElem < datas[j - 1]) {
					datas[j] = datas[j - 1]; //右移
					SortUtils.print(datas, j - 1, "▷");
				}else {
					System.out.println("找到位置啦");
					break;
				}
			}
			//退出的时候j就是插入的位置，有可能是原位不变
			datas[j] = compareElem;
			SortUtils.print(datas, i, " ┊ ");
			System.out.println("-------------------------");
		}
		SortUtils.print(datas);
	}
}
