package com.jvm;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class StringMemoryTest {
	private static Unsafe unsafe;
	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class Abc{
		private String s = "abc";
		private int a = 1;
	}

	public static void main(String[] args) throws Exception{
		String s0 = "abcdtaobao.com";
		String s1 = "abc";
		String s2 = new String("abc");
		System.out.println(s0 + s1 + s2);

		System.out.println(s0.hashCode());
		
		
		Thread.sleep(3600*1000);

		/*
		 * abc放在常量池 s0 主线程 main方法局部变量表 -> 指向常量池abc字符串 s1 主线程 main方法局部变量表 -> 指向常量池abc字符串
		 * s2 主线程 main方法局部变量表 -> 堆
		 */
	}

	@SuppressWarnings("restriction")
	public static <T> long addressOf(String str, Class<T>clazz) throws Exception {
		String[] array = new String[] { str };

		long baseOffset = unsafe.arrayBaseOffset(String[].class);
		int addressSize = unsafe.arrayIndexScale(String[].class);
		long objectAddress;
		switch (addressSize) {
		case 4:
			objectAddress = unsafe.getInt(array, baseOffset);
			break;
		case 8:
			objectAddress = unsafe.getLong(array, baseOffset);
			break;
		default:
			throw new Error("unsupported address size: " + addressSize);
		}

		return (objectAddress);
	}
}
