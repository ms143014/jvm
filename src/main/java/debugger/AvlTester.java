package debugger;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-29 21:42:58
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public interface AvlTester {
	public static void main(String[] args) {
		int n = 1000;
		double selectOne = 2d/n;
		double selectThree = 1d * (n-2) * 3 * 3 * 2 / (n * (n-1)*(n-2));
		System.out.printf("%f %f %f\n", selectOne, selectThree, selectOne - selectThree);
		System.out.printf("%f\n", (selectOne - selectThree)/selectThree*100);
	}
	public static float t0(int n) throws Exception {
		return 1f/n;
	}
}
