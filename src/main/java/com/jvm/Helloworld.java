package com.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


public class Helloworld implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 包装为Java文件
	 * */
	private static class SrcJavaFileObject extends SimpleJavaFileObject{
		private String code;
		protected SrcJavaFileObject(String name, String code) {
			//string:///com/loongdesign/fuck/Hell.java
			super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			this.code = code;
		}
		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
			return code;
		}
	}
	public static void main(String[] args) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager compileJavaManager = compiler.getStandardFileManager(
				/*DiagnosticListener<JavaFileObject>*/null, 
				Locale.CHINESE, 
				Charset.forName("UTF-8"));
		
		String packageName = "com.loongdesign.dynamic";
		String className = "C" + UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		String sourceCode = new StringBuffer()
				.append("package "+packageName+";")
				.append("public class "+className+"{ ")
				.append("	public void test(){ ")
				.append("		System.out.println(\"1234567890\"); ")
				.append("	} ")
				.append("}")
				.toString();
				
		SrcJavaFileObject sourceFile = new SrcJavaFileObject(packageName+"."+className, sourceCode) ;
		Iterable<JavaFileObject> javaSource = Arrays.asList(sourceFile);
		
		String flag = "-d";
        String outDir = null;
        try {
            File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
            //class文件输出目录
            outDir = classPath.getAbsolutePath() + File.separator;
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        //编译参数-d D:/nanaz_wk/jvm/target/classes/
        Iterable<String> compileOptions = Arrays.asList(flag, outDir);
        //编译任务
		JavaCompiler.CompilationTask task = compiler.getTask(
				null, /*Writer*/
				compileJavaManager, 
				(DiagnosticListener<JavaFileObject>)null, 
				compileOptions, 
				null, /*Iterable<String>*/ 
				javaSource);
		if(task.call()) { //1.编译输出class文件 2.使用当前ApplicationClassLoader加载该class文件
			try {
				Class<?> hellCls = Class.forName(packageName+"."+className);
				Object o = hellCls.newInstance();
				hellCls.getDeclaredMethod("test", new Class[] {}).invoke(o);
			} catch (ClassNotFoundException|InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		//自定义类加载器
		ClassLoader classLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				//如果是特定的类,加载特定的class文件作为该类
				if(name.equals("com.jvm.User")) {
					try(InputStream is = new FileInputStream("D:/User.class")){
						byte[] bytes = new byte[is.available()];
						is.read(bytes);
						return defineClass(name, bytes, 0, bytes.length);
					}catch (IOException e) {
						throw new ClassNotFoundException(name + "类加载失败");
					}
				}else {
					/*使用默认的 Application ClassLoader => Extension ClassLoader => Bootstrap ClassLoader*/
					return super.loadClass(name);
				}
			}
		};
		try {
			Class<?> cls = classLoader.loadClass("com.jvm.User");
			/*class com.jvm.User*/
			System.out.println(cls);
			/*com.jvm.Helloworld$1@15db9742*/
			System.out.println(cls.getClassLoader());
			/*sun.misc.Launcher$AppClassLoader@4e0e2f2a*/
			System.out.println(User.class.getClassLoader());
			/*false*/
			System.out.println(cls.newInstance() instanceof User);
			
			Object myUser = cls.newInstance();
			
			System.out.println(myUser);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}