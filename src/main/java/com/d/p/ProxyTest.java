package com.d.p;

import java.lang.reflect.Proxy;

public class ProxyTest {
	
	
	public static void main(String[] args) {
		AInterface a = new AImpl();
		
		AInterface proxyA = (AInterface)Proxy.newProxyInstance(AInterface.class.getClassLoader(), 
			new Class[] {AInterface.class}, 
			new AInvocationHandler(a));
		
		System.out.println(proxyA);
	}
}
