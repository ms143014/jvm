package com.jvm.source.b;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.jvm.source.b.PrintableNode.Line;
import com.jvm.source.b.PrintableNode.PrintableItem;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-02 20:52:54
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class PrintableUtils {
	/**
	 * 方便调试的Map用于收集PrintableNode，快速定位节点
	 * */
	private static Map<Integer, PrintableNode> map = new HashMap<>();
	/**
	 * 后续层级遍历
	 * @param height 树的高度
	 * @param depth 遍历的深度
	 * */
	public static PrintableNode postTravel(Node node, AtomicInteger leafX, int depth) {
		
		PrintableNode printableNode = new PrintableNode();
		if(node.isLeaf()) {
			int beforeLeafX = leafX.get(); //用于计算节点宽度
			
			for(int j=0; j< node.getItemSize(); j++) {
				
				
				int cellValue = node.getItem(j);
				if(j==0) {map.put(cellValue, printableNode);}
				
				PrintableItem printableItem = new PrintableItem();
				printableItem.setX(leafX.get());
				printableItem.setY(100 * depth);
				printableItem.setValue(cellValue);
				//字符宽度是10px
				//数字之间留一点间隔 
				printableItem.setWidth(10 * String.valueOf(cellValue).length() + PrintableNode.ITEM_SPACING);
				printableNode.getItems().add(printableItem);
				
				leafX.getAndAdd(printableItem.getWidth());
				
			}
			printableNode.setX(beforeLeafX);
			printableNode.setY(100 * depth);
			printableNode.setWidth(leafX.get() - beforeLeafX); //计算节点宽度
			
			leafX.getAndAdd(15); //叶子节点相隔20px
		}else {
			int widthSum = 0;
			List<PrintableNode> childs = printableNode.getChilds();
			for(int i=0; i < node.getItemSize() + 1; i++) {
				
				
				childs.add(postTravel(node.getChild(i), leafX, depth + 1));
				
				if(i < node.getItemSize()) {
					int cellValue = node.getItem(i);
					
					if(i==0) {map.put(cellValue, printableNode);}
					
					PrintableItem printableItem = new PrintableItem();
					printableItem.setY(100 * depth);
					printableItem.setValue(cellValue);
					//字符宽度是10px
					//数字之间留一点间隔 
					printableItem.setWidth(10 * String.valueOf(cellValue).length() + PrintableNode.ITEM_SPACING);
					printableNode.getItems().add(printableItem);
					
					widthSum += 10 * String.valueOf(cellValue).length(); //字符宽度是10px
					
					widthSum += PrintableNode.ITEM_SPACING;
					
				}
			}
			//孩子全部创建完毕
			PrintableItem firstItem = childs.get(0).getItems().get(0);
			PrintableNode lastChild = childs.get(childs.size() - 1);
			PrintableItem lastItem = lastChild.getItems().get(lastChild.getItems().size() - 1);
			printableNode.setWidth(widthSum); 
			printableNode.setNodeWidth(lastItem.getX() + lastItem.getWidth() - firstItem.getX());
			printableNode.setX(firstItem.getX() + (printableNode.getNodeWidth()/2 - printableNode.getWidth()/2));
			printableNode.setY(100 * depth);
			
			int parentX = printableNode.getX();
			
			//父的位置确定后才计算Item的x坐标
			for(int i=0; i< printableNode.getItems().size(); i++) {
				PrintableItem item = printableNode.getItems().get(i);
				if(i == 0) {
					item.setX(parentX);
				}else {
					PrintableItem prevItem = printableNode.getItems().get(i -1);
					item.setX(prevItem.getX() + prevItem.getWidth());
				}
				
				PrintableNode child = childs.get(i);
				
				//设置链接线
				Line line = new Line();
				line.setX0(item.getX()); //左节点X
				line.setY0(item.getY() + 20); //左节点Y
				child = childs.get(i);
				line.setX1(child.getX() + child.getWidth() /2); //子节点中间X
				line.setY1(child.getY()); //子节点中间Y
				child.setJoinParent(line);
				
				if(i == printableNode.getItems().size() - 1) {
					line = new Line();
					line.setX0(item.getX() + item.getWidth()); //左节点X
					line.setY0(item.getY() + 20); //左节点Y
					child = childs.get(i + 1);
					line.setX1(child.getX() + child.getWidth() /2); //子节点中间X
					line.setY1(child.getY()); //子节点中间Y
					child.setJoinParent(line);
				}
			}
		}
		return printableNode;
	}
}
