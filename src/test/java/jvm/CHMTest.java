package jvm;


import org.junit.Test;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-27 10:57:49
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class CHMTest {
	@Test
	public void a() {
		//tableSizeFor(0);
		int n = 0xffffffff;
		System.out.println(n + 1);
	}
	@Test
	public void b() {
		//int n = 65841;
		int n = 128;
		System.out.println(Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println(Integer.toBinaryString(n)  +" " + (n + 1));
	}
	private static final int tableSizeFor(int c) {
        int n = c - 1;
        System.out.println(n);
        n |= n >>> 1;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println(Integer.toBinaryString(n) +" " + (n + 1));
        return (n < 0) ? 1 : (n >= (1 << 30)) ? (1 << 30) : n + 1;
    }
}
