package com.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RpcProvider {
	private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	public static void pubilsh(String host, int port) throws IOException{
		try(ServerSocket serverSocket = new ServerSocket()){
			serverSocket.bind(new InetSocketAddress(host, port));
			while(true) {
				/*client的关闭操作
				 * 交由客户端*/
				Socket client = serverSocket.accept();
				long currentTimeMillis = System.currentTimeMillis();
				System.out.println("request:"+currentTimeMillis);
				executor.execute(new RpcProviderTask(client)); //建立连接
				System.out.println("response:"+currentTimeMillis);
			}
		}
	}
}
class RpcProviderTask implements Runnable{
	private Socket client;
	public RpcProviderTask(Socket client) {
		super();
		this.client = client;
	}
	@Override
	public void run() {
		try(ObjectInputStream input = new ObjectInputStream(client.getInputStream())){
			String serviceImplClassName = input.readUTF();
			String methodName = input.readUTF();
			Class<?>[]parameterTypes = (Class<?>[])input.readObject();
			Object[]arguments = (Object[])input.readObject();
			
			Class<?> serviceImplClass = Class.forName(serviceImplClassName);
			Method method = serviceImplClass.getMethod(methodName, parameterTypes);
			Object result = method.invoke(serviceImplClass.newInstance(), arguments);
			try(ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream())){
				output.writeObject(result);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
}
