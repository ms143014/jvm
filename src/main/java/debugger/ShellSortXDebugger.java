package debugger;

import com.jvm.source.sort.ShellSortX;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-11 14:41:41
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface ShellSortXDebugger {
	/**
	 * debugger.ShellSortXDebugger.t0()
	 */
	public static void t0() throws Exception {
		new Thread(()->{
			ShellSortX sortX = Debugger.get("shell");
			sortX.sort();
		}) .start();
	}
}
