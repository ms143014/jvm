package com.jvm.source.bplus;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-13 00:12:20
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BPlusTreeTest {
	public static void main(String[] args) throws Exception{
		Debugger.startDaemon(()->{
			BPlusTree tree = new BPlusTree();
			
			BDataNode<String, Long> plusLeafNode = new BDataNode<>(5);
			
			Debugger.set("tree", tree);
			
			System.out.println();
		}).join();
	}
}
