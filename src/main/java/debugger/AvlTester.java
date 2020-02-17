package debugger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-29 21:42:58
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface AvlTester {
	/**
	 * debugger.AvlTester.t0()
	 * */
	public static void t0() throws Exception {
		new Thread(()->{
			Lock lock = Debugger.get("lock");
			lock.lock();
			lock.unlock();
//			boolean locked = false;
//			try {
//				if(locked = lock.tryLock()) {
//					System.out.println("得到了锁");
//				}else {
//					System.out.println("获取锁失败");
//				}
//			}finally {
//				if(locked) {
//					lock.unlock();
//					System.out.println("锁获锁释放");
//				}
//			}
//			System.out.println("fin");
		}) .start();
	}
}
