package jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;

import sun.misc.Unsafe;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import com.jvm.BuyItem;
import com.jvm.ThreadUtils;
import com.jvm.BLockObject;

public class UnsafeTest {
	@Test
	public void classInfo() {
		BuyItem buyItem = new BuyItem();
		System.out.println(ClassLayout.parseInstance(buyItem).toPrintable());
		System.out.println(String.format("%x", VM.current().addressOf(BuyItem.class)));
		ThreadUtils.sleep(1000000);
	}
	/**
	 * OverStackflow测试
	 * */
	@Test
	public void testOverstackflow() {
		IntStream.range(0, 100000).forEach(i->{
			new Thread(()->{
				UnsafeTest.a(2);
			} ) .start();
		});
		ThreadUtils.sleep(3000);
	}
	@SuppressWarnings("unused")
	public static void a(long circle) {
		long a0 = 0;
		
		
		long a999 = 0;
		System.out.println("正在循环到"+circle);
		if(circle > 0) {
			a(--circle);
		}else {
			ThreadUtils.sleep(1000);
		}
	}
	@Test
	public void atomic() throws Exception {
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
		field.setAccessible(true);
		Unsafe unsafe = (Unsafe)field.get(Unsafe.class);
		printMemory(unsafe, 0x76b505c40L, 20);
		
		
	}
	@Test
	public void biasedLocking0() throws Exception {
		int a = 0x11223344;
		int b = 0x11223344;
		int c = 0x11223344;
		int d = 0x11223344;
		byte bs [] = {0x0A,0x0B,0x03C,0x0D};
		
		
		int e0 = 1;
		int f = e0+1;
		int g = f+1;
		int h = g+1;
		int i = h+1;
		
		String _s;
		String t;
		String u;
		String v;
		java.util.Map<Thread, StackTraceElement[]> ts = Thread.getAllStackTraces();  
		StackTraceElement[] ste = ts.get(Thread.currentThread()); 
		for(StackTraceElement e : ste) {
			System.out.printf("%s %x\n",e.getMethodName(), VM.current().addressOf(e));
		}
		
		System.out.println(a+""+b+""+c+""+d);
		System.out.printf("%x\n", VM.current().addressOf(Thread.currentThread()));
		
		Object object = new Object();
		System.out.printf("Object地址:%x\n", VM.current().addressOf(object));
		synchronized (object) {
			String name = ManagementFactory.getRuntimeMXBean().getName(); 
			String pid = name.split("@")[0];
			Process process = Runtime.getRuntime().exec(String.format("jstack %s", pid)); //pid
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
			String s;
			while((s=bufferedReader.readLine()) != null) {
				System.out.println(s);
				if(s.startsWith("\"main\" #")) {
					long addr = Long.parseLong(s.substring(s.lastIndexOf('[')+3, s.length()-1), 16) + 0x1430;
					Field field = Unsafe.class.getDeclaredField("theUnsafe");
					field.setAccessible(true);
					printMemory((Unsafe)field.get(Unsafe.class), addr, 20);
					
				}
			}
				
			System.out.println(process.waitFor());
			
			System.out.println(ClassLayout.parseInstance(object).toPrintable());
			Thread.sleep(10000000);
			
		}
		
	}
	void printCurrentTheadTrack(){
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		Thread thread = Thread.currentThread();
		StringBuilder msg = new StringBuilder();
	    long tid = thread.getId();
	    ThreadInfo threadInfo = bean.getThreadInfo(tid);
	    if(threadInfo != null){
	    	String lockInfo = threadInfo.getLockName() == null ? " " : ", " + threadInfo.getLockName();
		    msg.append("thread id: " + tid + ", name: " + threadInfo.getThreadName() + ", " + threadInfo.getThreadState() + lockInfo).append("\n");
		    StackTraceElement[] stackTraces = thread.getStackTrace();
		    for (StackTraceElement stackTrace: stackTraces) {
		        msg.append("\t").append(stackTrace).append("\n");
		    }
		    System.out.println(msg);
	    }
	    
	}
	/**
	 * 打印当前线程的调用堆栈
	 * 
	 */
	void printTrack(){
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		Map< Thread, StackTraceElement[] > traceMap = Thread.getAllStackTraces();
		Set< Thread > allThreads = traceMap.keySet();
		for (Thread thread: allThreads) {
		    StringBuilder msg = new StringBuilder();
		    long tid = thread.getId();
		    ThreadInfo threadInfo = bean.getThreadInfo(tid);
		    if(threadInfo == null){
		       continue;
		    }
		    String lockInfo = threadInfo.getLockName() == null ? " " : ", " + threadInfo.getLockName();
		    msg.append("thread id: " + tid + ", name: " + threadInfo.getThreadName() + ", " + threadInfo.getThreadState() + lockInfo).append("\n");
		    StackTraceElement[] stackTraces = thread.getStackTrace();
		    for (StackTraceElement stackTrace: stackTraces) {
		        msg.append("\t").append(stackTrace).append("\n");
		    }
		    System.out.println(msg);
		}
	}
	@Test
	public void biasedLocking() throws Exception {
		BLockObject oneFileldClass = new BLockObject();
		oneFileldClass.setId(0x11223344);
		
		Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe)theUnsafeField.get(Unsafe.class);
		
		long threadAddress = VM.current().addressOf(Thread.currentThread());
		printMemory(unsafe, threadAddress, 20);
		
		//eetop
		Field eetopField = Thread.class.getDeclaredField("eetop");
		eetopField.setAccessible(true);
		long eetop = (long)eetopField.get(Thread.currentThread());
		
		System.out.printf("0x%08x\n", eetop); //打印4个字节
		
		System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
		synchronized (oneFileldClass) {
			System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
		}
	}
	@Test
	public void testByte() throws Exception {
		/*byte b = 30;
		System.out.printf("%c", 32);
		
		Field unsafeFld = Unsafe.class.getDeclaredField("theUnsafe");
		unsafeFld.setAccessible(true);
		Unsafe unsafe = (Unsafe)unsafeFld.get(null);*/
		
//		Field idField = OneFileldClass.class.getDeclaredField("id");
//		System.out.println(unsafe.objectFieldOffset(idField));
//		
		BLockObject oneFileldClass = new BLockObject();
		oneFileldClass.setId(123456789);
//		OneFileldClass[] oneFileldClasses = new OneFileldClass[] {oneFileldClass, oneFileldClass, oneFileldClass, oneFileldClass };
//		
//		long baseOffset = unsafe.arrayBaseOffset(OneFileldClass[].class);
//		System.out.println("baseOffset=>"+baseOffset);
//		long addr = unsafe.getLong(oneFileldClasses, baseOffset);
//		System.out.printf("%X\n", addr);
//		
//		System.out.printf("%X\n", VM.current().addressOf(oneFileldClass));
		
		System.out.println(VM.current().details());
		System.out.println(ClassLayout.parseInstance(oneFileldClass).toPrintable());
		
		System.out.printf("oneFileldClass:%X\n", VM.current().addressOf(oneFileldClass));
		
//		Field field = Thread.class.getDeclaredField("blocker");
//		field.setAccessible(true);
//		Object blocker = field.get(Thread.currentThread());
		
		lockMethod(oneFileldClass);
//		lockMethod(oneFileldClass);
		Thread.sleep(10);
	}
	private void lockMethod(Object object) {
		synchronized (object) {
			System.err.println("----------------");
			System.out.println(ClassLayout.parseInstance(object).toPrintable());
			System.err.println("----------------");
		}
	}
	
	private static String getString(String key) throws Exception {
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName mbean = new ObjectName("com.sun.management:type=HotSpotDiagnostic");
        CompositeDataSupport val = (CompositeDataSupport) server.invoke(mbean, "getVMOption", new Object[]{key}, new String[]{"java.lang.String"});
        return val.get("value").toString();
    }
	public static class A {
	        boolean bo1, bo2;
	        byte b1, b2;
	        char c1, c2;
	        double d1, d2;
	        float f1, f2;
	        int i1, i2;
	        long l1, l2;
	        short s1, s2;
	    }
	@Test
	public void getMemeroy() throws Exception{
		
		Field unsafeFld = Unsafe.class.getDeclaredField("theUnsafe");
		unsafeFld.setAccessible(true);
		Unsafe unsafe = (Unsafe)unsafeFld.get(null);
		/*sun.misc.Unsafe@5ef04b5*/
		System.out.println(unsafe);
		
		BuyItem buyItem = (BuyItem)unsafe.allocateInstance(BuyItem.class);
		//System.out.println(buyItem);
		
		
		/*分配内存 释放内存*/
		long address = unsafe.allocateMemory(1024);
		System.out.printf("%x\n", address);
		
		System.out.printf("%x\n", unsafe.getAddress(address));
//		System.out.printf("%x\n", unsafe.getAddress(unsafe.getAddress(address)));
		
		
		printMemory(unsafe, address, 20);
		
		//设置内存数据
		unsafe.setMemory(address, 10L, (byte)0xff);
		unsafe.freeMemory(address);
		
		//unsafe.getByte(arg0)
		
		/*分配1M内存，常用于内存测试*/
		Long[]objects = new Long[100];
		IntStream.range(0, objects.length).forEach(i->{
			objects[i] = unsafe.allocateMemory(1024);
		});
		for(int i =0; i < objects.length; i++) {
			if(i > 0) {
				long offset = objects[i] - objects[i-1]; 
				System.out.printf("%04X\n", offset & 0xFFFF);
			}
		}
		
	}
	/**
	 * 1打印内存数据
	 * 1 示例
	 *  1DF15730 F0 6D EB 1D 00 00 00 00 50 51 F1 1D 00 00 00 00 .m......PQ......
		1DF15740 63 6F 6D 2F 6A 76 6D 2F 42 75 79 49 74 65 6D 07 com/jvm/BuyItem.
		1DF15750 00 04 01 00 10 6A 61 76 61 2F 6C 61 6E 67 2F 4F .....java/lang/O
		1DF15760 62 6A 65 63 74 01 00 02 69 64 01 00 10 4C 6A 61 bject...id...Lja
		1DF15770 76 61 2F 6C 61 6E 67 2F 4C 6F 6E 67 3B 01 00 09 va/lang/Long;...
		1DF15780 70 72 6F 64 75 63 74 49 64 01 00 0A 63 72 65 61 productId...crea
		1DF15790 74 65 44 61 74 65 01 00 10 4C 6A 61 76 61 2F 75 teDate...Ljava/u
		1DF157A0 74 69 6C 2F 44 61 74 65 3B 01 00 06 72 65 6D 61 til/Date;...rema
		1DF157B0 72 6B 01 00 12 4C 6A 61 76 61 2F 6C 61 6E 67 2F rk...Ljava/lang/
		1DF157C0 53 74 72 69 6E 67 3B 01 00 06 3C 69 6E 69 74 3E String;...<init>
		1DF157D0 01 00 03 28 29 56 01 00 04 43 6F 64 65 0A 00 03 ...()V...Code...
		1DF157E0 00 10 0C 00 0C 00 0D 09 00 12 00 14 07 00 13 01 ................
		1DF157F0 00 10 6A 61 76 61 2F 6C 61 6E 67 2F 53 79 73 74 ..java/lang/Syst
		1DF15800 65 6D 0C 00 15 00 16 01 00 03 6F 75 74 01 00 15 em........out...
		1DF15810 4C 6A 61 76 61 2F 69 6F 2F 50 72 69 6E 74 53 74 Ljava/io/PrintSt
		1DF15820 72 65 61 6D 3B 07 00 18 01 00 17 6A 61 76 61 2F ream;......java/
		1DF15830 6C 61 6E 67 2F 53 74 72 69 6E 67 42 75 69 6C 64 lang/StringBuild
		1DF15840 65 72 0A 00 17 00 10 0A 00 03 00 1B 0C 00 1C 00 er..............
		1DF15850 1D 01 00 08 67 65 74 43 6C 61 73 73 01 00 13 28 ....getClass...(
		1DF15860 29 4C 6A 61 76 61 2F 6C 61 6E 67 2F 43 6C 61 73 )Ljava/lang/Clas
	 * @param address 内存首地址
	 * @param line 行数，默认16个字节1行
	 * */
	public static void printMemory(Unsafe unsafe, long address, int line) {
		StringBuffer sb = new StringBuffer();
		//hex段
		StringBuffer byteHex = new StringBuffer();
		//ascii段
		StringBuffer byteStr = new StringBuffer();
		AtomicInteger byteLine = new AtomicInteger(1);
		IntStream.range(0, 16 * line).forEach(i->{
			byte data = unsafe.getByte(address+i);
			byteHex.append(String.format("%02X " , data));
			//数据转ascii
			if(data >= 32 && data <=126) { //可见字符
				byteStr.append(String.format("%c", data));
			}else { //非可见字符，用.代替
				byteStr.append('.');
			}
			//组装拼接
			if(i>0 && byteLine.get()*16-1 == i) {
				byteLine.incrementAndGet();
				//打印内存地址
				sb.append(String.format("%X ", address+i - 16 +1));
				
				sb.append(byteHex);
				sb.append(byteStr);
				byteHex.delete(0, byteHex.length());
				byteStr.delete(0, byteStr.length());
				sb.append('\n');
			}
		});
		System.out.println(sb);
	}
}
