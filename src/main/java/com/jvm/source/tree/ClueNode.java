package com.jvm.source.tree;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-18 17:42:21
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ClueNode<E> {
	public boolean isLeftChild = true; /*默认是孩子*/
	public boolean isRightChild = true; /*默认是孩子*/
	public ClueNode<E> leftNode = null;
	public ClueNode<E> rightNode = null;
	public E data = null;
	public ClueNode(E data) {
		super();
		this.data = data;
	}

	@Override
	public String toString() {
		return String.format("-----------------+\n  %c \n / \\\n%c   %c\n%s %s\n-----------------+", this.data, 
				this.leftNode == null?'#':this.leftNode.data,
				this.rightNode == null?'#':this.rightNode.data,
				this.isLeftChild?"Link": "Thread",
				this.isRightChild?"Link": "Thread");
	}

}
