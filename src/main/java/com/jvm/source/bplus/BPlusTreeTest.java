package com.jvm.source.bplus;

import org.apache.commons.lang3.RandomStringUtils;

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
			BPlusTree<String, Integer> tree = new BPlusTree<>();
			
			Debugger.set("tree", tree);
			
			tree.insert(RandomStringUtils.randomNumeric(10), 5);
			tree.insert(RandomStringUtils.randomNumeric(10), 1);
			tree.insert(RandomStringUtils.randomNumeric(10), 6);
			tree.insert(RandomStringUtils.randomNumeric(10), 10);
			tree.insert(RandomStringUtils.randomNumeric(10), 7);
			
			System.out.println();
		}).join();
	}
}
