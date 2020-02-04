package com.jvm.source.b;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-31 17:03:34
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class Node implements Serializable{
	private static final long serialVersionUID = -7860589311607455135L;
	/**
	 * 树的阶
	 * 若阶=6 最多有5个元素，6个孩子
	 * 除根节点和叶子节点，中间节点的最少元素个数 ┌阶/2┐ - 1，如果阶=6，则最小有2个元素 ，3颗子树
	 * 分裂的时候尽量靠中间 
	 * */
	public static final int MAX_CHILDS = 6;
	/**
	 * 内部节点最少节点数， 主要是分裂的时候用到
	 * */
	public static final int MIN_ELEMENTS = (int)Math.ceil(1.0 * MAX_CHILDS / 2) - 1;
	/**
	 * 判断满
	 * */
	public static final int MAX_ELEMENTS = MAX_CHILDS - 1;
	
	private int [] data = new int[MAX_CHILDS];		//[0]不使用，从1开始，为了对齐
	private Node [] childs = new Node[MAX_CHILDS];
	private Node parent;
	private int keyNum = 0;
	public Node() {
	}
	public Node(int key, int degree) {
		this();
		this.data = new int[degree];
		this.childs = new Node[degree];
		this.data[1] = key;
		this.keyNum++;
	}
	/**
	 * 构造器为了测试
	 * */
	public Node(int[]data, Node[]childs, int keyNum) {
		for(int i=0; i < data.length; i++)
			this.data[i] = data[i];
		for(int i=0; i < childs.length; i++)
				this.childs[i] = childs[i];	
		this.keyNum = keyNum;
	}
	/**
	 * 	一个新的节点
	 * */
	public Node(int key) {
		this.data[1] = key;
		this.keyNum++;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	public Node[] getChilds() {
		return childs;
	}
	public void setChilds(Node[] childs) {
		this.childs = childs;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int addKeyNum() {
		return ++this.keyNum;
	}
	public int minusKeyNum() {
		return --this.keyNum;
	}
	public int getKeyNum() {
		return keyNum;
	}
	/**
	 * 是否是叶子，插入元素必定是定位到叶子
	 * */
	public boolean isLeaf() {
		return this.childs[0] == null;
	}
	/**
	 * 是否满
	 * */
	public boolean isFull() {
		return keyNum == MAX_ELEMENTS;
	}
	/**
	 * @param index 数组data的下标，注意0
	 * */
	public void setKey(int index, int key) {
		this.data[index] = key;
	}
	public int getKey(int index) {
		return this.data[index];
	}
	/**
	 * 获取一个节点的孩子位置
	 * */
	public static Node getChildNodeAtIndex(Node node, int keyIndex, int offset) {
		keyIndex += offset;
		if(keyIndex < 0 || keyIndex > node.keyNum) {
			throw new IllegalArgumentException("索引超出范围");
		}
		return node.childs[keyIndex];
	}
	public void setChild(int index, Node child) {
		this.childs[index] = child;
	}
	/**
	 * 获取孩子引用，根据索引
	 * @param index 当前节点的孩子位置
	 * */
	public Node getChild(int index) {
		return this.childs[index];
	}
	/**
	 * 最后一个孩子
	 * */
	public Node getLastChild() {
		return this.childs[this.keyNum];
	}
	/**设置孩子*/
	public void connectChild(int index, Node child) {
		this.childs[index] = child;
		if(child != null) {
			child.parent = this;
		}
	}
	/**
	 * 寻找key的下标，找不到则为-1
	 * */
	public int find(int key) {
		for(int i = 0; i < this.keyNum; i++) {
			int nodeKey = this.getKey(i + 1); 
			if(key == nodeKey) {
				return i + 1;
			}
		}
		return -1;
	}
	/**最靠左边的兄弟*/
	public Node getLeftSibling() {
		if(this.parent == null)
			return null;
		int i;
		for(i=0; i < this.parent.childs.length; i++) {
			if(this.parent.childs[i] == this) {
				break;
			}
		}
		return i==0 ? null:this.parent.childs[i -1];
	}
	public int height(Node node) {
		if(!node.isLeaf()) {
			return 1 + height(node.getChild(0));
		}else {
			return 1; //叶子节点算一层
		}
	}
	/**查找结果
	 * 
	 * */
	public static class SearchResult{
		private Node node;	//找到的节点
		private int index;	//找到key的index位置，注意，如果index = 0，则代表是第一个key，对应数组 data[1]
		private boolean found = false;
		public Node getNode() {
			return node;
		}
		public void setNode(Node node) {
			this.node = node;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public boolean isFound() {
			return found;
		}
		public void setFound(boolean found) {
			this.found = found;
		}
		@Override
		public String toString() {
			return "SearchResult [node=" + node + ", index=" + index + ", found=" + found + "]";
		}
	}
	/**
	 * [10, 20, 30]
	 * 5
	 * */
	public Node cloneByInsert(Node newNode) {
		Node insertTo = this; //默认节点是不满的
		boolean isFull = this.isFull();
		if(isFull) {
			insertTo = new Node(); //新建一个Node用于装载所有数据
			insertTo.data = new int[keyNum + 2]; //容量 +1
			insertTo.childs = new Node[keyNum + 2]; //容量 +1
		}
		int newKey = newNode.getKey(1);	//新节点内容
		int i;	//遍历的位置，小于比较节点的
		for(i = 0; i < keyNum; i++) {
			int key = this.data[i +1];
			if(newKey > key) {
				insertTo.data[i + 1] = this.data[i + 1];
				insertTo.childs[i] =  this.childs[i];
				if(i == keyNum - 1) {
					insertTo.childs[i + 1] =  this.childs[i + 1];
				}
			}else if(newKey < key){
				break;
			}else {
				System.out.println("元素已经存在");
				return this; //元素已经存在不做任何修改
			}
		}
		//后续复制
		for(int j = keyNum; j >= i; j--) {
			if(j == 0 ) {
				continue; //哨兵不复制
			}
			insertTo.data[j + 1] = this.data[j];
			insertTo.childs[j + 1] = this.childs[j];
		}
		//找到位置设置数值
		insertTo.data[i + 1] = newKey;
		insertTo.childs[i + 1] = newNode.childs[1];
		insertTo.childs[i] = newNode.childs[0];
		
		insertTo.keyNum = this.keyNum + 1; //元素数量  +1
		
		if(isFull) {
			return insertTo.split();
		}else {
			return insertTo;
		}
		
		
	}
	/**
	 * 节点分割，节点满了，抽取中间的为parent，
	 * 左孩子是child[0]，右孩子为child[1]
	 * */
	public Node split() {
		Node parent = new Node(this.getKey( 1 + Node.MIN_ELEMENTS));
		Node leftChild = new Node();
		Node rightChild = new Node();
		parent.childs[0] = leftChild;
		parent.childs[1] = rightChild;
		int i;
		for(i = 0; i < Node.MIN_ELEMENTS; i++) {
			leftChild.data[i + 1] = this.data[i + 1];
			leftChild.childs[i] = this.childs[i];
			if(i == Node.MIN_ELEMENTS -1) {
				leftChild.childs[i + 1] = this.childs[i + 1]; //最右边的孩子
			}
			leftChild.keyNum++;
		}
		i++;
		rightChild.childs[0] = this.childs[i];
		i++;
		for(int j=1; i < this.keyNum + 1; i++,j++) {
			rightChild.data[j] = this.data[i];
			rightChild.childs[j] = this.childs[i];
			rightChild.keyNum++;
		}
		return parent;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < keyNum + 1; i++) {
			sb.append(this.data[i] +"-");
		}
		int lstInxOfComma = sb.lastIndexOf("-");
		if(lstInxOfComma > -1) {
			sb.delete(sb.length() - "-".length(), sb.length());
		}
		return sb.toString();
	}
}
