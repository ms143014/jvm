package com.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class DynamicDebugger {
	/**
	 * 根据当前Java文件生成一个新的Java文件文件名后面跟着uuid
	 * 编译它然后注入
	 * 项目结构必须是
	 * {@code <project>/src/main/java}
	 * {@code <project>/target/classes}
	 * @param method 静态方法，
	 * 				方法名和参数必须跟定义的方法和参数名完全一样
	 * 				必须是在项目classpath下面，因为需要使用classloader
	 * 				实际上只使用了方法名
	 * @param args 静态方法使用的参数
	 * */
	public static void debugInvoke(Method method, Object...args) {
		Class<?> cls = method.getDeclaringClass();
		//Java源码路径
		String javaSourcePath = null;
		String projectPath = null;
		{
			File f = new File(cls.getResource("").getFile());
			while(!f.getName().equals("target")) {
				f = f.getParentFile();
			}
			projectPath = f.getParent();
			javaSourcePath = projectPath+"/src/main/java/"+cls.getName().replace('.', '/')+".java";
		}
		//Java源码文件
		File javaSourceFile = new File(javaSourcePath);
	    
		long lastModified = javaSourceFile.lastModified();
		String newFilePath = javaSourcePath.substring(0, javaSourcePath.length() - 5) + lastModified + ".java";
		
		/*
		 * 新的java文件，如果文件不存在，就创建它，并编译成class，如果文件存在，就不再创建
		 * */
		File javaNewSourceFile = new File(newFilePath);
		if(!javaNewSourceFile.exists()) {
			try(InputStream is = new FileInputStream(javaSourceFile);
					OutputStream out = new FileOutputStream(newFilePath)){
				byte[] fileBytes = new byte[is.available()];
				is.read(fileBytes);
				out.write(new String(fileBytes).replace(cls.getSimpleName(), cls.getSimpleName()+lastModified).getBytes());
			}catch (Exception e) {
				e.printStackTrace();
			}
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			int result = compiler.run(null, null, null, "-d", projectPath+"/target/classes", newFilePath);
			if(result == 0) {
				System.out.println("编译结果: "+newFilePath);
			}else {
				throw new RuntimeException("编译失败");
			}
		}
		//在新类上面寻找同名同方法
		try {
			Class<?> newCls = cls.getClassLoader().loadClass(cls.getName() + lastModified);
			Method newMethod = null;
			for(Method m: newCls.getDeclaredMethods()) {
				if(m.getName().equals(method.getName())) {
					newMethod = m;
					break;
				}
			}
			newMethod.invoke(null, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
