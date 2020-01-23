package com.thread;

/**
 *	监控JVM数据，可监控的数据可以在jconsole里看到
 *	非必须的，所以不需要随系统启动
 * */
public class JMXMonitor {
	public static Monitor instance = null;
	public static Monitor getInstance() {
		if(instance == null) {
			synchronized (JMXMonitor.class) {
				if(instance == null) {
					instance = new Monitor();
				}
			}
		}
		return instance;
	}
	public static void main(String[] args) {
		new Thread(()->{
			JMXMonitor.getInstance();
		}) .start();
		new Thread(()->{
			JMXMonitor.getInstance();
		}) .start();
	}
}
class Monitor{
	public Monitor() {
		System.out.println("创建Monitor实例");
		Debugger.sleep(1000);
	}
}
