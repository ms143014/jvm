package com.jvm.source.hefuman;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-19 12:01:48
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class ContentNode extends AbstractNode{
	private static final long serialVersionUID = 2254536739866527424L;
	public String data = null;
	public ContentNode(String data) {
		super();
		this.data = data;
		this.score = 1;
	}
	@Override
	public String toString() {
		return "data: " + this.data +" score: "+ this.score +" code: " + this.code;
	}
}
