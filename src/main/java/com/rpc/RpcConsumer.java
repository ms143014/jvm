package com.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RpcConsumer<S> {
	//消费者代理
	@SuppressWarnings("unchecked")
	public S consumer(final Class<?> serviceClass, final InetSocketAddress address) {
		return (S)Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[] {serviceClass.getInterfaces()[0]}, new InvocationHandler() {
			/**
			 * 调用
			 * */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				try(Socket socket = new Socket()){
					socket.connect(address);
					//组建request,需要先获得OutputStream
					try(ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())){
						output.writeUTF(serviceClass.getName());
						output.writeUTF(method.getName());
						output.writeObject(method.getParameterTypes());
						output.writeObject(args);
						try(ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
								){
							return input.readObject();
						}
					}
					
				}
			}
		});
	}
	
}
