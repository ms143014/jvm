package com.jvm.source.bst;

import java.io.Serializable;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-19 22:38:37
 * @公司名称: 180830.com
 * @版本:V1.0
 */
class Node implements Serializable{
	private static final long serialVersionUID = 3098686719213102672L;
	public int data = 0;
	public Node lChild = null;
	public Node rChild = null;
	public Node(int data) {
		super();
		this.data = data;
	}
	@Override
	public String toString() {
		return String.format("%d[%d,%d]", this.data, this.lChild == null? -1: this.lChild.data,
				this.rChild == null? -1: this.rChild.data);
	}
}
public class BSTTest {
	public static void main(String[] args) throws Exception{
		BinarySearchTree _tree = new BinarySearchTree();
		Debugger.startDaemon(()->{
			BinarySearchTree tree = _tree;
			System.out.println("sss");
			BinarySearchTree t0 = SerializationUtils.clone(tree);
			Debugger.set("t0", t0);
//			t0.deserialize("./abcd.dat");
			
			//t0.delete(78);
			for(int i=0; i < 50; i++) {
				t0.insert(new Random().nextInt(100));
			}
			/*t0.insert(100);
			t0.insert(90);
			t0.insert(110);
			t0.insert(50);
			t0.insert(80);
			t0.insert(95);
			t0.insert(97);*/
//			new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(tree.toJson());
//			t0.travelMidOrderOnRoot();
			/*t0.toJson();
			t0.delete(95);*/
//			t0.toJson();
			System.out.println("pause");
			t0.calculateWidth();
//			t0.delete(50);
//			t0.travelMidOrderOnRoot();
			
			//DebuggerBST.t1(tree);
		}).join();
	}
}

