package com.jvm.source.b;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-31 17:09:34
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface BTreeTest {
	public static void main(String[] args)  throws Exception{
		BTree tree23 = new BTree();
		Debugger.startDaemon(()->{
			BTree tree = new BTree();
			Debugger.set("tree", tree);
			tree.insert(10);
			tree.insert(20);
			tree.insert(30);
			tree.insert(40);
			tree.insert(50);
			System.out.println();
			
		}).join();
	}
}
