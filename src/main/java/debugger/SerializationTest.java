package debugger;

import java.util.Stack;

import com.jvm.source.tree.NodeTest;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-17 18:11:39
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface SerializationTest {
	public static void t0() throws Exception {
		debugger.SerializationTest.t1();
	}
	public static void t1() throws Exception {
		for(String item: NodeTest.logs) {
			System.out.println(item);
		}
	}
}
