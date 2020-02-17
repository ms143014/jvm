package com.jvm.source.b;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.SerializationUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvm.source.b.BNode.SearchResult;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-31 17:08:46
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BTree implements Serializable{
	private static final long serialVersionUID = 2959777260160438463L;
	private BNode root = null;
	private int size = 0;
	public void setRoot(BNode root) {
		this.root = root;
	}
	public BNode getRoot() {
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
			this.root = new BNode(key);
		}else {
			this.root = insert(this.root, key);
		}
	}
	private BNode insert(BNode node, int key) {
		if(node == null) {
			return new BNode(key);
		}else {
			BNode child = null;
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
			BNode newChild = insert(child, key);
			if(child != newChild) { //分裂
				return node.cloneByInsert(newChild.getKey(1), (childs, index)->{
					childs[index] = newChild.getChild(1);
					childs[index-1] = newChild.getChild(0);
				});
			}else {
				return node;
			}
		}
	}
	/**移除批量key*/
	public List<Boolean> remove(int...keys) {
		List<Boolean> result = new ArrayList<Boolean>();
		for(int key: keys) {
			result.add(remove(this.root, key));
		}
		return result;
	}
	/**移除单个key*/
	public boolean remove(int key) {
		return remove(this.root, key);
	}
	/**
	 * 递归移除key
	 * */
	private boolean remove(BNode node, int key) {
		if(node == null) {
			return false;
		}
		AtomicInteger findndex = new AtomicInteger(-1);
		boolean find = node.find(key, findndex);
		if(find) {
			if(node.isLeaf()) { //寻找到叶子才能删
				int i;
				for(i = findndex.get(); i < node.getKeyNum(); i++) {
					node.setKey(i, node.getKey(i + 1));
					node.setChild(i, node.getChild(i + 1));
				}
				node.setKey(i, 0);
				node.setChild(i, null);
				node.decreKeyNum();
				if(node == this.root) {
					if(node.getKeyNum() == 0) {
						this.root = null;
						return true;
					}
				}
			}else { //删除中间节点
				node.subsitution(findndex.get()); //替换 找前继或者后继随便一个，本例找后继
				remove(node.getChild(findndex.get()), node.getKey(findndex.get())); //删除直接后继节点
			}
		}else { //本节点找不到
			find = remove(node.getChild(findndex.get()), key);
		}
		//放弃本层，在parent层判断
		//本node以及它的所有子树找到并且删除了数据
		int trackIndex = findndex.get();
		BNode trackingNode = node.getChild(trackIndex); //trackIndex取值0,1,2,3...keyNum
		//0 只能找1的右孩子
		//最后一个 只能找最后一个的左孩子
		//中间的 先找左，后找右
		if(trackingNode != null) { //如果找不到就不需要调整平衡
			if(trackingNode.isOnLowerBound()) {
				if(trackIndex == 0) { //要删除的Node在最左边，去找第一个key的右孩子看看能不能借
					BNode brother = node.getChild(1);
					if(brother.decreOnLowerBound()) { //兄弟没得借
						node.combine(1);
						
					}else { //借兄弟的第一个key
						node.lendToLeft(1);
					}
				}else if(trackIndex == node.getKeyNum()) { //右借左，因为是最后一个元素，只能向左借
					//最后一个元素的左孩子
					BNode brother = node.getChild(node.getKeyNum() - 1);
					if(brother.decreOnLowerBound()) { //没得借  
						node.combine(trackIndex);
					}else { //有得借
						node.lendToRight(node.getKeyNum());
					}
				}else { //在中间
					//左借 右借
					if(!node.getChild(trackIndex - 1).decreOnLowerBound()) { //左兄有得借
						node.lendToRight(trackIndex);
					}else if(!node.getChild(trackIndex + 1).decreOnLowerBound()){ //右兄有得借
						node.lendToLeft(trackIndex + 1); //因为是向右兄弟借，所以下标+1 [下标+1]作为右子树 [下标]作为左子树
					}else {
						node.combine(trackIndex); //跟左边组合
					}
				}
				if(node == this.root && node.getKeyNum() == 0) { //根节点只有一个元素，被借了
					this.root = node.getChild(0); //合并之后左孩子就是新root
				}
			}
		}
		//代码执行到这里代表，找不到，找到了已经删除了，删除的地方就是当前这个node
		return find;
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
	public static int searchForInsert(BNode node, int key) {
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
	private static void search(BNode node, int key, SearchResult searchResult) {
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
	//////For Test
	public void testForRemoveAllNode() {
		List<Integer> treeKeys = new ArrayList<>();
		if(this.root != null) {
			travelAllNode(this.root, treeKeys);
			System.out.println("B树有: "+treeKeys.size()+ "个元素");
		}
		int treeKeysSize = treeKeys.size();
		List<Integer> removedKeys = new ArrayList<>();
		while(treeKeys.size() > 0) {
			int randomIndex = new Random().nextInt(treeKeys.size());
			int randomKey = treeKeys.get(randomIndex);
			
			removedKeys.add(randomKey);
			try {
				System.out.printf("删除的元素 数目: %d 总数: %d, %s\n", removedKeys.size(), treeKeysSize, new ObjectMapper().writeValueAsString(removedKeys));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println("删除元素:" + randomKey);
			
			Debugger.set("temp", Debugger.clone(this));
			
			remove(randomKey);
			
			checkTree();
			
			treeKeys.remove(randomIndex);
			
		}
		if(this.root != null) {
			throw new RuntimeException("删除后root还有东西");
		}
	}
	public void checkTree() {
		travelAllNode(this.root, new ArrayList<>());
		System.out.println("检查结束");
	}
	private void travelAllNode(BNode node, List<Integer> treeKeys) {
		if(node == null) {
			return; 
		}
		for(int i = 1; i <= node.getKeyNum(); i++) {
			BNode leftChild = node.getChild(i - 1);
			BNode rightChild = node.getChild(i);
			int key = node.getKey(i);
			if(i == 1) {
				travelAllNode(leftChild, treeKeys);
				if(leftChild != null) {
					for(int j=1; j <= leftChild.getKeyNum(); j++) {
						if(key < leftChild.getKey(j)) {
							throw new RuntimeException("顺序不对");
						}
					}
				}
			}
			travelAllNode(rightChild, treeKeys);
			if(rightChild != null) {
				for(int j=1; j <= rightChild.getKeyNum(); j++) {
					if(key > rightChild.getKey(j)) {
						throw new RuntimeException("顺序不对");
					}
				}
			}
			if(treeKeys != null) {
				if(treeKeys.contains(key)) {
					throw new RuntimeException("有重复值:"+ key);
				}
				treeKeys.add(key);
			}
		}
	}
}
