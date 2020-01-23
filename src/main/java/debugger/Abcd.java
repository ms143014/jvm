package debugger;

import com.pattern.command1.KeyPad;

public class Abcd {
	public static class B{
		public static void t0(int i, int j) {
			System.out.println("hell > " + i * 2);
		}
	}
	public static void test(String a, String b) {
		Integer current = 1;
		if(Debugger.get(1, Integer.class) == current) {
			Debugger.set(1, current + 1);
			System.out.println("只调用一次");
			KeyPad keyPad = new KeyPad();
			keyPad.play();
			keyPad.accelerate();
			keyPad.decelerate();
			keyPad.pause();
			keyPad.rewind();
			keyPad.stop();
		}
	}
}
