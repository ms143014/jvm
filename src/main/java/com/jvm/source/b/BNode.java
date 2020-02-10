package com.jvm.source.b;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-31 17:03:34
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BNode implements Serializable{
	private static final long serialVersionUID = -7860589311607455135L;
	/**
	 * 树的阶
	 * 若阶=6 最多有5个元素，6个孩子
	 * 除根节点和叶子节点，中间节点的最少元素个数 ┌阶/2┐ - 1，如果阶=6，则最小有2个元素 ，3颗子树
	 * 分裂的时候尽量靠中间 
	 * 
	 * 5阶 4个孩子 最小key数 ┌阶/2┐ - 1 = 2
	 * 6阶 5个孩子 最小key数 ┌阶/2┐ - 1 = 2
	 * 
	 * */
	public static int MAX_CHILDS = 5;
	/**
	 * 内部节点最少节点数， 主要是分裂的时候用到
	 * */
	public static final int MIN_ELEMENTS = (int)Math.ceil(1.0 * MAX_CHILDS / 2) - 1;
	/**
	 * 判断满
	 * */
	public static final int MAX_ELEMENTS = MAX_CHILDS - 1;
	
	private int [] data = new int[MAX_CHILDS];		//[0]不使用，从1开始，为了对齐
	private BNode [] childs = new BNode[MAX_CHILDS];
	private BNode parent;
	private int keyNum = 0;
	public BNode() {
	}
	public BNode(int key, int degree) {
		this();
		this.data = new int[degree];
		this.childs = new BNode[degree];
		this.data[1] = key;
		this.keyNum++;
	}
	/**
	 * 构造器为了测试
	 * */
	public BNode(int[]data, BNode[]childs, int keyNum) {
		for(int i=0; i < data.length; i++)
			this.data[i] = data[i];
		for(int i=0; i < childs.length; i++)
				this.childs[i] = childs[i];	
		this.keyNum = keyNum;
	}
	/**
	 * 	一个新的节点
	 * */
	public BNode(int key) {
		this.data[1] = key;
		this.keyNum++;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	public BNode[] getChilds() {
		return childs;
	}
	public void setChilds(BNode[] childs) {
		this.childs = childs;
	}
	public BNode getParent() {
		return parent;
	}
	public void setParent(BNode parent) {
		this.parent = parent;
	}
	public int increKeyNum() {
		if(isFull()) {
			throw new RuntimeException("节点已满，不能再增加元素");
		}
		return ++this.keyNum;
	}
	public int decreKeyNum() {
		return --this.keyNum;
	}
	public int getKeyNum() {
		return keyNum;
	}
	/**
	 * 是否是叶子，插入元素必定是定位到叶子
	 * */
	public boolean isLeaf() {
		return this.childs[0] == null;
	}
	/**
	 * 是否满
	 * */
	public boolean isFull() {
		return keyNum == MAX_ELEMENTS;
	}
	/**
	 * 删除之后会用到
	 * */
	public boolean isOnLowerBound() {
		return keyNum < MIN_ELEMENTS;
	}
	/**
	 * 如果删除一个key 是否小于最小节点数
	 * */
	public boolean decreOnLowerBound() {
		return keyNum - 1 < MIN_ELEMENTS;
	}
	/**
	 * 如果添加一个key是否满
	 * */
	public boolean increOnUpperBound() {
		return keyNum + 1 > MAX_ELEMENTS;
	}
	/**
	 * @param index 数组data的下标，注意0
	 * */
	public void setKey(int index, int key) {
		this.data[index] = key;
	}
	public int getKey(int index) {
		return this.data[index];
	}
	/**
	 * 获取一个节点的孩子位置
	 * */
	public static BNode getChildNodeAtIndex(BNode node, int keyIndex, int offset) {
		keyIndex += offset;
		if(keyIndex < 0 || keyIndex > node.keyNum) {
			throw new IllegalArgumentException("索引超出范围");
		}
		return node.childs[keyIndex];
	}
	public void setChild(int index, BNode child) {
		this.childs[index] = child;
	}
	/**
	 * 获取孩子引用，根据索引
	 * @param index 当前节点的孩子位置
	 * */
	public BNode getChild(int index) {
		return this.childs[index];
	}
	/**
	 * @param index 该位置必须有元素
	 * */
	public void setLeftChildAtIndex(int index, BNode child) {
		if(this.keyNum >= index) {
			this.setChild(index - 1, child);
		}
	}
	/**
	 * @param index 该位置必须有元素
	 * */
	public BNode getLeftChildAtIndex(int index) {
		return this.getChild(index - 1);
	}
	/**
	 * @param index 该位置必须有元素
	 * */
	public void setRightChildAtIndex(int index, BNode child) {
		if(this.keyNum >= index) {
			this.setChild(index, child);
		}
	}
	/**
	 * @param index 该位置必须有元素
	 * */
	public BNode getRightChildAtIndex(int index) {
		return this.getChild(index);
	}
	/**
	 * @param index 该位置必须有元素
	 * */
	public void setChildAtIndex(int index, BNode leftChild, BNode rightChild) {
		this.setLeftChildAtIndex(index, leftChild);
		this.setRightChildAtIndex(index, rightChild);
	}
	/**
	 * 在末尾追加，只使用右子树
	 * */
	public void append(BNode newNode) {
		this.data[this.keyNum + 1] = newNode.getKey(1);
		this.childs[this.keyNum + 1] = newNode.getRightChildAtIndex(1);
		this.keyNum++;
				
	}
	/**
	 *  根据下标删除 
	 *  @param index 从1开始，1就是第一个key
	 *  @return 返回key值和左右子树 包装成Node返回
	 * */
	public BNode remove(int index) {
		if(this.keyNum >= index) {
			BNode removeNode = new BNode(this.getKey(index));
			removeNode.setChildAtIndex(index, this.getLeftChildAtIndex(index), 
					this.getRightChildAtIndex(index));
			this.childs[index - 1] = this.childs[index]; //处理左孩子
			for(int i = index; i < this.keyNum; i++) {
				this.data[i] =  this.data[i + 1]; 
				this.childs[i] =  this.childs[i + 1]; 
			}
			this.keyNum--;
			return removeNode;
		}
		return null;
	}
	/**
	 * 左子树不够，借给左子树，向右子树借
	 * @param index 左子树 指向
	 * */
	protected void lendToLeft(int index) {
		BNode left = this.getChild(index - 1);
		BNode right = this.getChild(index);
		right.lend(1, (lendKey, lendLeft, lendRight)->{
			left.cloneByInsert(this.getKey(index), (childs, i)->{
				childs[i] = lendLeft;
			});
			this.setKey(index, lendKey); //parent更新
		});
	}
	/**
	 * 右子树不够，借给右子树，向左子树借
	 * */
	protected void lendToRight(int index) {
		BNode left = this.getChild(index - 1);
		BNode right = this.getChild(index);
		left.lend(left.getKeyNum(), (lendKey, lendLeft, lendRight)->{
			right.cloneByInsert(this.getKey(index), (childs, _index)->{
				childs[0] = lendRight;
			});
			this.setKey(index, lendKey);
		});
	}
	/**
	 * 	借出去一个元素
	 * @param iterator 借出去的元素数据暴露，这个有可能为null，当合并的时候，去parent借一个节点
	 * */
	public void lend(int index, NodeIterator iterator) {
		if(this.keyNum >= index) {
			if(iterator != null) {
				iterator.usage(this.data[index], this.childs[index - 1], this.childs[index]);
			}
			//左子树补一下
			this.childs[index - 1] = this.childs[index]; //处理左孩子
			for(int i = index; i < this.keyNum; i++) {
				this.data[i] =  this.data[i + 1]; 
				this.childs[i] =  this.childs[i + 1]; 
			}
			this.keyNum--;
		}
	}
	/**
	 * 最后一个孩子
	 * */
	public BNode getLastChild() {
		return this.childs[this.keyNum];
	}
	/**设置孩子*/
	public void connectChild(int index, BNode child) {
		this.childs[index] = child;
		if(child != null) {
			child.parent = this;
		}
	}
	/**
	 * 寻找key的下标
	 * 如果找不到， 就寻找孩子指针
	 * @param key 对比的键
	 * @param index <p>返回继续向哪一个孩子寻找</p>
	 * 	<p>如果小于第1个元素，则从最左的孩子找[0]</p>
	 *  <p>如果小于第2个元素，则从第2个元素的最左的孩子找[1]</p>
	 * 	<p>如果大于最后一个元素，则最后元素的右孩子找</p>
	 * */
	public boolean find(int key, AtomicInteger index) {
		for(int i = 0; i < this.keyNum; i++) {
			int nodeKey = this.getKey(i + 1);
			if(key < nodeKey) {
				index.set(i);	//小于，所以是左孩子
				break;
			}else if(key == nodeKey) {	//等于不好说
				index.set(i + 1);
				return true;
			}else {
				if(i == this.keyNum - 1) {
					index.set(i + 1);
				}
			}
		}
		return false;
	}
	/**最靠左边的兄弟*/
	public BNode getLeftSibling() {
		if(this.parent == null)
			return null;
		int i;
		for(i=0; i < this.parent.childs.length; i++) {
			if(this.parent.childs[i] == this) {
				break;
			}
		}
		return i==0 ? null:this.parent.childs[i -1];
	}
	/**当前元素被它的右孩子的最小值替代，也就是直接后继
	 * @param index 是当前key的右子树
	 * */
	public void subsitution(int index) {
		BNode cursor = this.getChild(index);
		do {
			this.setKey(index, cursor.getKey(1));
			cursor = cursor.getChild(0);
		}while(cursor != null);
	}
	/**
	 * <p>删除元素，发现元素个数少于最小 节点数</p>
	 * <p>这个是parent</p>
	 * <p>要合并的是data[index]的左右子树childs[index-1]，childs[index]</p>
	 * 因为是用左孩子合并，所以不需要合并后的node赋值回去
	 * @param index <p>要合并的位置</p>
	 * 	<p>这个是元素的位置data[index]，它的左子树是childs[index-1]，右子树是childs[index]</p>
	 * 	
	 * */
	public void combine(int index) {
		BNode left = this.getChild(index - 1);
		BNode right = this.getChild(index);
		right.setKey(0, this.getKey(index)); //先取值
		this.lend(index, null); //删
		for(int i =0; i < right.keyNum + 1; i++) {
			left.increKeyNum();
			left.setKey(left.keyNum, right.getKey(i));
			left.setChild(left.keyNum, right.getChild(i));
		}
		this.setChild(index - 1, left); //更新一下新节点
	}
	public int height(BNode node) {
		if(!node.isLeaf()) {
			return 1 + height(node.getChild(0));
		}else {
			return 1; //叶子节点算一层
		}
	}
	/**查找结果
	 * 
	 * */
	public static class SearchResult{
		private BNode node;	//找到的节点
		private int index;	//找到key的index位置，注意，如果index = 0，则代表是第一个key，对应数组 data[1]
		private boolean found = false;
		public BNode getNode() {
			return node;
		}
		public void setNode(BNode node) {
			this.node = node;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public boolean isFound() {
			return found;
		}
		public void setFound(boolean found) {
			this.found = found;
		}
		@Override
		public String toString() {
			return "SearchResult [node=" + node + ", index=" + index + ", found=" + found + "]";
		}
	}
	@FunctionalInterface
	public static interface NodeIterator{
		/**
		 * 位置已经空出来了
		 * */
		void usage(int key, BNode left, BNode right);  
	}
	@FunctionalInterface
	public static interface NodeUsage{
		/**
		 * 位置已经空出来了
		 * */
		void usage(BNode[]childs, int index);  
	}
	public BNode cloneByInsert(int newKey) {
		return cloneByInsert(newKey, null);
	}
	/**
	 * [10, 20, 30]
	 * 5
	 * */
	public BNode cloneByInsert(int newKey, NodeUsage nodeUsage) {
		BNode insertTo = this; //默认节点是不满的
		boolean isFull = this.isFull();
		if(isFull) {
			insertTo = new BNode(); //新建一个Node用于装载所有数据
			insertTo.data = new int[BNode.MAX_CHILDS + 1]; //容量 +1
			insertTo.childs = new BNode[BNode.MAX_CHILDS + 1]; //容量 +1
		}
		//int newKey = newNode.getKey(1);	//新节点内容
		int i;	//遍历的位置，小于比较节点的
		for(i = 0; i < keyNum; i++) {
			int key = this.data[i +1];
			if(newKey > key) {
				insertTo.data[i + 1] = this.data[i + 1];
				insertTo.childs[i] =  this.childs[i];
				if(i == keyNum - 1) {
					insertTo.childs[i + 1] =  this.childs[i + 1];
				}
			}else if(newKey < key){
				break;
			}else {
				System.out.println("元素已经存在");
				return this; //元素已经存在不做任何修改
			}
		}
		//后续复制
		for(int j = keyNum; j >= i; j--) {
			if(j == 0 ) {
				continue; //哨兵不复制
			}
			insertTo.data[j + 1] = this.data[j];
			insertTo.childs[j + 1] = this.childs[j];
		}
		//找到位置设置数值
		insertTo.data[i + 1] = newKey;
		//如果只设置值不管孩子，nodeUsage就为null
		if(nodeUsage != null) {
			/*insertTo.childs[i + 1] = newNode.childs[1];
			insertTo.childs[i] = newNode.childs[0];*/
			nodeUsage.usage(insertTo.childs, i + 1); 
		}
		
		insertTo.keyNum = this.keyNum + 1; //元素数量  +1
		
		if(isFull) {
			return insertTo.split();
		}else {
			return insertTo;
		}
	}
	/**
	 * 节点分割，节点满了，抽取中间的为parent，
	 * 左孩子是child[0]，右孩子为child[1]
	 * */
	public BNode split() {
		BNode parent = new BNode(this.getKey( 1 + BNode.MIN_ELEMENTS));
		BNode leftChild = new BNode();
		BNode rightChild = new BNode();
		leftChild.parent = rightChild.parent = parent;
		parent.childs[0] = leftChild;
		parent.childs[1] = rightChild;
		int i;
		for(i = 0; i < BNode.MIN_ELEMENTS; i++) {
			leftChild.data[i + 1] = this.data[i + 1];
			leftChild.childs[i] = this.childs[i];
			if(i == BNode.MIN_ELEMENTS -1) {
				leftChild.childs[i + 1] = this.childs[i + 1]; //最右边的孩子
			}
			leftChild.keyNum++;
		}
		i++;
		rightChild.childs[0] = this.childs[i];
		i++;
		for(int j=1; i < this.keyNum + 1; i++,j++) {
			rightChild.data[j] = this.data[i];
			rightChild.childs[j] = this.childs[i];
			rightChild.keyNum++;
		}
		return parent;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < keyNum + 1; i++) {
			sb.append(this.data[i] +"-");
		}
		int lstInxOfComma = sb.lastIndexOf("-");
		if(lstInxOfComma > -1) {
			sb.delete(sb.length() - "-".length(), sb.length());
		}
		return sb.toString();
	}
}
