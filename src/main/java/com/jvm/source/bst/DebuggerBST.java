package com.jvm.source.bst;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-19 22:46:17
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class DebuggerBST{
	public static void t0() throws Exception {
		com.jvm.source.bst.DebuggerBST.t1(null);
	}
	public static void t1(BinarySearchTree tree) throws Exception {
		IntStream.range(0, 100).forEach(i->{
			tree.insert(new Random().nextInt(1000));
		});
		tree.travelMidOrderOnRoot();
	}
}