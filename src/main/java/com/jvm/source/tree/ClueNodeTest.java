package com.jvm.source.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-18 17:48:50
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ClueNodeTest {
	public static void main(String[] args) throws Exception {
		Queue<Character> queue = new LinkedList<Character>();
		for(Character c: "ABC##D##E#F##".toCharArray()) {
			queue.offer(c);
		}
		ClueNode<Character> _root =  new ClueNode<Character>('$');
		ClueNode<Character> _node = createTree(null, queue);
		_root.leftNode = _root.rightNode = _node;
		
		AtomicReference<ClueNode<Character>> preNode = new AtomicReference<ClueNode<Character>>(null);
		inOrdering(_node, preNode);
		joinRoot(_root, _node);
		
		Debugger.startDaemon(()->{
			ClueNode<Character> node = _node;
			ClueNode<Character> root = _root;
			System.out.println();
						
			/*boolean begin = false;
			while(!begin || (begin && cursor != root)) {
				if(!begin) {
					if(cursor.leftNode == root) {
						begin = true;
					}else {
						cursor = cursor.leftNode;			
					}		
				}else {
					System.out.println(cursor.data);
					cursor = cursor.rightNode;
				}
			}*/
			
			
		}).join();
	}
	private static ClueNode<Character> createTree(ClueNode<Character> parent, Queue<Character> queue) {
		if(!queue.isEmpty()) {
			if(parent == null) {
				createTree(parent = new ClueNode<>(queue.poll()), queue);
				return parent;
			}else {
				Character c = queue.poll();
				if(c != '#') {
					parent.leftNode = new ClueNode<>(c);
					createTree(parent.leftNode, queue);
				}
				c = queue.poll();
				if(c != '#') {
					parent.rightNode = new ClueNode<>(c);
					createTree(parent.rightNode, queue);
				}
			}
		}
		return null;
	}
	public static void travel(ClueNode<Character> root) {
		ClueNode<Character> cursor = root.leftNode;
		boolean start = false;
		while(cursor != root) {
			if(!start) {
				if(cursor.leftNode == root) {
					start = true;	//定位到开始点
				}else {
					cursor = cursor.leftNode;
				}
			}else{
				System.out.print(cursor.data);	//打印数据
				cursor = cursor.rightNode;	//右孩子切换
			}
		}
		System.out.println();
	}
	public static ClueNode<Character> findNode(ClueNode<Character> node, char c){
		if(node.leftNode != null) {
			ClueNode<Character> ret = findNode(node.leftNode, c);
			if(ret != null) {
				return ret;
			}
		}
		if(node.data == c) {
			return node;
		}
		if(node.rightNode != null) {
			ClueNode<Character> ret = findNode(node.rightNode, c);
			if(ret != null) {
				return ret;
			}
		}
		return null; //找不到
	}
	/**
	 *	建立线索
	 * */
	private static void inOrdering(ClueNode<Character> node, AtomicReference<ClueNode<Character>> preNode) {
		if(node.leftNode != null) {
			inOrdering(node.leftNode, preNode); //前一个节点是parent没错
		}
		ClueNode<Character> pre = preNode.get();
		if(node.leftNode == null) {	//如果左孩子是null则，左孩子指向父节点
			node.leftNode = pre;
			node.isLeftChild = false;
		}
		if(pre != null && pre.rightNode == null) {
			pre.rightNode = node;
			pre.isRightChild = false;
		}
		System.out.println(node.data + " 前一个节点  " +(pre == null?' ': pre.data));
		preNode.set(node);
		
		if(node.rightNode != null) {
			inOrdering(node.rightNode, preNode);
		}
	}
	/**
	 * 连接头尾
	 * */
	public static void joinRoot(ClueNode<Character> root, ClueNode<Character> node) {
		ClueNode<Character> cursor = node; 
		while(cursor.leftNode != null) {
			cursor = cursor.leftNode;
		}
		cursor.leftNode = root; //定位到第一个节点，然后设置一下
		cursor = node;
		while(cursor.rightNode != null) {
			cursor = cursor.rightNode;
		}
		cursor.rightNode = root;
	}
}
