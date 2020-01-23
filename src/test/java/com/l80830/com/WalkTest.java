package com.l80830.com;

import static com.l80830.com.RouteUtils.*;

import org.junit.Assert;
import org.junit.Test;

public class WalkTest {
	@Test
	public void run() {
		float time = Walk.run(40, 60, 200, 2, 800);
		Assert.assertTrue(Math.abs(scale2(timeToDistance(40, time)) - scale2(timeToDistance(60, time))) == 800f);
	}
}
