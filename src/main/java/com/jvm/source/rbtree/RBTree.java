package com.jvm.source.rbtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import com.jvm.source.rbtree.RBNode.Color;
import com.jvm.source.rbtree.RBPrintableNode;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-04 20:23:31
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class RBTree<T extends Comparable<T>> implements Serializable{
	private static final long serialVersionUID = -4660896715578274995L;
	protected RBNode<T> root = null;
	private int size = 0;
	
	/**
	 * 插入元素
	 * 
	 * */
	public void insert(@SuppressWarnings("unchecked") T...datas) {
		for(T data: datas) {
			System.out.println("插入的新值是:" + data);
			this.root = insert(this.root, data, new AtomicBoolean(false));
			this.root.color = Color.BLACK;
		}
	}
	/**
	 * @param finishFix 是否不再修正，对于RR-1修正完毕后不需要递归修正，也就是针对RR-1，修正完毕设置这个为true
	 * 	刚开始为false，表示检查修正
	 * */
	private RBNode<T> insert(RBNode<T> node, T data, AtomicBoolean finishFix) {
		if(node == null) {
			this.size++;
			return new RBNode<T>(data);
		} else if(node.eq(data)) {
			System.out.println("键相等，更新值即可");
		} else{
			if(node.lt(data)) { //右分支
				node.right = insert(node.right, data, finishFix);
				node.right.parent = node;
			}else/* if(node.gt(data))*/{ //左分支
				node.left = insert(node.left, data, finishFix);
				node.left.parent = node;
			}
			if(!finishFix.get()) {
				//如果child是黑色， 那就不用调了，因为之前已经平衡的，只有child是红色才有可能打破平衡
				RBNode<T> left = node.left;
				RBNode<T> right = node.right;
				if(node.isLeftRed() && node.isRightRed() &&
					(left.isLeftRed() || left.isRightRed() || right.isLeftRed() || right.isRightRed())) {
					//RR-2
					node.color = Color.RED;
					left.color = Color.BLACK;
					right.color = Color.BLACK;
				}else if(node.isLeftRed() && (right == null || right.color == Color.BLACK)) {
					if(left.isLeftRed()) {
						//RR-1 LL
						node = node.rotateLL();
						finishFix.set(true);
					}else if(left.isRightRed()) {
						//RR-1 LR
						node = node.rotateLR();
						finishFix.set(true);
					}
				}else if(node.isRightRed() && (left == null || left.color == Color.BLACK)) {
					if(right.isLeftRed()) {
						//RR-1 RL
						node = node.rotateRL();
						finishFix.set(true);
					}else if(right.isRightRed()) {
						//RR-1 RR
						node = node.rotateRR();
						finishFix.set(true);
					}
				}
			}
		}
		return node;
	}
	public RBNode<T> find(T data){
		return find(this.root, data);
	}
	private RBNode<T> find(RBNode<T> node, T data){
		if(node == null || node.eq(data)) {
			return node;
		}else {
			if(node.lt(data)) { //右分支
				return find(node.right, data);
			}else{ //左分支
				return find(node.left, data);
			}
		}
	}
	/**
	 * 批量删除元素
	 * */
	public List<Boolean> removes(@SuppressWarnings("unchecked") T...datas) {
		List<Boolean> result = new ArrayList<>();
		for(T data: datas) {
			result.add(remove(data));
		}
		return result;
	}
	public boolean remove(T data) {
		RBNode<T> find = find(data);
		if(find == null) {
			return false;
		}
		while(find.left != null || find.right != null) {
			RBNode<T> scape;
			if(find.left != null) {
				scape = find.predecessor();
			}else /*find.right not null*/ {
				scape = find.successor();
			}
			find.setData(scape.getData());
			find = scape;
		}
		if(find.parent == null) {
			this.root = null;
		}else {
			lostBlackFix(find);
			//删除自己，这个只执行一次
			if(find.isOnLeft()) { //有parent 自己是孩子， 那肯定不是左就是右
				find.parent.left = null;
			}else /*不是左就是右*/{
				find.parent.right = null;
			}
			find.parent = null;
			this.size--;
		}
		System.out.printf("%d <= 本次删除键\n", find.getData());
		return true;
	}
	/**
	 * 要处理的节点必须是黑色，如果它有parent，则它的兄弟节点肯定不为NULL，如果为NULL则调整前必定已经不平衡，
	 * 所以brother必定不为null
	 * 要处理的节点不是root，因为fix前已经对root进行了判断
	 * 所以parent不为null
	 * @param node 要处理的节点，不一定是要删除节点，有可能是递归处理平衡的节点
	 * */
	private void lostBlackFix(RBNode<T> node) {
		RBNode<T> grandParent = node.parent.parent;
		RBNode<T> parent = node.parent;
		RBNode<T> brother = node.brother();
		
		if(node.color == Color.BLACK) {
			Consumer<RBNode<T>> parentRebinder = parent.isOnLeft()? grandParent::setLeft : grandParent::setRight;
			/*兄弟节点为红色，那parent必定位黑色*/
			if(/*parent.color == Color.BLACK && */brother.color == Color.RED) { 
				System.out.println("LB-1 父黑 兄红");
				//先改颜色
				RBNode<T> ret;
				if(node.isOnLeft()) {
					//R
					parentRebinder.accept(ret = parent.rotateRSimple());
					
				}else {
					//L
					parentRebinder.accept(ret = parent.rotateLSimple());
				}
				parent.color = Color.RED; //这个其实是brother担当的
				ret.color = Color.BLACK;
				lostBlackFix(node);
				return;
			}else { //LB-3
				Color parentColor = parent.color;
				if(brother.isLeftRed()) { //既然子节点是红色那么父必定是黑(brother黑 我黑 父不定)
					System.out.println("LB-3 父? 我黑 兄黑 兄左孩子红");
					brother.left.color = Color.BLACK;
					RBNode<T> ret;
					if(node.isOnLeft()) {
						/*    parent
						 *    /    \
						 *   me    brother
						 *         /
						 *       red  
						 */
						parent.right = brother.rotateLSimple();
						parentRebinder.accept(ret=parent.rotateRSimple());
						//parent原先颜色是不定的删除后在左边 需要染黑它，因为这个位置的颜色原先是黑色
						ret.left.color = Color.BLACK;
					}else { 
						/*    parent
						 *    /    \
						 *brother   me
						 *   /
						 * red      
						 */
						parentRebinder.accept(ret=parent.rotateLSimple());
						ret.right.color = Color.BLACK;
					}
					ret.color = parentColor;
					return;
				}else if(brother.isRightRed()) {
					System.out.println("LB-3 父? 我黑 兄黑 兄右孩子红");
					brother.right.color = Color.BLACK;
					RBNode<T> ret;
					if(node.isOnLeft()) {
						/*    parent
						 *    /    \
						 *   me    brother
						 *           \
						 *           red  
						 */
						parentRebinder.accept(ret=parent.rotateRSimple());
						ret.left.color = Color.BLACK;
					}else {
						/*    parent
						 *    /    \
						 *brother   me
						 *   \
						 *   red      
						 */
						parent.left = brother.rotateRSimple();
						parentRebinder.accept(ret=parent.rotateLSimple());
						ret.right.color = Color.BLACK;
					}
					ret.color = parentColor;
					return;
				}
			}
			//兄弟左红或者右红已经在LB-3处理了，来到这里兄弟必定没有红色孩子
			if(parent.color == Color.RED) {
				System.out.println("LB-2R 父红 兄黑 兄无红孩子 无递归"); 
				parent.color = Color.BLACK;
				brother.color = Color.RED; 
			}else {
				System.out.println("LB-2B 父黑 兄黑 兄无红孩子 递归检查parent");
				brother.color = Color.RED; //只需要染红兄弟，并递归以parent检查
				lostBlackFix(parent); //递归检查
			}
		}
	}
	public void clear() {
		removeClear(this.root, null);
		this.root = null;
		this.size--;
	}
	private void removeClear(RBNode<T> node, RBNode<T> parent) {
		if(node.left != null) {
			removeClear(node.left, node);
		}
		if(node.right != null) {
			removeClear(node.right, node);
		}
		if(parent != null) {
			if(parent.left == node) {
				System.out.println("删除" + node.getData());
				parent.left = null;
				this.size--;
			}
			if(parent.right == node) {
				System.out.println("删除" + node.getData());
				parent.right = null;
				this.size--;
			}
		}
	}
	public void rendered() {
		if(this.root == null) {
			return;
		}
		RBPrintableNode<T> printableRoot = new RBPrintableNode<>(this.root);
		Queue<RBPrintableNode<T>> result = new LinkedList<RBPrintableNode<T>>();
		printStepCalcWidth(printableRoot, null);
		fixXY(printableRoot, null, result);
		try {
			DebuggerWebsocket.post(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 计算节点的x，y坐标
	 * */
	public static <M extends Comparable<M>>void fixXY(RBPrintableNode<M> node, RBPrintableNode<M> parent, Queue<RBPrintableNode<M>> result) {
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
			fixXY((RBPrintableNode<M>)node.getLeftChild(), node, result);
		}
		if(node.getRightChild() != null) {
			fixXY((RBPrintableNode<M>)node.getRightChild(), node, result);
		}
	}
	public static <M extends Comparable<M>> void printStepCalcWidth(RBPrintableNode<M> node, RBPrintableNode<M> parent) {
		int eleWidth = 30;
		int eleSpace = 20;
		if(node.getLeftChild() != null) {
			printStepCalcWidth((RBPrintableNode<M>)node.getLeftChild(), node);
		}
		if(node.getRightChild() != null) {
			printStepCalcWidth((RBPrintableNode<M>)node.getRightChild(), node);
		}
		if(node.getLeftChild() == null) {
			if(node.getRightChild() == null) {
				node.setWidth(eleWidth + eleSpace / 2); //叶子
			}else { //只有右子树
				node.setWidth(eleSpace * 2 + ((RBPrintableNode<M>)node.getRightChild()).getWidth());
			}
		}else {
			if(node.getRightChild() == null) { //只有左子树
				node.setWidth(eleSpace * 2 + ((RBPrintableNode<M>)node.getLeftChild()).getWidth());
			}else { //中间
				node.setWidth(eleSpace + ((RBPrintableNode<M>)node.getLeftChild()).getWidth() + ((RBPrintableNode<M>)node.getRightChild()).getWidth());
			}
		}
		//System.out.println(node + "- " +(parent ==null?"空":parent));
	}
}
