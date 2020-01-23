package com.jvm.source;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-16 12:04:49
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ArrayListTest {
	public static void main(String[] args) throws Exception {
		List<Object> list = new ArrayList<>();
		java.lang.reflect.Field elementDataField = ArrayList.class.getDeclaredField("elementData");
		elementDataField.setAccessible(true);
		java.lang.reflect.Field sizeField = ArrayList.class.getDeclaredField("size");
		sizeField.setAccessible(true);
		
		Thread t = Debugger.startDaemon(()->{
			List<Object> _list = list;
			java.lang.reflect.Field _elementDataField = elementDataField;
			java.lang.reflect.Field _sizeField = sizeField;
			System.out.println();
		});
		t.join();
	}
}
