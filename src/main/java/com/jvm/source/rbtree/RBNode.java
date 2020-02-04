package com.jvm.source.rbtree;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-04 20:18:02
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class RBNode {
	public static enum Color{
		RED, BLACK;
	}
	protected RBNode parent;
	protected RBNode left;
	protected RBNode right; 
	protected Color color = Color.RED; //默认是红色
	public RBNode broder() {
		return this.parent.left == this? this.parent.right: this.parent.left; 
	}
	public RBNode rotateL() {
		return null;
	}
	public RBNode rotateR() {
		return null;
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
	public RBNode find() {
		return null;
	}
	/**在一颗树种删除一颗子树*/
	public void remove(RBNode node) {
		
	}
}
