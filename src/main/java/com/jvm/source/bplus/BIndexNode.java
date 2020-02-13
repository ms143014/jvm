package com.jvm.source.bplus;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:
 * 	T是数据类型
 * 	V是键，也就是索引，需要可比较
 * @创建者: Pom
 * @创建时间: 2020-02-12 23:56:13
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BIndexNode<T, V extends Comparable<V>> implements Serializable{
	private static final long serialVersionUID = -3208029717442433962L;
	protected BIndexNode<T, V> parent;
	protected List<BIndexNode<T, V>> childs;
	protected List<V>keys;
	/**索引的个数*/
	private int size;
	public BIndexNode(int maxSize) {
		this.parent = null;
		this.childs = new ArrayList<>(maxSize);
		this.keys = new ArrayList<>(maxSize);
	}
	public int getSize() {
		return size;
	}
	

}
