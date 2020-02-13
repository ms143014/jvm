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
	public BDataNode(int nodeSize) {
		super(nodeSize);
	}

}
