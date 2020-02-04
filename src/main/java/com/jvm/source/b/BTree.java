package com.jvm.source.b;

import org.apache.commons.lang3.SerializationUtils;

import com.jvm.source.b.Node.SearchResult;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-31 17:08:46
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BTree {
	private Node root = null;
	public void setRoot(Node root) {
		this.root = root;
	}
	public Node getRoot() {
		return root;
	}
	public void rendered() throws Exception {
		if(this.root == null) {
			System.out.println("空树，不打印");
			return;
		}
		PrintableUtils.postTravel(this.root);
	}
	public void insert(int key) {
		if(root == null) {
			this.root = new Node(key);
		}else {
			this.root = insert(this.root, key);
		}
	}
	private Node insert(Node node, int key) {
		if(node == null) {
			return new Node(key);
		}else {
			Node child = null;
			int i;
			for(i = 0; i < node.getKeyNum(); i++) {
				int nodeKey = node.getKey(i + 1);
				if(key < nodeKey) {
					child = node.getChild(i); //左孩子
					break;
				}else if(key == nodeKey){
					System.out.println("元素已经存在");
					return node;
				}else { //
					//一般不用管，直接下一个
					if(i == node.getKeyNum() - 1) { //比最后一个大
						child = node.getChild(i + 1); //右孩子
					}
				}
			}
			Node newChild = insert(child, key);
			if(child != newChild) {
				return node.cloneByInsert(newChild);
			}else {
				return node;
			}
		}
	}
	public void t0() {
		SearchResult searchResult = Debugger.get("sss");
		Node node = SerializationUtils.clone(searchResult.getNode());
		if(node.isLeaf()) {
			if(node.getKeyNum() > 1) {
				int i;
				for(i = searchResult.getIndex(); i < node.getKeyNum(); i++) {
					node.setKey(i, node.getKey(i + 1));
					node.setChild(i, node.getChild(i + 1));
				}
				node.setKey(i, 0);
				node.setChild(i, null);
				node.minusKeyNum();
			}else {
				
			}
		}
		System.out.println(node);
		
	}
	public void remove(int key) {
		SearchResult searchResult = new SearchResult();
		search(this.root, key, searchResult);
		if(searchResult.isFound()) {
			Node node = searchResult.getNode();
			if(node.isLeaf()) {
				if(node.getKeyNum() > 1) {
					int i;
					for(i = searchResult.getIndex(); i < node.getKeyNum(); i++) {
						node.setKey(i, node.getKey(i + 1));
						node.setChild(i, node.getChild(i + 1));
					}
					node.setKey(i, 0);
					node.setChild(i, null);
					node.minusKeyNum();
				}else {
					
				}
			}
		}else {
			System.out.printf("元素%d不存在\n", key);
		}
	}
	/**
	 * 查找关键字将会在哪一个地方插入
	 * 0表示最左边类似string的 indexOf
	 * itemSize +1 表示最右边
	 * [-][10][20][30]
	 * insert
	 * 	5	0
	 * 	10	0 相等
	 * 	15	1
	 *  20	1 相等
	 *  25	2
	 *  30	2 相等
	 *  31	3
	 * */
	public static int searchForInsert(Node node, int key) {
		int i;
		for(i=0; i < node.getKeyNum(); i++) {
			if(key <= node.getData()[i + 1])
				break;
		}
		return i;
	}
	/**寻找叶子节点*/
	public SearchResult search(int data) {
		SearchResult searchResult = new SearchResult();
		search(this.root, data, searchResult);
		return searchResult;
	}
	private static void search(Node node, int key, SearchResult searchResult) {
		if(searchResult.isFound() || node == null) //已经找到或者没有找到都返回
			return;
		//从左到右比对 
		for(int i = 0; i < node.getKeyNum(); i++) {
			int nodeKey = node.getKey(i + 1);
			if(key < nodeKey) {
				search(node.getChild(i), key, searchResult);
			}else if(key == nodeKey) {
				searchResult.setIndex(i + 1);
				searchResult.setFound(true);
				searchResult.setNode(node);
				break;
			}else {
				if(i == node.getKeyNum() - 1) {
					search(node.getChild(i + 1), key, searchResult); //最后一个元素的右孩子
				}
			}
		}
	}
	
}
