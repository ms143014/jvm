package com.jvm.source.rbtree;

import java.io.FileInputStream;

import org.apache.commons.lang3.SerializationUtils;

import com.jvm.source.rbtree.RBNode.Color;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-05 19:08:07
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class RBTreeTest {
	public static void main(String[] args) throws Exception{
		Debugger.startDaemon(()->{
			RBTree<Integer> tree = new RBTree<>();
			
			tree =  SerializationUtils.deserialize(new FileInputStream("./abcd.dat"));
			Debugger.set("tree", tree);
			
			System.out.println();
		}).join();;
	}
}
