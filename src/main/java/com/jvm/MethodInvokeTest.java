package com.jvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class MethodInvokeTest {
	public static void main(String[] args) {
				Arrays.asList(MethodInvokeTest.class.getMethods())
					.stream()
					.filter(it->it.getName().equals("hello"))
					.findFirst().ifPresent(method->{
						try {
							method.invoke(new MethodInvokeTest(), new Object[] {"a", 2});
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
					});
	}
	public void hello(String a, Integer b) {
		System.out.println(a + b);
	}
}
