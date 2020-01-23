package com.jvm;

public interface ThreadUtils {
	public static void sleep(long millis) throws RuntimeException {
		try {
			Thread.sleep(millis);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("被吵醒了");
		}
	}
}
