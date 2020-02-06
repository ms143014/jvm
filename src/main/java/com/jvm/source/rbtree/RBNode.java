package com.jvm.source.rbtree;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-04 20:18:02
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class RBNode<T extends Comparable<T>> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8100173203772427674L;
	public static enum Color{
		RED, BLACK;
	}
	private T data;
	protected RBNode<T> parent;
	protected RBNode<T> left;
	protected RBNode<T> right; 
	protected Color color = Color.RED; //默认是红色
	public RBNode(T data) {
		super();
		this.data = data;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public RBNode<T> getParent() {
		return parent;
	}
	public void setParent(RBNode<T> parent) {
		this.parent = parent;
	}
	public RBNode<T> getLeft() {
		return left;
	}
	public void setLeft(RBNode<T> left) {
		this.left = left;
	}
	public RBNode<T> getRight() {
		return right;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	///////////////////
	public boolean gt(T data) {
		return this.data.compareTo(data) > 0;
	}
	public boolean eq(T data) {
		return this.data.compareTo(data) == 0;
	}
	public boolean lt(T data) {
		return this.data.compareTo(data) < 0;
	}
	public boolean gt(RBNode<T> node) {
		return this.data.compareTo(node.data) > 0;
	}
	public boolean lt(RBNode<T> node) {
		return this.data.compareTo(node.data) < 0;
	}
	public RBNode<T> broder() {
		return this.parent.left == this? this.parent.right: this.parent.left; 
	}
	public RBNode<T> rotateLL() {
		RBNode<T> left = this.left;
		this.left = left.right;
		left.right = this;
		
		left.color = Color.BLACK;
		this.color = Color.RED;
		
		return left;
	}
	/**
	 * <pre>
	 *      20
	 *     /  \
	 *    10   NULL
	 *     \
	 *      15
	 * 或者
	 *      20
	 *     /  \
	 *    10   30
	 *     \
	 *      15     
	 * </pre>
	 * */
	public RBNode<T> rotateLR() {
		//子右处理
		RBNode<T> node10 = this.left;
		RBNode<T> node15 = node10.right;
		
		node10.right = node15.left;
		node15.left = node10;
		
		this.left = node15;
		
		return this.rotateLL();
	}
	public RBNode<T> rotateRR() {
		RBNode<T> right = this.right;
		this.right = right.left;
		right.left = this;
		
		right.color = Color.BLACK;
		this.color = Color.RED;
		
		return right;
	}
	/**
	 * <pre>
	 *      20
	 *     /  \
	 *   NULL 30   
	 *        /
	 *       25
	 * 或者
	 *      20
	 *     /  \
	 *    10  30   
	 *        /
	 *       25
	 * </pre>
	 * */
	public RBNode<T> rotateRL() {
		RBNode<T> node30 = this.right;
		RBNode<T> node25 = node30.left;
		
		node30.left = node25.right;
		node25.right = node30;
		
		this.right = node25;
		
		return this.rotateRR();
	}
	/**
	 * 修正双红
	 * */
	public void solveDoubleRed() {
	}
	/**
	 * 丢失黑色修正
	 * */
	public void solveLostBlack() {
	}
	public RBNode<T> find() {
		return null;
	}
	/**在一颗树种删除一颗子树*/
	public void remove(RBNode<T> node) {
		
	}
	/**
	 * 	获取节点的后继节点
	 * */
	public RBNode<T> successor() {
		if(this.right == null) {
			return successorOnRightNull();
		}else {
			return successor(this.right);
		}
	}
	private RBNode<T> successor(RBNode<T> node) {
		if(node.left == null) {
			return node;
		}else {
			return successor(node.left);
		}
	}
	private RBNode<T> successorOnRightNull(){
		if(this.parent == null) {
			return null;
		}else {
			if(this.parent != null &&
				this.parent.left == this) {
				return this.parent;
			}else {
				return this.parent.successorOnRightNull();
			}
		}
	}
	/**
	 * 获取节点的前继
	 * 有左儿子查找左子树最大的，如果没有
	 * */
	public RBNode<T> predecessor() {
		if(this.left == null) {
			return predecessorOnLeftNull();
		}else {
			return predecessor(this.left);
		}
		
	}
	private RBNode<T> predecessor(RBNode<T> node) {
		if(node.right == null) {
			return node;
		}else {
			return predecessor(node.right);
		}
	}
	private RBNode<T> predecessorOnLeftNull(){
		if(this.parent == null) {
			return null;
		}else {
			if(this.parent != null &&
				this.parent.right == this) {
				return this.parent;
			}else {
				return this.parent.predecessorOnLeftNull();
			}
		}
	}
	@Override
	public String toString() {
		return String.format("%s %s", this.data, this.color);
	}
}
