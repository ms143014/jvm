package com.jvm.source.bplus;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-12 23:55:45
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BPlusTree <T, V extends Comparable<V>> implements Serializable{
	private static final long serialVersionUID = -2715915650601348115L;
	protected BIndexNode<T, V> root = null;
	protected int degree;
	protected BDataNode<T, V> records;
	public BPlusTree() {
		this.degree = 5; //默认为5阶
	}
	public void insert(T value, V key) {
		this.root = insert(this.root, value, key);
	}
	public BIndexNode<T, V> insert(BIndexNode<T, V> node, T value, V key) {
		if(node == null) {
			BDataNode<T, V> newNode = new BDataNode<>(degree, value, key);
			return newNode;
		}
		if(node.isLeaf()) {
			if(!node.isFull()) {
				BDataNode<T, V> dataNode = (BDataNode<T, V>)node;
				for(int i = 0; i < node.getSize(); i++) {
					int compare = node.compare(i, key);
					if(compare > 0) {
						//就插在这个位置
						node.incrSize();
						for(int j = node.getSize() - 1; j > i ; j--) {
							dataNode.setKey(j, node.getKey(j - 1));
							dataNode.setValue(j, dataNode.getValue(j - 1));
						}
						dataNode.setKey(i, key);
						dataNode.setValue(i, value);
						break;
					}else if(compare < 0){
						if(i == node.getSize() - 1) {
							//就插在这个位置
							node.incrSize();
							dataNode.setKey(node.getSize() - 1, key);
							dataNode.setValue(node.getSize() - 1, value);
						}
					}else if(compare == 0) {
						//更新value即可
						dataNode.setValue(i, value);
						break;
					}
				}
			}else {
				//分裂
				BIndexNode<T, V> left = new BIndexNode<>(this.degree);
				BIndexNode<T, V> right = new BIndexNode<>(this.degree);
				if(node.getClass() == BIndexNode.class) { //是索引节点
					
				}else { //是叶子节点
					for(int i=0; i < node.getSize(); i++) {
					}
				}
			}
		}else {
			
		}
		return node;
	}
}
