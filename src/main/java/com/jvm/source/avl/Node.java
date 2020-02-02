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
		return this.height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 *	刷新一下这个父节点的高度
	 * */
	public void evalHeight() {
		this.height = Math.max(getLeftChildHeight(), getRightChildHeight()) + 1;
	}
	public void evalNodeHeight() {
		evalNodeHeight(this);
	}
	private int evalNodeHeight(Node<T> node) {
		if(node == null) {
			return 0;
		}
		return Math.max(evalNodeHeight(node.leftChild), evalNodeHeight(node.rightChild)) + 1;
	}
	/**
	 * 获取左子树高度，null则为0
	 * */
	public int getLeftChildHeight() {
		return this.leftChild == null ? 0: this.leftChild.height;
	}
	/**
	 * 获取右子树高度，null则为0
	 * */
	public int getRightChildHeight() {
		return this.rightChild == null ? 0: this.rightChild.height;
	}
	/**
	 * 计算平衡因子
	 * */
	public int getBalanceFactor() {
		return getLeftChildHeight() - getRightChildHeight();
	}
	/**
	 * 	此节点是否平衡
	 * */
	public boolean isInBalance() {
		return Math.abs(getBalanceFactor()) < 2;
	}
	/**
	 * 	寻找最大节点，最右边的那个才是
	 * */
	public Node<T> findMax(Node<T> node){
		if(node.getRightChild() == null) {
			return node;
		}else {
			return findMax(node.getRightChild());
		}
	}
	/**
	 * 	寻找最大节点，最右边的那个才是
	 * */
	public Node<T> findMin(Node<T> node){
		if(node.getLeftChild() == null) {
			return node;
		}else {
			return findMin(node.getLeftChild());
		}
	}
	/**
	 * 	右旋
	 * */
	public Node<T> rotateRR(){
		Node<T> rightChild = this.rightChild;
		this.rightChild = rightChild.leftChild;
		rightChild.leftChild = this;
		
		rightChild.height = Math.max(rightChild.getLeftChildHeight(), rightChild.getRightChildHeight()) + 1;
		this.height = Math.max(getLeftChildHeight(), getRightChildHeight()) + 1;
		
		return rightChild; //返回新的节点
	}
	/**
	 * 	左旋
	 * */
	public Node<T> rotateLL(){
		Node<T> leftChild = this.leftChild;
		this.leftChild = leftChild.rightChild;
		leftChild.rightChild = this;
		
		leftChild.height = Math.max(leftChild.getLeftChildHeight(), leftChild.getRightChildHeight()) + 1;
		this.height = Math.max(getLeftChildHeight(), getRightChildHeight()) + 1;
		
		return leftChild;
	}
	public Node<T> rotateRL(){
		this.rightChild = this.rightChild.rotateLL();
		return this.rotateRR();
	}
	/**
	 * 	左右旋
	 * */
	public Node<T> rotateLR(){
		this.leftChild = this.leftChild.rotateRR();
		return this.rotateLL();
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
