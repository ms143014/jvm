package com.jvm.source.sort;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-10 23:16:58
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ShellSortX implements Serializable{
	private static final long serialVersionUID = 5463692581546580321L;
	public int[] data = null;
	public ShellSortX() {
		this.data = SortUtils.initData(100);
	}
	public void previewData(int index, int gap) {
		StringBuilder sb = new StringBuilder("[");
		StringBuilder datas = new StringBuilder("[");
		for(int i=index; i < this.data.length; i+= gap) {
			sb.append(i+"-");
			datas.append(this.data[i]+"-");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		
		datas.deleteCharAt(datas.length() - 1);
		datas.append("]");
		sb.append(datas);
		System.out.println(sb);
	}
	public void sort() {
		if(this.data != null) {
			int length = data.length;
			int gap = length;
			do {
				gap = gap/3 + 1; // 分3段
				
				for(int j=0; j < gap; j++) {
					StringBuilder sb = new StringBuilder("[");
					StringBuilder datas = new StringBuilder("[");
					for(int i = j; i < length; i+=gap) {
						sb.append(i+"-");
						datas.append(this.data[i]+"-");
					}
					sb.deleteCharAt(sb.length()-1);
					sb.append("]");
					
					datas.deleteCharAt(datas.length() - 1);
					datas.append("]");
					sb.append(datas);
					System.out.println(sb);
				}
				/*第一个元素默认不用排*/
				for(int i = gap; i < length; i++) {
					//定位插入的位置
					int j, compareElem = this.data[i];
					for(j = i; j >= gap; j -= gap) {
						if(compareElem < data[j - gap]) {
							data[j] = data[j - gap];
						}else {
							break;
						}
					}
					//插入元素
					data[j] = compareElem;
				}
				
				for(int j=0; j < gap; j++) {
					StringBuilder sb = new StringBuilder("[");
					StringBuilder datas = new StringBuilder("[");
					for(int i = j; i < length; i+=gap) {
						sb.append(i+"-");
						datas.append(this.data[i]+"-");
					}
					sb.deleteCharAt(sb.length()-1);
					sb.append("]");
					
					datas.deleteCharAt(datas.length() - 1);
					datas.append("]");
					sb.append(datas);
					System.out.println(sb);
				}
			}while(gap > 1);
		}
	}
	public void rendered() {
		SortUtils.print(this.data);
	}
}
