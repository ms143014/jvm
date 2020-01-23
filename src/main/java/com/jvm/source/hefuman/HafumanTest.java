package com.jvm.source.hefuman;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-19 12:02:39
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class HafumanTest {
	private static Map<String, String> hexMap = new HashMap<>();
	private static Map<String, String> binaryMap = new HashMap<>();
	static {
		hexMap.put("0000", "0");
		hexMap.put("0001", "1");
		hexMap.put("0010", "2");
		hexMap.put("0011", "3");
		hexMap.put("0100", "4");
		hexMap.put("0101", "5");
		hexMap.put("0110", "6");
		hexMap.put("0111", "7");
		hexMap.put("1000", "8");
		hexMap.put("1001", "9");
		hexMap.put("1010", "A");
		hexMap.put("1011", "B");
		hexMap.put("1100", "C");
		hexMap.put("1101", "D");
		hexMap.put("1110", "E");
		hexMap.put("1111", "F");
		
		binaryMap.put("0", "0000");
		binaryMap.put("1", "0001");
		binaryMap.put("2", "0010");
		binaryMap.put("3", "0011");
		binaryMap.put("4", "0100");
		binaryMap.put("5", "0101");
		binaryMap.put("6", "0110");
		binaryMap.put("7", "0111");
		binaryMap.put("8", "1000");
		binaryMap.put("9", "1001");
		binaryMap.put("A", "1010");
		binaryMap.put("B", "1011");
		binaryMap.put("C", "1100");
		binaryMap.put("D", "1101");
		binaryMap.put("E", "1110");
		binaryMap.put("F", "1111");
	}
	public static void main(String[] args) throws Exception {
		String input = "all rightssssssssssssssssssssssssssssssdsdssssssssssssssdsdssssssss! helldsssssssssssssdsdsdddddddddddddddddo world, fishc! this is a test only!";
		
		System.out.println("输入的字符串: " + input);
		
		StringBuilder codeTable = new StringBuilder();
		String encodeHex = encodeHex(input, codeTable);
		System.out.println("编码结果:"+encodeHex);
		System.out.println("解码："+decodeHex(codeTable.toString(), encodeHex));
		
		
		Debugger.startDaemon(()->{
			System.out.println();
		}).join();
	}
	
	/**
	 * 	序列化List
	 * */
	public static String serialzeNodes(LinkedList<AbstractNode> nodes) {
		StringBuilder result = new StringBuilder();
		nodes.forEach(node->{
			result.append(((ContentNode)node).data+":"+node.score+"#");
		});
		if(result.length() > 0) {
			result.deleteCharAt(result.lastIndexOf("#"));
		}
		return result.toString();
	}
	/**
	 * 	反序列化List
	 * */
	public static LinkedList<AbstractNode> unserialzeNodes(String str){
		if(str == null)
			return null;
		LinkedList<AbstractNode> result = new LinkedList<>();
		for(String data: str.split("#")) {
			ContentNode node = new ContentNode(data.split(":")[0]);
			node.score = Integer.parseInt(data.split(":")[1]);
			result.add(node);
		}
		return result;
	}
	/**
	 * 	字符出现次数表 序列化
	 * */
	public static String sourceSerialize(String source) {
		LinkedList<ContentNode> inputNodes = new LinkedList<>();
		for(char i : source.toCharArray()) {
			ContentNode node = (ContentNode)inputNodes.stream().filter(item->((ContentNode)item).data.equals(String.valueOf(i)))
				.findFirst().orElse(null);
			if(node == null) {
				inputNodes.add(new ContentNode(String.valueOf(i)));
			}else {
				node.score++;
			}
		}
		StringBuilder result = new StringBuilder();
		inputNodes.forEach(node->{
			result.append(node.data+":"+node.score+",");
		});
		if(result.length() > 0) {
			result.deleteCharAt(result.lastIndexOf(","));
		}
		return result.toString();
	}
	public static String encodeHex(String source, StringBuilder codeTale) {
		/*把字符转换为一个一个的Node*/
		LinkedList<AbstractNode> inputNodes = new LinkedList<>();
		for(char i : source.toCharArray()) {
			ContentNode node = (ContentNode)inputNodes.stream().filter(item->((ContentNode)item).data.equals(String.valueOf(i)))
				.findFirst().orElse(null);
			if(node == null) {
				inputNodes.add(new ContentNode(String.valueOf(i)));
			}else {
				node.score++;
			}
		}
		codeTale.append(serialzeNodes(inputNodes));
		/*排序*/
		inputNodes.sort((n0, n1)-> n0.score - n1.score);
		inputNodes.forEach(System.out::println);
		
		/*生成树*/
		Deque<AbstractNode> inputDeque = inputNodes;
		while(inputDeque.size() > 1) {
			WeightNode weightNode = new WeightNode();
			AbstractNode n0 = inputDeque.pollFirst();
			AbstractNode n1 = inputDeque.pollFirst();
			weightNode.score = n0.score + n1.score;
			//节点，左小，右大
			if(n0.score > n1.score) { 
				weightNode.leftChild = n1;
				weightNode.rightChild = n0;
			}else {
				weightNode.leftChild = n0;
				weightNode.rightChild = n1;
			}
			inputDeque.offerFirst(weightNode);
		}
		AbstractNode root = inputNodes.peek();
		/*构建二进制编码*/
		build(root, "");
		
		
		StringBuilder encodeBinary = new StringBuilder();
		/*遍历字符串进行编码并拼接*/
		for(char i : source.toCharArray()) {
			AtomicReference<String> result = new AtomicReference<>();
			//编码
			encode(root, String.valueOf(i), result);
			//拼接
			encodeBinary.append(result.get());
		}
		//十六进制对齐
		int mode = encodeBinary.length() % 4;
		if(mode > 0) {
			int fillSize = 4 - mode; //即时是整除也补0，例如整除 则填充4个
			for(int i =0; i < fillSize; i++) {
				encodeBinary.append("0");
			}
			encodeBinary.append(fillSize);
		}else {
			encodeBinary.append("5"); //代表整除4
		}
		//十六进制结果
		StringBuilder encodeHex = new StringBuilder();
		for(int i = 0; i < encodeBinary.length() - 1; i+=4) {
			encodeHex.append(hexMap.get(encodeBinary.substring(i, i + 4)));
		}
		encodeHex.append(encodeBinary.charAt(encodeBinary.length() - 1));
		return encodeHex.toString();
	}
	public static String decodeHex(String codeTable, String hex) {
		LinkedList<AbstractNode> inputNodes = unserialzeNodes(codeTable);
		/*排序*/
		inputNodes.sort((n0, n1)-> n0.score - n1.score);
		inputNodes.forEach(System.out::println);
		
		/*生成树*/
		Deque<AbstractNode> inputDeque = inputNodes;
		while(inputDeque.size() > 1) {
			WeightNode weightNode = new WeightNode();
			AbstractNode n0 = inputDeque.pollFirst();
			AbstractNode n1 = inputDeque.pollFirst();
			weightNode.score = n0.score + n1.score;
			//节点，左小，右大
			if(n0.score > n1.score) { 
				weightNode.leftChild = n1;
				weightNode.rightChild = n0;
			}else {
				weightNode.leftChild = n0;
				weightNode.rightChild = n1;
			}
			inputDeque.offerFirst(weightNode);
		}
		AbstractNode root = inputNodes.peek();
		/*构建二进制编码*/
		build(root, "");
		StringBuilder binary = new StringBuilder();
		int lastDigitIndex = hex.length() -1; //最后一个数字的索引
		for(int i =0; i < hex.length() -1 ; i++) {
			binary.append(binaryMap.get(String.valueOf(hex.charAt(i))));
		}
		int fillSize = Integer.parseInt(String.valueOf(hex.charAt(lastDigitIndex)));
		if(fillSize < 5) {
			binary.delete(binary.length() - fillSize, binary.length());
		}
		return decode(root, binary);
	}
	
	/**
	 * @param code 需要设置的code 
	 * */
	public static void build(AbstractNode node, String code) {
		node.code = code;
		if(node instanceof WeightNode) {
			WeightNode weightNode = (WeightNode) node;
			build(weightNode.leftChild, code + "0");
			build(weightNode.rightChild, code + "1");
		}
	}
	/**
	 * 一长串字符二进制串解码
	 * @param index 找到的下标
	 * */
	public static String decode(AbstractNode root, StringBuilder sourceBinary) {
		StringBuilder decodeData = new StringBuilder();
		int index = 0;
		while(index < sourceBinary.length()) {
			AbstractNode cursor = root;
			while(cursor instanceof WeightNode) {
				char c = sourceBinary.charAt(index);
				if(c == '0') {
					cursor = ((WeightNode)cursor).leftChild;
				}else if(c == '1'){
					cursor = ((WeightNode)cursor).rightChild;
				}else {
					throw new RuntimeException("压缩文件有错");
				}
				index++;
			}
			decodeData.append(((ContentNode)cursor).data);
		}
		return decodeData.toString();
	}
	/**
	 * 编码，把正常的字符串编为二进制
	 * */
	public static void encode(AbstractNode node, String find, AtomicReference<String> result) {
		if(result.get() == null) {
			if(node instanceof WeightNode) {
				WeightNode weightNode = (WeightNode) node;
				encode(weightNode.leftChild, find, result);
				encode(weightNode.rightChild, find, result);
			}else {
				if(((ContentNode)node).data.equals(find)) {
					result.set(node.code);
				}
			}
		}
	}
}
