package com.jvm.source.hefuman;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-19 12:29:11
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class WeightNode extends AbstractNode {
	private static final long serialVersionUID = -7863562665309724810L;
	public AbstractNode leftChild = null;
	public AbstractNode rightChild = null;
	@Override
	public String toString() {
		return "weight scorce: " + this.score + " code: " + this.code;
	}
}
