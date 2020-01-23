package com.jvm.source;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-16 12:04:49
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class LinkedListTest {
	public static void main(String[] args) throws Exception {
		LinkedList<Object> list = new LinkedList<>();
		
		Thread t = Debugger.startDaemon(()->{
			LinkedList<Object> _list = list;
			System.out.println();
		});
		t.join();
	}
}
