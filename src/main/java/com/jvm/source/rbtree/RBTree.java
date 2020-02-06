package com.jvm.source.rbtree;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import com.jvm.source.rbtree.RBNode.Color;
import com.jvm.source.rbtree.RBPrintableNode;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-04 20:23:31
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class RBTree<T extends Comparable<T>> implements Serializable{
	private static final long serialVersionUID = -4660896715578274995L;
	protected RBNode<T> root = null;
	private int size = 0;
	
	/**
	 * 插入元素
	 * */
	public void insert(@SuppressWarnings("unchecked") T...datas) {
		for(T data: datas) {
			System.out.println("插入的新值是:" + data);
			this.root = insert(this.root, data);
			this.root.color = Color.BLACK;
		}
	}
	private RBNode<T> insert(RBNode<T> node, T data) {
		if(node == null) {
			RBNode<T> newNode = new RBNode<T>(data);
			this.size++;
			return newNode;
		} else if(node.eq(data)) {
			System.out.println("键相等，更新值即可");
		} else{
			RBNode<T> child;
			if(node.lt(data)) { //右分支
				child = node.right = insert(node.right, data);
				child.parent = node;
			}else/* if(node.gt(data))*/{ //左分支
				child = node.left = insert(node.left, data);
				child.parent = node;
			}
			//如果child是黑色， 那就不用调了，因为之前已经平衡的，只有child是红色才有可能打破平衡
			RBNode<T> left = node.left;
			RBNode<T> right = node.right;
			if(left == null) {
				if(right != null && right.color == Color.RED) {
					if(right.left != null && right.left.color == Color.RED) {
						//RR-1 RL
						node = node.rotateRL();
					}else if(right.right != null && right.right.color == Color.RED){
						//RR-1 RR
						node = node.rotateRR();
					}
				}
			}else {
				//左黑右红
				if(left.color == Color.BLACK) {
					if(right != null && right.color == Color.RED) {
						//左黑右红
						if(right.left != null && right.left.color == Color.RED) { //孙左红
							//RR-1 RL
							node = node.rotateRL();
						}else if(right.right != null && right.right.color == Color.RED) { //孙右红
							//RR-1 RR
							node = node.rotateRR();
						}
					}
				}else if(left.color == Color.RED) {
					if(right == null || right.color == Color.BLACK) {
						if(left.left != null && left.left.color == Color.RED) {
							//RR-1 LL
							node = node.rotateLL();
						}else if(left.right != null && left.right.color == Color.RED){
							//RR-1 LR
							node = node.rotateLR();
						}
					}else if(right != null && right.color == Color.RED){
						if((left.left != null && left.left.color == Color.RED) ||
							(left.right != null && left.right.color == Color.RED) ||
							(right.left != null && right.left.color == Color.RED) ||
							(right.right != null && right.right.color == Color.RED)) {
							//RR-2
							node.color = Color.RED;
							left.color = Color.BLACK;
							right.color = Color.BLACK;
						}
					}
				}
			}
		}
		return node;
	}
	public RBNode<T> find(T data){
		return find(this.root, data);
	}
	private RBNode<T> find(RBNode<T> node, T data){
		if(node == null || node.eq(data)) {
			return node;
		}else {
			if(node.lt(data)) { //右分支
				return find(node.right, data);
			}else{ //左分支
				return find(node.left, data);
			}
		}
	}
	public void rendered() {
		if(this.root == null) {
			return;
		}
		RBPrintableNode<T> printableRoot = new RBPrintableNode<>(this.root);
		Queue<RBPrintableNode<T>> result = new LinkedList<RBPrintableNode<T>>();
		printStepCalcWidth(printableRoot, null);
		fixXY(printableRoot, null, result);
		try {
			DebuggerWebsocket.post(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 计算节点的x，y坐标
	 * */
	public static <M extends Comparable<M>>void fixXY(RBPrintableNode<M> node, RBPrintableNode<M> parent, Queue<RBPrintableNode<M>> result) {
		int eleSpace = 20;
		if(parent == null) {
			node.setX(0);
			node.setY(0);
		}else {
			//暂时宽度一半为x轴
			if(parent.getLeftChild() == node) {
				node.setX(parent.getX() - node.getWidth() / 2);
			}else if(parent.getRightChild() == node) {
				node.setX(parent.getX() + node.getWidth() / 2);
			}
			node.setY(parent.getY() + (int)(eleSpace * 2.5));
		}
		if(parent != null) { //为了连线
			node.setParentX(parent.getX());
			node.setParentY(parent.getY());
		}
		result.add(node);
		if(node.getLeftChild() != null) {
			fixXY((RBPrintableNode<M>)node.getLeftChild(), node, result);
		}
		if(node.getRightChild() != null) {
			fixXY((RBPrintableNode<M>)node.getRightChild(), node, result);
		}
	}
	public static <M extends Comparable<M>> void printStepCalcWidth(RBPrintableNode<M> node, RBPrintableNode<M> parent) {
		int eleWidth = 30;
		int eleSpace = 20;
		if(node.getLeftChild() != null) {
			printStepCalcWidth((RBPrintableNode<M>)node.getLeftChild(), node);
		}
		if(node.getRightChild() != null) {
			printStepCalcWidth((RBPrintableNode<M>)node.getRightChild(), node);
		}
		if(node.getLeftChild() == null) {
			if(node.getRightChild() == null) {
				node.setWidth(eleWidth + eleSpace / 2); //叶子
			}else { //只有右子树
				node.setWidth(eleSpace * 2 + ((RBPrintableNode<M>)node.getRightChild()).getWidth());
			}
		}else {
			if(node.getRightChild() == null) { //只有左子树
				node.setWidth(eleSpace * 2 + ((RBPrintableNode<M>)node.getLeftChild()).getWidth());
			}else { //中间
				node.setWidth(eleSpace + ((RBPrintableNode<M>)node.getLeftChild()).getWidth() + ((RBPrintableNode<M>)node.getRightChild()).getWidth());
			}
		}
		//System.out.println(node + "- " +(parent ==null?"空":parent));
	}
}
