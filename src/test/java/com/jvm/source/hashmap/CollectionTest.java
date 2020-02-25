package com.jvm.source.hashmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-18 11:49:59
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class CollectionTest {
	@Test
	public void testLinkedHashMap() {
		Debugger.startAndJoin(()->{
			Map<String, Object> map = new LinkedHashMap<>();
			Debugger.set("map", map);
			System.out.println(map);
			map.entrySet().forEach(e->{
				System.out.println(e.getKey() + ":" + e.getValue());
			});
		});
	}
	@Test
	public void testTreeMapConfict() {
		List<Object> list = new ArrayList<Object>();
		Map<Integer, Object> tree = new TreeMap<>();
		for(int i=0; i < 1000000; i++) {
			Object object = new Object();
			Object treeObject = tree.get(object.hashCode()); //取元素
			if(treeObject != null) { //已有，代表hashCode一样
				list.add(object);
				list.add(treeObject);
			}
			tree.put(object.hashCode(), object);
		}
		System.out.println("检查完毕");
	}
	@Test
	public void testHashMapConfict() {
		Debugger.startAndJoin(()->{
			Map<ConflictDto, String> map = new HashMap<>();
			Debugger.set("map", map);
			ConflictDto dto = new ConflictDto();
			map.put(dto, "ok");
//			for(int i=10; i < 20; i++) {
//				set.add(i);
//			}
			System.out.println();
		});
	}
	@Test
	public void testSet() {
		Debugger.startAndJoin(()->{
			Set<Integer> set = new HashSet<>();
			Debugger.set("set", set);
//			for(int i=10; i < 20; i++) {
//				set.add(i);
//			}
			System.out.println();
		});
	}
	@Test
	public void testTreeSet() {
		Debugger.startAndJoin(()->{
			SortedSet<Integer> tree = new TreeSet<>((a,b)-> {
				return a - b;
			});
			Debugger.set("tree", tree);
			for(int i=10; i < 20; i++) {
				tree.add(i);
			}
			System.out.println();
		});
	}
	@Test
	public void testMaxInteger() {
		Debugger.startAndJoin(()->{
			System.out.println();
		});
	}
	@Test
	public void testHashSet() {
		Debugger.startAndJoin(()->{
			Set<Integer> set = Collections.synchronizedSet(new HashSet<>());
			
			System.out.println();
		});
	}
	@Test
	public void testProperties() {
		Debugger.startAndJoin(()->{
			TreeMap<Integer, Object> tree = new TreeMap<>((a, b)->{
				return b - a;
			}) ;
			Debugger.set("tree", tree);
			for(int i = 10; i < 20; i++){
				tree.put(i, org.apache.commons.lang3.RandomStringUtils.randomNumeric(5));	
			}
			System.out.println();
		});
	}
}
