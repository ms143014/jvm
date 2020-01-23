package com.jvm.source.bst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-20 10:44:26
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BinarySearchTree implements Serializable{
	private static final long serialVersionUID = 4096984036888628882L;
	private Node root = null;
	/**把root节点信息保存到本地*/
	public void serialize(String file) {
		if(this.root != null) {
			try {
				SerializationUtils.serialize(this.root, new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public void deserialize(String file) {
		try {
			this.root = SerializationUtils.deserialize(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**插入元素*/
	public void insert(int data) {
		Node newNode = new Node(data);
		if(root == null) {
			this.root = newNode;
			return;
		}
		Node cursor = root;
		int maxLoop = 10; //防止死循环
		while(maxLoop-->0) {
			if(cursor.data > data) {
				if(cursor.lChild == null) {
					cursor.lChild = newNode; break;
				}else {
					cursor = cursor.lChild;
				}
			}else if(cursor.data < data) {
				if(cursor.rChild == null) {
					cursor.rChild = newNode;  break;
				}else {
					cursor = cursor.rChild;
				}
			}else {
				break; //节点相等
			}
		}
	}
	/**寻找*/
	public Node find(int data) {
		Node cursor = root;
		while(cursor != null) {
			if(data < cursor.data) {
				cursor = cursor.lChild;
			}else if(data > cursor.data) {
				cursor = cursor.rChild;
			}else {
				return cursor;
			}
		}
		return null;
	}
	/**删除*/
	public void delete(int data) {
		Node cursor = root, pre = null; //保存前一个节点
		while(cursor != null) {
			if(data < cursor.data) {
				pre = cursor;
				cursor = cursor.lChild;
			}else if(data > cursor.data) {
				pre = cursor;
				cursor = cursor.rChild;
			}else {
				//删除左子树 - 只需要把父节点的左指针指向删除节点的右子树
				int cursorChilds = 0;
				if(cursor.lChild != null) 
					cursorChilds++;
				if(cursor.rChild != null) 
					cursorChilds++;
				if(cursorChilds < 2) {
					if(pre.lChild == cursor) {
						pre.lChild = cursor.rChild;
					}else if(pre.rChild == cursor){ //删除右子树 - 只需要把父节点的右指针指向到删除节点的左子树
						pre.rChild = cursor.lChild;
					}
				}else {
					System.out.println("双子树删除");
					cursor.data = deleteMin(pre, cursor.rChild);
				}
				break;
			}
		}
	}
	/**
	 * 对一颗小树里面找最小的节点
	 * */
	public int deleteMin(Node parent, Node node) {
		if(node.lChild != null) {
			return deleteMin(node, node.lChild);
		}else {
			parent.lChild = node.rChild; //删除找到的节点， 由于最小节点必定没有左子树，父子肯定是左孩子关系，所以父的左子树必定指向 删除节点的右子树
			return node.data;
		}
	}
	/**
	 * 对一颗小树里面找最小的节点
	 * */
	public Node findMinNode(Node node) {
		if(node.lChild != null) {
			return findMinNode(node.lChild);
		}else {
			return node;
		}
	}
	/**
	 * 对一颗小树里面找最大的节点
	 * */
	public Node findMaxNode(Node node) {
		if(node.rChild != null) {
			return findMaxNode(node.rChild);
		}else {
			return node;
		}
	}
	/**
	 * 获取树的深度
	 * */
	public int depth() {
		if(this.root == null) {
			return 0;
		}
		AtomicInteger depthRet = new AtomicInteger(0);
		depth(this.root, 1, depthRet);
		return depthRet.get();
	}
	private void depth(Node node, int depth, AtomicInteger depthRet) {
		if(node.lChild != null) {
			depth(node.lChild, depth + 1, depthRet);
		}
		depthRet.set(Math.max(depthRet.get(), depth));
		if(node.rChild != null) {
			depth(node.rChild, depth + 1, depthRet);
		}
	}
	public static ClonableNode copyTree(Node node) {
		ClonableNode cNode = new ClonableNode(node.data);
		if(node.lChild != null) {
			cNode.setLeftChild(copyTree(node.lChild));
		}
		if(node.rChild != null) {
			cNode.setRightChild(copyTree(node.rChild));
		}
		return cNode;
	}
	public void calculateWidth() {
		ClonableNode cRoot = copyTree(this.root);
		Queue<ClonableNode> result = new LinkedList<ClonableNode>();
		travelPost(cRoot, null);
		fixXY(cRoot, null, result);
		try {
			System.out.println(new ObjectMapper().writeValueAsString(result));;
			DebuggerWebsocket.post(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 计算节点的x，y坐标
	 * */
	public static void fixXY(ClonableNode node, ClonableNode parent, Queue<ClonableNode> result) {
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
			fixXY(node.getLeftChild(), node, result);
		}
		if(node.getRightChild() != null) {
			fixXY(node.getRightChild(), node, result);
		}
	}
	/**
	 * 计算树的宽度
	 * */
	public static void travelPost(ClonableNode node, ClonableNode parent) {
		int eleWidth = 30;
		int eleSpace = 20;
		if(node.getLeftChild() != null) {
			travelPost(node.getLeftChild(), node);
		}
		if(node.getRightChild() != null) {
			travelPost(node.getRightChild(), node);
		}
		if(node.getLeftChild() == null) {
			if(node.getRightChild() == null) {
				node.setWidth(eleWidth + eleSpace / 2); //叶子
			}else { //只有右子树
				node.setWidth(eleSpace * 2 + node.getRightChild().getWidth());
			}
		}else {
			if(node.getRightChild() == null) { //只有左子树
				node.setWidth(eleSpace * 2 + node.getLeftChild().getWidth());
			}else { //中间
				node.setWidth(eleSpace + node.getLeftChild().getWidth() + node.getRightChild().getWidth());
			}
		}
		System.out.println(node + "- " +(parent ==null?"空":parent));
	}
	public void travelMidOrderOnRoot() {
		if(this.root == null)
			return;
		travelMidOrder(this.root);
		System.out.println();
	}
	public void travelMidOrder(Node node) {
		if(node.lChild != null) travelMidOrder(node.lChild);
		System.out.print(node.data + " ");
		if(node.rChild != null) travelMidOrder(node.rChild);
	}
}