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
public class BNodeTest {
	/**
	 * 叶子节点插入元素
	 * 只测试6阶
	 * */
	@Test
	public void cloneByInsertLeaf(){
		BNode templateNode = new BNode(
				new int[] {0, 10, 20},
				new BNode[] {null, null, null},
				2);
		//最左边添加元素
		BNode node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(4);
		assert node.getKey(1) == 4;
		assert node.getKey(2) == 10;
		assert node.getKey(3) == 20;
		
		//插入已存在的元素
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(10);
		assert node.getKey(1) == 10;
		assert node.getKey(2) == 20;
		
		//插入中间的元素
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(11);
		assert node.getKey(1) == 10;
		assert node.getKey(2) == 11;
		assert node.getKey(3) == 20;
		
		//插入右边的元素
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(30);
		assert node.getKey(1) == 10;
		assert node.getKey(2) == 20;
		assert node.getKey(3) == 30;
		
		//插入分裂
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(30);
		node.cloneByInsert(40);
		node.cloneByInsert(50);
		node = node.cloneByInsert(60);
		assert node.getKey(1) == 30;
		assert node.getChild(0).getKey(1) == 10;
		assert node.getChild(0).getKey(2) == 20;
		assert node.getChild(1).getKey(1) == 40;
		assert node.getChild(1).getKey(2) == 50;
		assert node.getChild(1).getKey(3) == 60;
		
	}
	/**
	 * 内部节点的插入
	 * 只测试6阶
	 * */
	@Test
	public void cloneByInsertInternal() {
		BNode templateNode = new BNode(
				new int[] {0, 10, 20},
				new BNode[] {new BNode(5), new BNode(15), new BNode(25)},
				2);
		//最左
		BNode node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(8,(childs, index)->{
			childs[index - 1] = new BNode(5);
			childs[index] = new BNode(9);
		});
		assert node.getKey(0) == 0;
		assert node.getChild(0).getKey(1) == 5;
		assert node.getKey(1) == 8;
		assert node.getChild(1).getKey(1) == 9;
		assert node.getKey(2) == 10;
		assert node.getChild(2).getKey(1) == 15;
		assert node.getKeyNum() == 3;
		
		//中间
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(15, (childs, index)->{
			childs[index - 1] = new BNode(11);
			childs[index] = new BNode(19);
		});
		assert node.getKeyNum() == 3;
		assert node.getKey(1) == 10;
		assert node.getChild(1).getKey(1) == 11;
		assert node.getKey(2) == 15;
		assert node.getChild(2).getKey(1) == 19;
		
		//右边
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(30, (childs, index)->{
			childs[index - 1] = new BNode(29);
			childs[index] = new BNode(100);
		});
		assert node.getKeyNum() == 3;
		assert node.getKey(2) == 20;
		assert node.getChild(2).getKey(1) == 29;
		assert node.getKey(3) == 30;
		assert node.getChild(3).getKey(1) == 100;
		//分裂左
		node = SerializationUtils.clone(templateNode);
		node.cloneByInsert(30, (childs, index)->{
			childs[index - 1] = new BNode(25);
			childs[index] = new BNode(35);
		});
		node.cloneByInsert(40, (childs, index)->{
			childs[index - 1] = new BNode(35);
			childs[index] = new BNode(45);
		});
		node.cloneByInsert(50, (childs, index)->{
			childs[index - 1] = new BNode(45);
			childs[index] = new BNode(55);
		});
		node = node.cloneByInsert(8, (childs, index)->{
			childs[index - 1] = new BNode(3);
			childs[index] = new BNode(9);
		});
		assert node.getKeyNum() == 1;
		assert node.getKey(1) == 20;
		assert node.getChild(0).getChild(0).getKey(1) == 3; //左孩子
		assert node.getChild(0).getKey(1) == 8;
		assert node.getChild(0).getChild(1).getKey(1) == 9;
		assert node.getChild(1).getChild(0).getKey(1) == 25; //右孩子
		
		
		
		
		
		
	}
}
