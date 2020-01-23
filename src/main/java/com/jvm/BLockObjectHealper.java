package com.jvm;

import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2019-11-09 14:34:40
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class BLockObjectHealper {
	public static void t0() {
		IntStream.range(1, 10).forEach(i->{
			System.out.println("i= " + i);
		});
		System.out.println("continue...");
	}
}
