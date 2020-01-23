package com.jvm;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class DynamicClsbf9d05b0c07e40a8bc6c7b4a410f047d {
	
	public static void test(String i, String s) {
		System.out.println("这次算是可以乱来了吧");
		IntStream.range(0, 12).forEach(n->{
			System.out.println("n:"+n);
		});
		new Thread(()->{
			System.out.println("线程也可以");
			Debugger.sleep(10000);
		}) .start();
	}
	public static void debugInvoke(Object...args) {
		Class<DynamicClsbf9d05b0c07e40a8bc6c7b4a410f047d>dynamicCls = DynamicClsbf9d05b0c07e40a8bc6c7b4a410f047d.class;
		//Java源码路径
		String javaSourcePath = null;
		String projectPath = null;
		{
			File f = new File(dynamicCls.getResource("").getFile());
			while(!f.getName().equals("target")) {
				f = f.getParentFile();
			}
			projectPath = f.getParent();
			javaSourcePath = projectPath+"/src/main/java/"+dynamicCls.getName().replace('.', '/')+".java";
		}
		//Java源码文件
		File javaSourceFile = new File(javaSourcePath);
	    
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String newFilePath = javaSourcePath.substring(0, javaSourcePath.length() - 5) + uuid + ".java";
		
		try(InputStream is = new FileInputStream(javaSourceFile);
				OutputStream out = new FileOutputStream(newFilePath)){
			byte[] fileBytes = new byte[is.available()];
			is.read(fileBytes);
			out.write(new String(fileBytes).replace("DynamicClsbf9d05b0c07e40a8bc6c7b4a410f047d", "DynamicClsbf9d05b0c07e40a8bc6c7b4a410f047d"+uuid).getBytes());
		}catch (Exception e) {
			e.printStackTrace();
		}
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null, null, null, "-d", projectPath+"/target/classes", newFilePath);
		System.out.println(result);
		
		try {
			Class<?> cls = dynamicCls.getClassLoader().loadClass(dynamicCls.getName() + uuid);
			Method testMethod = null;
			for(Method method: cls.getDeclaredMethods()) {
				if(method.getName().equals("test")) {
					testMethod = method;
					break;
				}
			}
			testMethod.invoke(null, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
