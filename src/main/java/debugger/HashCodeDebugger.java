package debugger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-16 17:46:30
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface HashCodeDebugger {
	public static void t0() throws Exception {
		Map<Integer, List<Object>> data = null;
		debugger.HashCodeDebugger.t1(data);
	}
	public static void t1(Map<Integer, List<Object>> data) throws Exception {
		for(int i =0; i < 50000; i++) {
			Object object = new Object();
			int hashCode = object.hashCode();
			List<Object> values = data.get(hashCode) == null? new ArrayList<Object>(): data.get(hashCode);
			values.add(object);
			if(data.containsKey(object.hashCode())) {
				
			}else {
				data.put(object.hashCode(), values);
			}
		}
		System.out.println("插入完毕");
	}
}
