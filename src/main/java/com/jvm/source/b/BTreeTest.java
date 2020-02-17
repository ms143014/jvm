package com.jvm.source.b;

import java.io.FileInputStream;
import java.util.Random;

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
			int keyNums = 100 + new Random().nextInt(200);
			for(int i=0; i < keyNums; i ++) {
				tree.insert(new Random().nextInt(1000));
			}
			Debugger.serialize(tree, "./abcd.dat");
//			tree = SerializationUtils.deserialize(new FileInputStream("./abcd.dat"));
			Debugger.set("tree", tree);
//			tree.remove(548,969,537,632,332);
//			tree.remove(712); //重复863
			try {
				tree.testForRemoveAllNode();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
//			tree.rendered();
			System.out.println();
			
			//Node.cloneByInsertTest();
			
			
		}).join();
	}
}
