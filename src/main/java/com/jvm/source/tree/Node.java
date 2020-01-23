package com.jvm.source.tree;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-17 16:42:46
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class Node<E> {
	private E element;
	private Node<E> parent = null;
	private Node<E> leftChild = null;
	private Node<E>rightChild = null;
	public Node() {
	}
	public Node(E element) {
		super();
		this.element = element;
	}
	public E getElement() {
		return element;
	}
	public void setElement(E element) {
		this.element = element;
	}
	public Node<E> getParent() {
		return parent;
	}
	public void setParent(Node<E> parent) {
		this.parent = parent;
	}
	public Node<E> getLeftChild() {
		return leftChild;
	}
	public Node<E> setLeftChild(Node<E> leftChild) {
		this.leftChild = leftChild;
		if(leftChild != null)
			leftChild.parent = this;
		return this;
	}
	public Node<E> getRightChild() {
		return rightChild;
	}
	public Node<E> setRightChild(Node<E> rightChild) {
		this.rightChild = rightChild;
		if(rightChild != null)
			rightChild.parent = this;
		return this;
	}
	public void setChild(Node<E> leftChild, Node<E> rightChild) {
		setLeftChild(leftChild);
		setRightChild(rightChild);
	}
	@Override
	public String toString() {
		return "Node [element=" + element + ", parent " +(parent != null)+", leftChild=" + (leftChild != null) + ", rightChild=" + (rightChild!=null) + " ]";
	}
	
}
