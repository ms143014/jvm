package debugger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.SerializationUtils;

import com.jvm.source.b.BNode;
import com.jvm.source.b.BTree;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-29 21:42:58
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface BTreeTester {
	/**
	 * 
	 * debugger.BTreeTester.t0()
	 */
	public static void t0() throws Exception {
		new Thread(()->{
			try {
				BTree tree = SerializationUtils.deserialize(new FileInputStream("./abcd.dat"));
				//BNode node = tree.search(342).getNode();
//				node.lend(2, (key, left, right)->{
//					System.out.println(key +" " + left + " " + right);
//				});
				tree.remove(15);
				tree.rendered();
				tree.search(46).getNode().combine(1);
				tree.rendered();
				//System.out.println(node);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}) .start();
	}
}
