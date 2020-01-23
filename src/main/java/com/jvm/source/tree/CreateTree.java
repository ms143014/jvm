package com.jvm.source.tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-18 11:36:42
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class CreateTree {
	public static void main(String[] args) throws Exception {
		String input = "ABDH##I##E#J##CF#K##G##";
		char[]inputChars = input.toCharArray();
		Queue<Character> inputQueue = new LinkedList<>();
		for(int i=0; i < inputChars.length; i++)
			inputQueue.offer(inputChars[i]);
		
		Node<Character> _root = linkNode(null, inputQueue);
		Debugger.startDaemon(()->{
			Node<Character> root = _root;
			System.out.println();
		}).join();
		
	}
	/**
	 * 使用前序遍历的方式创建树
	 * 遇到#就丢弃，不挂载
	 * */
	public static Node<Character> linkNode(Node<Character> parent, Queue<Character> inputQueue) {
		if(!inputQueue.isEmpty()) {
			char c = inputQueue.poll();
			if(parent == null) {
				 linkNode(parent = new Node<Character>(c), inputQueue);
				 return parent;
			}else {
				System.out.println(parent.getElement() +" left:"+ c);
				Node<Character> left, right;
				if(c != '#') {
					parent.setLeftChild(left = new Node<Character>(c));
					linkNode(left, inputQueue);
				}
				c = inputQueue.poll();
				System.out.println(parent.getElement() +" right:"+ c);
				if(c != '#') {
					parent.setRightChild(right = new Node<Character>(c));
					linkNode(right, inputQueue);
				}
				return null;
			}
		}
		return null;
	}
	public static void travel(Node<Character> node, Character c, int depth, AtomicInteger result) {
		if(result.get() <= -1) {
			if(node.getElement() == c) {
				result.set(depth);
			}
			if(node.getLeftChild() != null) {
				travel(node.getLeftChild(), c, depth + 1, result);
			}
			if(node.getRightChild() != null) {
				travel(node.getRightChild(), c, depth + 1, result);
			}
		}
	}
}
