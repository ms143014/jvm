package debugger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-24 22:07:32
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ChangeClassLoader {
	public static void t0() throws Exception {
		debugger.ChangeClassLoader.t1();
	}
	public static void t1() throws Exception {
		Class<?> cls = Thread.currentThread().getContextClassLoader().loadClass("com.jvm.source.avl.Node");
		System.out.println(cls);
		/*Field f = Class.class.getDeclaredField("classLoader");
		
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
		
		f.setAccessible(true);
		f.set(cls, null);*/
		
		
	}
}
