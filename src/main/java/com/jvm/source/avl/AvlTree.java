package com.jvm.source.avl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import com.jvm.source.bst.ClonableNode;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-22 22:27:07
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class AvlTree<T extends Comparable<T>> implements Serializable {
	private static final long serialVersionUID = 2604092845746514952L;
	public static int INSERT_SUCCESS = 1;
	public static int INSERT_ALREADY_EXISTS = 2;
	public Node<T> root = null; 
	
	/**
	 * 查找
	 * */
	public boolean contains(T data) {
		if(data == null) {
			return false; 
		}
		Node<T> cursor = root;
		while(cursor != null) {
			if(data.compareTo(cursor.getData()) > 0) {
				if(cursor.getRightChild() != null) {
					cursor = cursor.getRightChild();
				}else {
					return false;
				}
				
			}else if(data.compareTo(cursor.getData()) < 0) {
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
	 * 使用后续遍历 刷新节点的高度Height
	 * */
	private int refreshHeight(Node<T> node) {
		int leftHeight = 0;
		int rightHeight = 0;
		if(node.getLeftChild() != null) {
			leftHeight = refreshHeight(node.getLeftChild());
		}
		if(node.getRightChild() != null) {
			rightHeight = refreshHeight(node.getRightChild());
		}
		int height;
		node.setHeight(height = Math.max(leftHeight, rightHeight) + 1);
		return height;
	}
	public void insert(@SuppressWarnings("unchecked") T...datas) {
		if(datas == null || datas.length == 0) {
			throw new RuntimeException("请提供插入的数据"); 
		}
		for(T data: datas) {
			this.root = insert(this.root, data);
		}
	}
	/**
	 * 不计算高度，不旋转的元素插入， 为了构造一颗树测试
	 * */
	private Node<T> insertSimple(Node<T> cur, T data){
		if(cur == null) {
			//最后一个节点不用计算平衡
			return new Node<T>(data);
		}
		int compare = cur.getData().compareTo(data);
		if(compare < 0) {  //插入在右树上，仅会左旋
			cur.setRightChild(insert(cur.getRightChild(), data));
		}else if(compare > 0) {//插入在右树上，仅会右旋
			cur.setLeftChild(insert(cur.getLeftChild(), data));
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
		if(compare < 0) {  //插入在右树上，仅会左旋
			cur.setRightChild(insert(cur.getRightChild(), data)); //有右
			if(!cur.isInBalance()) {
				if(cur.getRightChild().getLeftChild() == null) {
					cur = rotateRR(cur);
				}else {
					cur = rotateRL(cur);
				}
			}
		}else if(compare > 0) {//插入在右树上，仅会右旋
			cur.setLeftChild(insert(cur.getLeftChild(), data));
			if(!cur.isInBalance()) {
				//if(cur.getLeftChild() == null) {
					cur = rotateLL(cur); //没有左孩子，简单的转换
				/*}else {
					cur = rotateRL(cur); //有左孩子
				}*/
			}
		}
		refreshHeight(this.root);
		return cur;
	}
	/**
	 * RR旋转
	 * <pre>
	 * x
	 *  \
	 *   y
	 *    \
	 *     z
	 *-------------
	 *   y
	 *  / \
	 * x   z
	 * </pre>
	 * @param node 父杰节点，既然是左旋，则右子树必定不为null,左子树可能为null，可能不为null
	 * @return 返回最高的右节点
	 * */
	public Node<T> rotateRR(Node<T> node) {
		Node<T> rightChild = node.getRightChild();
		node.setRightChild(null);// 必须断开，否则互指
		rightChild.setLeftChild(node);
		
		refreshHeight(this.root);
		
		return rightChild;
	}
	/**
	 * LR旋转
	 * <pre>
	 *   x
	 *  /
	 * y
	 *  \
	 *   z
	 *-------------
	 *    z
	 *   / \
	 *  y   x
	 * </pre>
	 * x > z > y，所以 z为中
	 * */
	public Node<T> rotateLR(Node<T> nodeX){
		Node<T> nodeY = nodeX.getLeftChild();
		Node<T> nodeZ = nodeY.getRightChild();
		nodeZ.setLeftChild(nodeY);
		nodeZ.setRightChild(nodeX);
		
		nodeX.setLeftChild(null);
		nodeY.setRightChild(null);
		
		return nodeZ;
	}
	/**
	 * LL旋转
	 * <pre>
	 *     x
	 *    /
	 *   y
	 *  /
	 * z
	 *-------------
	 *   y
	 *  / \
	 * z   x
	 * </pre>
	 * */
	public Node<T> rotateLL(Node<T> nodeX){
		Node<T> nodeY = nodeX.getLeftChild();
		nodeY.setRightChild(nodeX);
		
		nodeX.setLeftChild(null); //清除
		
		refreshHeight(this.root);
		return nodeY;
	}
	/**
	 * RL旋转
	 * <pre>
	 * x
	 *  \
	 *   y
	 *  /
	 * z
	 *-------------
	 *    z
	 *   / \
	 *  x   y
	 * </pre>
	 * */
	public Node<T> rotateRL(Node<T> nodeX){
		Node<T> nodeZ = nodeX.getRightChild().getLeftChild(); //选取中心
		Node<T> nodeY = nodeX.getRightChild();
		nodeZ.setRightChild(nodeY);
		nodeY.setLeftChild(null); //清除旧左孩子
		nodeX.setRightChild(null); //清除旧左孩子
		nodeZ.setLeftChild(nodeX);
		return nodeZ;
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
