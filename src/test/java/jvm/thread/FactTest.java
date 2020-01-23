package jvm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

public class FactTest {
	@Test
	public void testFact() {
		fact(1000).forEach(System.out::println);
	}
	/**
	 * 分解因数
	 * */
	public static List<Integer> fact(int f){
		List<Integer> result = new ArrayList<Integer>();
		IntStream.range(2, f/2).forEach(i->{
			if(f%i == 0) {
				if(!result.contains(i)) result.add(i);
				if(!result.contains(f/i)) result.add(f/i);
			}
		});
		result.sort((a,b)->a-b);
		return result;
	}
}
