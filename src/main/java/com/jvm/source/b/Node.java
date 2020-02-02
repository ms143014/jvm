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
	 * 若阶=5 最多有4个元素，5个孩子
	 * 除根节点和叶子节点，中间节点的最少元素个数 ┌阶/2┐ - 1，如果阶=5，则最小有2个元素 ，3颗子树
	 * 分裂的时候尽量靠中间 
	 * */
	public static final int MAX_NODE = 5; 
	private int [] data = new int[MAX_NODE -1];
	private Node [] childs = new Node[MAX_NODE];
	private Node parent;
	private int itemSize = 0;
	public Node() {
	}
	/**
	 * 	一个新的节点
	 * */
	public Node(int data) {
		this.data[0] = data;
		this.itemSize++;
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
	public int getItemSize() {
		return itemSize;
	}
	public void setItemSize(int itemSize) {
		this.itemSize = itemSize;
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
		return itemSize == MAX_NODE -1;
	}
	public int getItem(int index) {
		return this.data[index];
	}
	/**
	 * 	叶子节点添加元素
	 *  	不为满的情况下 
	 *  @return 返回插入位置的下标
	 * */
	public int insertItem(int data) {
		assert this.itemSize < MAX_NODE;
		int insertIntoIndex = -1; //元素插入的位置
		boolean inserted = false;
		for(int i=0; i < this.itemSize; i++) {
			if(data < this.data[i]) {
				inserted = true;
				for(int j = this.itemSize; j > i; j--) {
					this.data[j] = this.data[j - 1];
				}
				this.data[i] = data;
				insertIntoIndex = i;
			}else if(data == this.data[i]){
				return i;
			}
		}
		if(!inserted) {
			this.data[this.itemSize] = data;
			insertIntoIndex = this.itemSize; //最后一个位置
		}
		itemSize++;
		return insertIntoIndex;
	}
	/**
	 * 获取一个节点的孩子位置
	 * */
	public static Node getChildNodeAtIndex(Node node, int keyIndex, int offset) {
		keyIndex += offset;
		if(keyIndex < 0 || keyIndex > node.itemSize) {
			throw new IllegalArgumentException("索引超出范围");
		}
		return node.childs[keyIndex];
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
		return this.childs[this.itemSize];
	}
	/**设置孩子*/
	public void connectChild(int index, Node child) {
		this.childs[index] = child;
		if(child != null) {
			child.parent = this;
		}
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
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i < itemSize; i++) {
			sb.append(this.data[i] +"-");
		}
		int lstInxOfComma = sb.lastIndexOf("-");
		if(lstInxOfComma > -1) {
			sb.delete(sb.length() - "-".length(), sb.length());
		}
		return sb.toString();
	}
}
