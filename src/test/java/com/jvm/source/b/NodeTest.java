package com.jvm.source.b;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-01 13:11:15
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class NodeTest {
	/**
	 * 叶子节点插入元素
	 * */
	@Test
	public void cloneByInsertLeaf(){
		Node templateNode = new Node(
				new int[] {0, 10, 20},
				new Node[] {null, null, null},
				2);
		//最左边添加元素
		Node node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(4));
		assert node.getKey(1) == 4;
		assert node.getKey(2) == 10;
		assert node.getKey(3) == 20;
		
		//插入已存在的元素
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(10));
		assert node.getKey(1) == 10;
		assert node.getKey(2) == 20;
		
		//插入中间的元素
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(11));
		assert node.getKey(1) == 10;
		assert node.getKey(2) == 11;
		assert node.getKey(3) == 20;
		
		//插入右边的元素
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(30));
		assert node.getKey(1) == 10;
		assert node.getKey(2) == 20;
		assert node.getKey(3) == 30;
		
		//插入分裂
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(30));
		node.cloneByInsert(new Node(40));
		node.cloneByInsert(new Node(50));
		node = node.cloneByInsert(new Node(60));
		assert node.getKey(1) == 30;
		assert node.getChild(0).getKey(1) == 10;
		assert node.getChild(0).getKey(2) == 20;
		assert node.getChild(1).getKey(1) == 40;
		assert node.getChild(1).getKey(2) == 50;
		assert node.getChild(1).getKey(3) == 60;
		
	}
	/**
	 * 内部节点的插入
	 * */
	@Test
	public void cloneByInsertInternal() {
		Node templateNode = new Node(
				new int[] {0, 10, 20},
				new Node[] {new Node(5), new Node(15), new Node(25)},
				2);
		//最左
		Node node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(new int[] {0, 8}, 
				new Node[] {new Node(5), new Node(9)},
				1));
		assert node.getKey(0) == 0;
		assert node.getChild(0).getKey(1) == 5;
		assert node.getKey(1) == 8;
		assert node.getChild(1).getKey(1) == 9;
		assert node.getKey(2) == 10;
		assert node.getChild(2).getKey(1) == 15;
		assert node.getKeyNum() == 3;
		
		//中间
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(new int[] {0, 15}, 
				new Node[] {new Node(11), new Node(19)},
				1));
		assert node.getKeyNum() == 3;
		assert node.getKey(1) == 10;
		assert node.getChild(1).getKey(1) == 11;
		assert node.getKey(2) == 15;
		assert node.getChild(2).getKey(1) == 19;
		
		//右边
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(new int[] {0, 30}, 
				new Node[] {new Node(29), new Node(100)},
				1));
		assert node.getKeyNum() == 3;
		assert node.getKey(2) == 20;
		assert node.getChild(2).getKey(1) == 29;
		assert node.getKey(3) == 30;
		assert node.getChild(3).getKey(1) == 100;
		//分裂左
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(new Node(new int[] {0, 30}, 
				new Node[] {new Node(25), new Node(35)},
				1));
		node.cloneByInsert(new Node(new int[] {0, 40}, 
				new Node[] {new Node(35), new Node(35)},
				1));
		node.cloneByInsert(new Node(new int[] {0, 50}, 
				new Node[] {new Node(45), new Node(55)},
				1));
		node = node.cloneByInsert(new Node(new int[] {0, 8}, 
				new Node[] {new Node(3), new Node(9)},
				1));
		assert node.getKeyNum() == 1;
		assert node.getKey(1) == 20;
		assert node.getChild(0).getChild(0).getKey(1) == 3; //左孩子
		assert node.getChild(0).getKey(1) == 8;
		assert node.getChild(0).getChild(1).getKey(1) == 9;
		assert node.getChild(1).getChild(0).getKey(1) == 25; //右孩子
		
		
		
		
		
		
	}
}
