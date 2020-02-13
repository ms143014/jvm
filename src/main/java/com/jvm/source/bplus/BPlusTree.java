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
	protected int degree;
	public BPlusTree() {
		this.degree = 5; //默认为5阶
	}
}
