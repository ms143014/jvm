package com.jvm.source.avl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-22 22:27:07
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class AvlTree<T extends Comparable<T>> implements Serializable {
	private static final long serialVersionUID = 2604092845746514952L;
	private Node<T> root = null; 
	public AvlTree<T> clear(){
		this.root = null;
		return this;
	}
	
	/**
	 * 查找 是否包含key
	 * */
	public boolean contains(T data) {
		if(data == null) {
			return false; 
		}
		Node<T> cursor = root;
		while(cursor != null) {
			if(data.compareTo(cursor.getData()) > 0) { //大于，去右子树找
				if(cursor.getRightChild() != null) {
					cursor = cursor.getRightChild();
				}else {
					return false;
				}
			}else if(data.compareTo(cursor.getData()) < 0) { //小于，去左子树找
				if(cursor.getLeftChild() != null) {
					cursor = cursor.getLeftChild();
				}else {
					return false;
				}
			}else {
				return true;  //节点找到，不能加
			}
		}
		return false;
	}
	/**
	 * 	寻找元素，通常同于测试节点
	 * */
	public Node<T> find(T data){
		return find(this.root, data);
	}
	private Node<T> find(Node<T> node, T data){
		if(node == null) {
			return null;
		}
		int compare = node.getData().compareTo(data);
		if(compare > 0) {
			return find(node.getLeftChild(), data);
		}else if(compare < 0) {
			return find(node.getRightChild(), data);
		}else {
			return node;
		}
	}
	public void insert(@SuppressWarnings("unchecked") T...datas) {
		if(datas == null || datas.length == 0) {
			throw new RuntimeException("请提供插入的数据"); 
		}
		for(T data: datas) {
			this.root = insert(this.root, data);
		}
	}
	public void remove(T data) {
		if(data == null) {
			return;
		}
		if(this.root == null) {
			return;
		}
		remove(this.root, data, false);
	}
	
	/**
	 *  普通插入	不调整平衡 专门用于测试
	 * */
	@SuppressWarnings("unchecked")
	public void bstInsert(T...data){
		if(this.root == null) {
			this.root = new Node<T>(data[0]);
		}
		for(int i =1 ; i < data.length; i++)
			this.root = bstInsert(this.root, data[i]);
	}
	/**
	 *  普通插入	不调整平衡 专门用于测试
	 * */
	public Node<T> bstInsert(Node<T> cur, T data){
		if(cur == null) {
			//最后一个节点不用计算平衡
			return new Node<T>(data);
		}
		int compare = cur.getData().compareTo(data);
		if(compare < 0) {  //插入在右树上，仅会左旋
			cur.setRightChild(bstInsert(cur.getRightChild(), data));
		}else if(compare > 0) {//插入在右树上，仅会右旋
			cur.setLeftChild(bstInsert(cur.getLeftChild(), data));
		}
		return cur;
	}
	/**
	 * 插入元素
	 * 后置方式检查平衡
	 * */
	private Node<T> insert(Node<T> cur, T data){
		if(cur == null) {
			//最后一个节点不用计算平衡
			return new Node<T>(data);
		}
		int compare = cur.getData().compareTo(data);
		if(compare < 0) {  //插入在右树上
			cur.setRightChild(insert(cur.getRightChild(), data));
			if(!cur.isInBalance()) { //预计算
				if(cur.getRightChild().getData().compareTo(data) < 0) {
					cur = cur.rotateRR();
				}else if(cur.getRightChild().getData().compareTo(data) > 0) {
					cur = cur.rotateRL();
				}
			}
		}else if(compare > 0) {//插入在左树上
			cur.setLeftChild(insert(cur.getLeftChild(), data));
			if(!cur.isInBalance()) { //预计算
				if(cur.getLeftChild().getData().compareTo(data) > 0) {
					cur = cur.rotateLL();
				}else if(cur.getLeftChild().getData().compareTo(data) < 0) {
					cur = cur.rotateLR();
				}
			}
		}else {
			System.out.println("元素已经存在");
		}
		//调整当前元素高度
		cur.setHeight(Math.max(cur.getLeftChildHeight(), cur.getRightChildHeight()) + 1);
		return cur;
	}
	public Node<T> remove(Node<T>node, T data, boolean removed) {
		if(node == null) {
			return null;
		}
		int nodeMinuesData = node.getData().compareTo(data);
		if(nodeMinuesData > 0) { //左分支
			node.setLeftChild(remove(node.getLeftChild(), data, removed));
		}else if(nodeMinuesData < 0){ //右分支
			node.setRightChild(remove(node.getRightChild(), data, removed));
		}else {
			if(node.getLeftChild() == null) {
				if(node.getRightChild() == null) {
					return null; //删除叶子
				}else {
					//删除只有右子树的节点
					return node.getRightChild(); 
				}
			}else {
				if(node.getRightChild() == null) {
					//删除只有左子树的节点
					return node.getLeftChild(); 
				}else {
					//左子树高，就找左子树的最大节点
					//右子树高，就找右子树最小节点
					Node<T> killNode = node.getLeftChildHeight() > node.getRightChildHeight()?
							node.findMax(node.getLeftChild()): node.findMin(node.getRightChild());
					node = remove(node, killNode.getData(), false); //再次递归删除，子树会调节平衡
					node.setData(killNode.getData());
					return node;
				}
			}
		}
		if(!node.isInBalance()) {
			if(node.getLeftChildHeight() > node.getRightChildHeight()) {
				if(node.getLeftChild().getLeftChildHeight() > node.getLeftChild().getRightChildHeight()) {
					node = node.rotateLL();
				}else {
					node = node.rotateLR();
				}
			}else {
				if(node.getRightChild().getRightChildHeight() > node.getRightChild().getLeftChildHeight()) {
					node = node.rotateRR();
				}else {
					node = node.rotateRL();
				}
			}
		}
		node.setHeight(Math.max(node.getLeftChildHeight(), node.getRightChildHeight()) + 1);
		return node;
	}
	public void printStepCopy() {
		if(this.root == null) {
			return;
		}
		PrintableNode<T> printableRoot = new PrintableNode<>(this.root);
		Queue<PrintableNode<T>> result = new LinkedList<PrintableNode<T>>();
		printStepCalcWidth(printableRoot, null);
		fixXY(printableRoot, null, result);
		System.out.println(result);
		try {
			DebuggerWebsocket.post(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 计算节点的x，y坐标
	 * */
	public static <M extends Comparable<M>>void fixXY(PrintableNode<M> node, PrintableNode<M> parent, Queue<PrintableNode<M>> result) {
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
			fixXY((PrintableNode<M>)node.getLeftChild(), node, result);
		}
		if(node.getRightChild() != null) {
			fixXY((PrintableNode<M>)node.getRightChild(), node, result);
		}
	}
	public static <M extends Comparable<M>> void printStepCalcWidth(PrintableNode<M> node, PrintableNode<M> parent) {
		int eleWidth = 30;
		int eleSpace = 20;
		if(node.getLeftChild() != null) {
			printStepCalcWidth((PrintableNode<M>)node.getLeftChild(), node);
		}
		if(node.getRightChild() != null) {
			printStepCalcWidth((PrintableNode<M>)node.getRightChild(), node);
		}
		if(node.getLeftChild() == null) {
			if(node.getRightChild() == null) {
				node.setWidth(eleWidth + eleSpace / 2); //叶子
			}else { //只有右子树
				node.setWidth(eleSpace * 2 + ((PrintableNode<M>)node.getRightChild()).getWidth());
			}
		}else {
			if(node.getRightChild() == null) { //只有左子树
				node.setWidth(eleSpace * 2 + ((PrintableNode<M>)node.getLeftChild()).getWidth());
			}else { //中间
				node.setWidth(eleSpace + ((PrintableNode<M>)node.getLeftChild()).getWidth() + ((PrintableNode<M>)node.getRightChild()).getWidth());
			}
		}
		System.out.println(node + "- " +(parent ==null?"空":parent));
	}
}
