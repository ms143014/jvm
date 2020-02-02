package com.jvm.source.b;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-01 13:11:15
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class NodeTest {
	AtomicInteger incrementId = new AtomicInteger(1);
	/**
	 * 创建一层叶子，通过递归调用就是创建一颗B树
	 * @param layers 层数
	 * */
	private void initLayer(Queue<Node> layerNodes, int layers) {
		if(layers == 0 ) return;
		Queue<Node> nextLayerNodes = new LinkedList<>();
		while(!layerNodes.isEmpty()) {
			Node node = layerNodes.poll();
			for(int i=0; i < node.getItemSize() + 1; i++) {
				Node newNode;
				//叶子有 1 ~ Node.MAX_NODE - 1 个键
				nextLayerNodes.offer(newNode = createNode(1 + new Random().nextInt(Node.MAX_NODE - 1)));
				node.connectChild(i, newNode);
			}
		}
		initLayer(nextLayerNodes, layers - 1);
	}
	/**根据size创建一个key数量为size的叶子*/
	private Node createNode(int size) {
		Node node = new Node(incrementId.getAndIncrement());
		size--;
		IntStream.range(0, size).forEach(__->{
			node.insertItem(incrementId.getAndIncrement());
		});
		return node;
	}
	public void postInJson(Node root) throws Exception{
		DebuggerWebsocket.post(PrintableUtils.postTravel(root, new AtomicInteger(0), 0));
	}
	@Test
	public void s() throws Exception{
		
		//SerializationUtils.serialize(root, new FileOutputStream(new File("./abcd.dat")));
		//Node root = SerializationUtils.deserialize(new FileInputStream("./abcd.dat"));
		//printInJson(root);
		Debugger.startDaemon(()->{
			incrementId.set(1);
			Node root = createNode(Node.MAX_NODE - 1);
			System.out.println(root);
			Queue<Node>layerNodes = new LinkedList<>();
			layerNodes.offer(root);
			initLayer(layerNodes, 2);
			System.out.println("start---");
			System.out.println();
			postInJson(root);
		}).join();
		
		//postTravel(root, new AtomicInteger(0), 1);
		
		
		
	}
}
