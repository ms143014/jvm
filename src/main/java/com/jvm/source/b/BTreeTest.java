package com.jvm.source.b;

import java.io.FileInputStream;

import org.apache.commons.lang3.SerializationUtils;

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
			tree = SerializationUtils.deserialize(new FileInputStream("./abcd.dat"));
			Debugger.set("tree", tree);
			/*Debugger.set("sss", tree.search(77));
			*/
			//tree.search(222).getNode().subsitution(3);
			//tree.remove(797);
//			tree.remove(210);
			tree.rendered();
			System.out.println();
			
			//Node.cloneByInsertTest();
			
			
		}).join();
	}
}
