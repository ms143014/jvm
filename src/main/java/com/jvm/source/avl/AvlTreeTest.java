package com.jvm.source.avl;

import org.apache.commons.lang3.SerializationUtils;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-22 22:26:31
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class AvlTreeTest {
	public static void main(String[] args) throws Exception{
		AvlTree<Integer> _tree = new AvlTree<>();
		
		Debugger.startDaemon(()->{
			AvlTree<Integer> tree = SerializationUtils.clone(_tree);
//			AvlTree<Integer> tree = SerializationUtils.deserialize(new java.io.FileInputStream("./abcd.dat"));
			Debugger.set("tree", tree);
			tree.insert(4);
			tree.insert(5);
			tree.insert(6);
//			tree.root = tree.leftRotate(tree.root);
			tree.insert(7);
			tree.insert(8);
//			tree.leftRotate(tree.root.getRightChild());
			System.out.println();
		}).join();
	}
}
