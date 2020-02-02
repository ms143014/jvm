package debugger;

import com.jvm.source.avl.AvlTree;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-24 22:07:32
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class FileLoader2 {
	/**
	 * debugger.FileLoader2.t2()
	 */
	public static void t2() throws Exception {
		System.out.println("sss222");
	}
	/**
	 * debugger.FileLoader1.t1(tree)
	 * */
	public static void t1(AvlTree<Integer> tree) throws Exception {
		new Thread(()->{
			System.out.println("t1方法:"+Thread.currentThread().getName());
			tree.contains(10);	
		}).start();
	}
}
