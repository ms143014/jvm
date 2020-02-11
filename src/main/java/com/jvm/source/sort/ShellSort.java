package com.jvm.source.sort;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-10 22:16:34
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ShellSort {
	public static void main(String[] args) throws Exception {
		Debugger.startDaemon(()->{
			ShellSortX shell = (ShellSortX)Debugger.deserialize("./shell.dat");//new ShellSortX();
			Debugger.set("shell", shell);
			//shell.rendered();
			shell.sort();
			System.out.println();
			
		}).join();
		
	}
}



