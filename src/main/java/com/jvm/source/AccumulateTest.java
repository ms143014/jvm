package com.jvm.source;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-17 09:59:31
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class AccumulateTest {
	public static void main(String[] args) {
		Map<Integer, Integer> result = new HashMap<>();
		result.put(1, 1);
		result.put(2, 1);
		for(int i = 3; i <= 12;i ++) {
			result.put(i, result.get(i -1) + result.get(i - 2) );
		}
		System.out.println(result);
		
	}
	private static int accumulate(int n) {
		if(n > 0) {
			System.out.printf("f(%-2d)= %-2d + f(%d)\n", n, n, n - 1);
			return n + accumulate(n - 1);
		}
		System.out.printf("f(%d) = %d\n", n, n);
		return 0;
	}
}
