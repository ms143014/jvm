package com.jvm.source.avl;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-22 22:43:47
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class PrintableNode<T extends Comparable<T>> extends Node<T>{
	private int width;
	private int x;
	private int y;
	private int parentX;
	private int parentY;
	public PrintableNode() {
	}
	/**
	 * @param node 原始节点，必须不为null
	 * */
	public PrintableNode(Node<T> node) {
		PrintableNode<T> printableNode = copyFromNode(node);
		super.setData(printableNode.getData());
		super.setHeight(printableNode.getHeight());
		super.setLeftChild(printableNode.getLeftChild());
		super.setRightChild(printableNode.getRightChild());
	}
	private static <M extends Comparable<M>>PrintableNode<M> copyFromNode(Node<M> node) {
		PrintableNode<M> printableNode = new PrintableNode<>();
		printableNode.setData(node.getData());
		printableNode.setHeight(node.getHeight());
		if(node.getLeftChild() != null) {
			printableNode.setLeftChild(copyFromNode(node.getLeftChild()));
		}
		if(node.getRightChild() != null) {
			printableNode.setRightChild(copyFromNode(node.getRightChild()));
		}
		return printableNode;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
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
	@Override
	public String toString() {
		return super.toString() + " [width=" + width + ", x=" + x + ", y=" + y + ", parentX=" + parentX + ", parentY="
				+ parentY + "]";
	}
}
