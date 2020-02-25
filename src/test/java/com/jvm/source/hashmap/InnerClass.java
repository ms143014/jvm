package com.jvm.source.hashmap;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-20 23:30:28
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class InnerClass {
	public static void main(String[] args) {
		InnerClass ic = new InnerClass();
		System.out.println(ic);
	}
	private A a = null;
	public A entryA() {
		A _a;
		return (_a = a) == null? (a = new A()) : _a;
	}
	final class A{
		
	}
}
