package debugger;

import com.jvm.source.hefuman.AbstractNode;
import com.jvm.source.hefuman.ContentNode;
import com.jvm.source.hefuman.WeightNode;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-19 18:48:08
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface DebuggerHafuman {
	public static void t0() throws Exception {
		String decodeStr = null;
		AbstractNode root = null;
		StringBuilder sb = new StringBuilder();
		debugger.DebuggerHafuman.serialze(root, sb);
	}
	public static void serialze(AbstractNode node, StringBuilder sb) throws Exception {
	}
	
	public static void t1(String decodeStr, AbstractNode root) throws Exception {
		StringBuilder result = new StringBuilder();
		int index = 0;
		while(index < decodeStr.length()) {
			AbstractNode cursor = root;
			while(cursor instanceof WeightNode) {
				char c = decodeStr.charAt(index);
				if(c == '0') {
					cursor = ((WeightNode)cursor).leftChild;
				}else if(c == '1'){
					cursor = ((WeightNode)cursor).rightChild;
				}else {
					throw new RuntimeException("压缩文件有错");
				}
				index++;
			}
			result.append(((ContentNode)cursor).data);
		}
		System.out.println("解码结果:"+result);
		
	}

}
