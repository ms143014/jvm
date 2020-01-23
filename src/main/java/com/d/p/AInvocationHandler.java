package com.d.p;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AInvocationHandler implements InvocationHandler{
	private AInterface a;
	public AInvocationHandler(AInterface a) {
		super();
		this.a = a;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(a, args);
	}
}
