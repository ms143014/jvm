package com.jvm.source.b;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-31 17:08:46
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BTree {
	private Node root = null;
	public Node getRoot() {
		return root;
	}
	public void insert(int data) {
		if(root == null) {
			this.root = new Node(data);
		}else {
			Node leaf = find(this.root, data); //先找到叶子
			insert(leaf, data);
		}
	}
	private Node insert(Node sourceNode, int data) {
		if(sourceNode.isFull()) {
			int findIndex = -1;
			for(int i=0; i< sourceNode.getItemSize(); i++, findIndex++) {
				if(data < sourceNode.getItem(i)) {
					break;
				}else if(data == sourceNode.getItem(i)){
					return null;
				}
			}
			if(findIndex == -1) { //最左
				Node left = new Node(data);
				Node parent = new Node(sourceNode.getItem(0)); //后截着的一个元素
				Node right = new Node();
				for(int i=1; i< sourceNode.getItemSize(); i++) { //下一个开始后面的所有
					right.insertItem(sourceNode.getItem(i));
				}
				Node leafParent = sourceNode.getParent();
				if(leafParent == null) {
					leafParent = parent;
					leafParent.connectChild(0, left);
					leafParent.connectChild(1, right);
					this.root = leafParent;
				}else {
					
				}
			}else if(findIndex == sourceNode.getItemSize() - 1) { //最右
				Node left = new Node();
				for(int i=0; i< sourceNode.getItemSize() - 1; i++) { //下一个开始后面的所有
					left.insertItem(sourceNode.getItem(i));
				}
				Node parent = new Node(sourceNode.getItem(sourceNode.getItemSize() -1)); //最后一个为中
				Node right = new Node(data);
				Node leafParent = sourceNode.getParent();
				if(leafParent == null) {
					leafParent = parent;
					this.root = leafParent;
					leafParent.connectChild(0, left);
					leafParent.connectChild(1, right);
					this.root = leafParent;
				}else {
					int upToParentIndex = leafParent.insertItem(sourceNode.getItem(sourceNode.getItemSize() -1));
					leafParent.connectChild(upToParentIndex, left);
					leafParent.connectChild(upToParentIndex + 1, right);
				}
				
			}
			System.out.println("findIndex: " + findIndex);
		}else {
			sourceNode.insertItem(data);
			System.out.println("...");
		}
		return null;
	}
	/**寻找叶子节点*/
	public Node find(int data) {
		return find(this.root, data);
	}
	private Node find(Node node, int data) {
		if(node.isLeaf() ) {
			return node;
		}
		//从左到右比对 
		for(int i=0; i < node.getItemSize(); i++) {
			if(data < node.getItem(i)) {
				return find(node.getChild(i), data);
			}
		}
		return find(node.getLastChild(), data); //比所有都大，最后一个节点
	}
	
}
