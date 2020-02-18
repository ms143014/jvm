package com.jvm.source.bplus;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-12 23:57:09
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BDataNode<T, V extends Comparable<V>> extends BIndexNode<T, V> implements Serializable{
	private static final long serialVersionUID = 8136739034009505960L;
	protected BDataNode<T, V> next = null;
	protected Object values[];
	public BDataNode(int nodeSize) {
		super(nodeSize);
		this.values = new Object[nodeSize];
	}
	public BDataNode(int nodeSize, T value, V key) {
		this(nodeSize);
		this.values[0] = value;
		super.keys[0] = key;
		super.incrSize();
	}
	public void setValue(int index, T value) {
		this.values[index] = value;
	}
	public T getValue(int index) {
		return (T)this.values[index];
	}

}
