package com.jvm.source.bplus;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:
 * 	如果是索引T是Node类型，如果是叶子，则是Data实际类型
 * 	V是键，也就是索引，需要可比较
 * @创建者: Pom
 * @创建时间: 2020-02-12 23:56:13
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BIndexNode<T, V extends Comparable<V>> implements Serializable{
	private static final long serialVersionUID = -3208029717442433962L;
	protected BIndexNode<T, V> parent;
	protected BIndexNode<T, V>[] childs;
	protected Object[]keys;
	/**索引的个数*/
	private int size;
	private int maxSize;
	
	@SuppressWarnings("unchecked")
	public BIndexNode(int maxSize) {
		this.maxSize = maxSize;
		this.parent = null;
		this.size = 0;
		this.childs = new BIndexNode[maxSize];
		this.keys = new Object[maxSize];
	}
	public void incrSize() {
		this.size++;
	}
	public int getSize() {
		return size;
	}
	
	public boolean isLeaf() {
		return this instanceof BDataNode;
	}
	public boolean isFull() {
		return this.size >= maxSize;
	}
	public V getKey(int index) {
		return (V)this.keys[index];
	}
	public void setKey(int index, V key) {
		this.keys[index] = key;
	}
	public void setChild(int index, BIndexNode<T, V> child) {
		this.childs[index] = child;
	}
	/**
	 * 下标为index的key和参数key比较大小
	 * */
	public int compare(int index, V key) {
		V indexKey = (V)this.keys[index];
		return indexKey.compareTo(key);
	}
	

}
