package com.jvm.source.avl;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-22 22:23:09
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class Node<T extends Comparable<T>> implements Serializable {
	private static final long serialVersionUID = -6715339953242505117L;
	private T data;
	private Node<T> leftChild;
	private Node<T> rightChild;
	private int height = 1;
	public Node() {
	}
	public Node(T data) {
		super();
		this.data = data;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Node<T> getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(Node<T> leftChild) {
		this.leftChild = leftChild;
	}
	public Node<T> getRightChild() {
		return rightChild;
	}
	public void setRightChild(Node<T> rightChild) {
		this.rightChild = rightChild;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * 获取左子树高度，null则为0
	 * */
	public int getLeftChildHeight() {
		return this.leftChild == null ? 0: this.leftChild.getHeight();
	}
	/**
	 * 获取右子树高度，null则为0
	 * */
	public int getRightChildHeight() {
		return this.rightChild == null ? 0: this.rightChild.getHeight();
	}
	/**
	 * 计算平衡因子
	 * */
	public int getBalanceFactor() {
		return getLeftChildHeight() - getRightChildHeight();
	}
	/**
	 * 此节点是否平衡
	 * */
	public boolean isInBalance() {
		return Math.abs(getBalanceFactor()) < 2;
	}
	@Override
	public String toString() {
		return String.format("%s [%s, %s] %d", 
				String.valueOf(this.data),
				this.leftChild == null ? "null": this.leftChild.getData(),
						this.rightChild == null ? "null": this.rightChild.getData(),
						this.height);
	}
	
}
