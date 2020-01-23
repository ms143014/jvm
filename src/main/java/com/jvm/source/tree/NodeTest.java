package com.jvm.source.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.SerializationUtils;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-17 16:44:20
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class NodeTest {
	public static void main(String[] args) throws Exception{
		Node<String> nodeA = new Node<>("A");
		Node<String> nodeB = new Node<>("B");
		Node<String> nodeC = new Node<>("C");
		Node<String> nodeD = new Node<>("D");
		Node<String> nodeE = new Node<>("E");
		Node<String> nodeF = new Node<>("F");
		Node<String> nodeG = new Node<>("G");
		Node<String> nodeH = new Node<>("H");
		Node<String> nodeI = new Node<>("I");
		Node<String> nodeJ = new Node<>("J");
		Node<String> nodeK = new Node<>("K");
		nodeA.setChild(nodeB, nodeC);
		nodeB.setChild(nodeD, nodeE);
		nodeD.setChild(nodeH, nodeI);
		nodeE.setChild(null, nodeJ);
		nodeC.setChild(nodeF, nodeG);
		nodeF.setChild(null, nodeK);
		StringBuilder sb = new StringBuilder();
		
		//travel前序遍历(nodeA, sb);
		//travel后序遍历(nodeA, sb);
		travel层次遍历x(new LinkedList<>(Arrays.asList(nodeA)), sb);
		
		System.out.println(sb);
		logs.stream().forEach(System.out::println);
		//debugger.SerializationTest.t1();
		
	}
	public static Stack<String> stack = new Stack<>();
	public static LinkedList<String> logs = new LinkedList<>();
	
	public static void travel层次遍历(Queue<Node<String>> nodes, StringBuilder sb) {
		if(nodes.size() > 0) {
			Queue<Node<String>> childs = new LinkedList<Node<String>>();
			nodes.stream().forEach(parent->{
				sb.append(parent.getElement());
				if(parent.getLeftChild() != null)
					childs.offer(parent.getLeftChild());
				if(parent.getRightChild() != null)
					childs.offer(parent.getRightChild());
			});
			/*收集一层的数据进行递归遍历*/
			travel层次遍历(childs, sb);
		}
	}
	public static void travel层次遍历x(Queue<Node<String>> nodes, StringBuilder sb) {
		if(nodes.size() > 0) {
			Node<String> item = nodes.poll();
			sb.append(item.getElement());
			if(item.getLeftChild() != null)
				nodes.offer(item.getLeftChild());
			if(item.getRightChild() != null)
				nodes.offer(item.getRightChild());
			travel层次遍历x(nodes, sb);
		}
	}
	public static void travel层次遍历(Node<String> root, StringBuilder sb) {
		if(root != null) {
			Queue<Node<String>> childs = new LinkedList<>();
			childs.offer(root);
			while(!childs.isEmpty()) {
				Node<String> item = childs.poll();
				
				sb.append(item.getElement());
				
				if(item.getLeftChild() != null)
					childs.offer(item.getLeftChild());
				if(item.getRightChild() != null)
					childs.offer(item.getRightChild());
			}
		}
	}
	public static void travel后序遍历(Node<String> node, StringBuilder sb) {
		String element = node.getElement();
		Node<String> leftChild = null, rightChild = null;
		stack.push(element);
		
		if((leftChild = node.getLeftChild()) != null) {
			travel后序遍历(leftChild, sb);
		}
		
		if((rightChild = node.getRightChild()) != null) {
			travel后序遍历(rightChild, sb);
		}
		sb.append(element);
		logs.add(stack.toString() + " - " + stack.pop());
	}
	/**
	 * 先访问左节点
	 * */
	public static void travel中序遍历(Node<String> node, StringBuilder sb) {
		String element = node.getElement();
		Node<String> leftChild = null, rightChild = null;
		stack.push(element);
		
		if((leftChild = node.getLeftChild()) != null) {
			travel中序遍历(leftChild, sb);
		}
		
		sb.append(element);
		logs.add(stack.toString() + " - " + stack.pop());
		
		BreakPointX.breakOn(sb.toString().equals("HDIBEJ"), true);
		if((rightChild = node.getRightChild()) != null) {
			travel中序遍历(rightChild, sb);
		}
	}
	public static void travel前序遍历(Node<String> node, StringBuilder sb) {
		String element = node.getElement();
		BreakPointX.breakOn(element.equals("H"), false);
		
		stack.push(String.format("travel(%s)", element));
		logs.add(stack.toString());
		
		sb.append(node.getElement());
		if(node.getLeftChild() != null) {
			travel前序遍历(node.getLeftChild(), sb);
			stack.pop();
		}
		if(node.getRightChild() != null) {
			travel前序遍历(node.getRightChild(), sb);
			stack.pop();
		}
	}
}
