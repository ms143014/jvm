package debugger;

import com.pattern.command1.KeyPad;

public class Abcd {
	/**
	 * debugger.Abcd.t0()
	 */
	public static void t0() throws Exception {
		new Thread(()->{
			for(int i=0; i < 10; i++) {
				Object object= Debugger.get("w" + i);
				System.out.println("notify:" + i);
				synchronized(object) {
					object.notify();		
				}
			}
		}) .start();
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
