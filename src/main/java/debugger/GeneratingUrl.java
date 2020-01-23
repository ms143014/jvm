package debugger;

import java.util.stream.IntStream;

public class GeneratingUrl {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		IntStream.range(0, 100).forEach(i ->{
			sb.append(String.format("http://pom:12345@www.a%02d.com:8761/eureka/,", i));
		});
		sb.delete(sb.length() - 1, sb.length());
		System.out.println(sb);
	}
}
