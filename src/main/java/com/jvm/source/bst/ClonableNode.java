package com.jvm.source.bst;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-21 18:38:20
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ClonableNode {
	private int data;
	private ClonableNode leftChild;
	private ClonableNode rightChild;
	private int x = 0;
	private int y = 0;
	private int parentX = 0;
	private int parentY = 0;
	private int width = -1;
	public ClonableNode(int data) {
		super();
		this.data = data;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public ClonableNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(ClonableNode leftChild) {
		this.leftChild = leftChild;
	}
	public ClonableNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(ClonableNode rightChild) {
		this.rightChild = rightChild;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getParentX() {
		return parentX;
	}
	public void setParentX(int parentX) {
		this.parentX = parentX;
	}
	public int getParentY() {
		return parentY;
	}
	public void setParentY(int parentY) {
		this.parentY = parentY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	@Override
	public String toString() {
		return String.format("(%d, %d) w:%d", x, y, width) + " [" + data + ", " + (leftChild==null?-1:leftChild.getData()) + ", " + (rightChild==null?-1:rightChild.getData()) + "]";
	}
}
