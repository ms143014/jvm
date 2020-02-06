package com.jvm.source.rbtree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jvm.source.rbtree.RBNode.Color;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-05 18:06:38
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class RBPrintableNode<T extends Comparable<T>> {
	private T data;
	private int width;
	private int height; //深度
	private int x;
	private int y;
	private int parentX;
	private int parentY;
	private Color color;
	
	private RBPrintableNode<T> leftChild;
	private RBPrintableNode<T> rightChild;
	
	public RBPrintableNode() {
	}
	/**
	 * @param node 原始节点，必须不为null
	 * */
	public RBPrintableNode(RBNode<T> node) {
		RBPrintableNode<T> printableNode = copyFromNode(node);
		this.data = printableNode.data;
		this.height = printableNode.height;
		this.leftChild = printableNode.leftChild;
		this.rightChild = printableNode.rightChild;
		this.color = printableNode.color;
	}
	private static <M extends Comparable<M>>RBPrintableNode<M> copyFromNode(RBNode<M> node) {
		RBPrintableNode<M> printableNode = new RBPrintableNode<>();
		printableNode.setData(node.getData());
		printableNode.height = 0;
		if(node.left != null) {
			printableNode.leftChild = copyFromNode(node.getLeft());
		}
		if(node.right != null) {
			printableNode.rightChild = copyFromNode(node.getRight());
		}
		printableNode.color = node.color;
		return printableNode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
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
	@JsonIgnore
	public RBPrintableNode<T> getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(RBPrintableNode<T> leftChild) {
		this.leftChild = leftChild;
	}
	@JsonIgnore
	public RBPrintableNode<T> getRightChild() {
		return rightChild;
	}
	public void setRightChild(RBPrintableNode<T> rightChild) {
		this.rightChild = rightChild;
	}
	public Color getColor() {
		return color;
	}
	@Override
	public String toString() {
		return super.toString() + " [width=" + width + ", x=" + x + ", y=" + y + ", parentX=" + parentX + ", parentY="
				+ parentY + "]";
	}
}
