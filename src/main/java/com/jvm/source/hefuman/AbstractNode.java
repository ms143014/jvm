package com.jvm.source.hefuman;

import java.io.Serializable;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-19 12:30:14
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public abstract class AbstractNode implements Serializable {
	private static final long serialVersionUID = -2277534111509769146L;
	public int score = 0;
	public String code = "";
}
