package debugger;

import com.jvm.source.rbtree.RBTree;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-29 21:42:58
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface AvlTester2 {
	/**
	 * debugger.AvlTester2.t0(tree)
	 */
	public static void t0(RBTree<Integer> tree) throws Exception {
		new Thread(()->{
			tree.checkTree();
		}) .start();
	}
}
