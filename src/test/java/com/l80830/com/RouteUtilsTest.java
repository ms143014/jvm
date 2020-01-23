package com.l80830.com;

import org.junit.Test;

public class RouteUtilsTest {
	@Test
	public void test60() {
		RouteUtils.check(60, 200f/60, 200);
		RouteUtils.check(60, 200f/60 + 1, 200);
		RouteUtils.check(60, 200f/60 + 1.5f, 200);
		RouteUtils.check(60, 200f/60 + 2, 200);
		RouteUtils.check(60, 200f/60 * 2 + 2, 400);
		RouteUtils.check(60, 200f/60/2, 100);
	}
	@Test
	public void test40() {
		RouteUtils.check(40, 5f, 200);
		RouteUtils.check(40, 2.5f, 100);
		RouteUtils.check(40, 7f, 200);
		RouteUtils.check(40, 30f, 880);
		RouteUtils.check(40, 7 * 10 + .33f, 200 * 10 + 40 * 0.33f);
		RouteUtils.check(40, 7 * 10 + 5f, 200 * 11);
		RouteUtils.check(40, 7 * 10 + 5.1f, 200 * 11);
		RouteUtils.check(40, 7 * 10 + 7.1f, 200 * 11 + 40 * 0.1f);
	}
}
