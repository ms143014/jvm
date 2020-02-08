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
	/**设置右孩子并绑定父子关系*/
	public void setRight(RBNode<T> right) {
		this.right = right;
		if(this.right != null) {
			this.right.parent = this;
		}
	}
	/**设置左孩子并绑定父子关系*/
	public void setLeft(RBNode<T> left) {
		this.left = left;
		if(this.left != null) {
			this.left.parent = this;
		}
	}
	
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
	public RBNode<T> brother() {
		return this.parent.left == this? this.parent.right: this.parent.left; 
	}
	public RBNode<T> rotateLL() {
		RBNode<T> left = this.left;
		this.setLeft(left.right);
		left.setRight(this);
		
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
		
		node10.setRight(node15.left);
		node15.setLeft(node10);
		
		this.setLeft(node15);;
		
		return this.rotateLL();
	}
	/**
	 * 插入和删除都适用
	 * */
	public RBNode<T> rotateRR() {
		RBNode<T> right = this.right;
		this.setRight(right.left);
		right.setLeft(this);
		
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
		
		node30.setLeft(node25.right);
		node25.setRight(node30);
		
		this.setRight(node25);
		
		return this.rotateRR();
	}
	/**
	 * L旋，不改变颜色
	 * */
	public RBNode<T> rotateLSimple() {
		RBNode<T> left = this.left;
		this.setLeft(left.right);
		left.setRight(this);
		return left;
	}
	/**
	 * R旋，不改变颜色
	 * */
	public RBNode<T> rotateRSimple() {
		RBNode<T> right = this.right;
		this.setRight(right.left);
		right.setLeft(this);
		return right;
	}
	/**
	 * 左孩子是不是红色
	 * */
	public boolean isLeftRed() {
		return this.left != null && this.left.color == Color.RED;
	}
	/**
	 * 右孩子是不是红色
	 * */
	public boolean isRightRed() {
		return this.right != null && this.right.color == Color.RED;
	}
	/**
	 * 是否是parent的左孩子
	 * */
	public boolean isOnLeft() {
		if(this.parent == null) {
			return false;
		}
		return this.parent.left == this;
	}
	/**
	 * 是否是parent的右孩子
	 * */
	public boolean isOnRight() {
		if(this.parent == null) {
			return false;
		}
		return this.parent.right == this;
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
