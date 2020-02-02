package com.jvm.source.b;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-02-01 22:02:20
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class PrintableNode{
	/**元素之间空隔4px*/
	public static final int ITEM_SPACING = 4;
	public static class PrintableItem{
		private int x;
		private int y;
		private int value;
		private int width;
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		@Override
		public String toString() {
			return "PrintableItem [x=" + x + ", y=" + y + ", value=" + value + ", width=" + width + "]";
		}
	}
	public static class Line{
		private int x0;
		private int y0;
		private int x1;
		private int y1;
		public int getX0() {
			return x0;
		}
		public void setX0(int x0) {
			this.x0 = x0;
		}
		public int getY0() {
			return y0;
		}
		public void setY0(int y0) {
			this.y0 = y0;
		}
		public int getX1() {
			return x1;
		}
		public void setX1(int x1) {
			this.x1 = x1;
		}
		public int getY1() {
			return y1;
		}
		public void setY1(int y1) {
			this.y1 = y1;
		}
		@Override
		public String toString() {
			return "Line [x0=" + x0 + ", y0=" + y0 + ", x1=" + x1 + ", y1=" + y1 + "]";
		}
	}
	private List<PrintableItem> items = new ArrayList<>();
	private List<PrintableNode> childs = new ArrayList<>(); 
	private int width;
	private int nodeWidth;
	private int x;
	private int y;
	private Line joinParent;
	public void setItems(List<PrintableItem> items) {
		this.items = items;
	}
	public List<PrintableItem> getItems() {
		return items;
	}
	public List<PrintableNode> getChilds() {
		return childs;
	}
	public void setChilds(List<PrintableNode> childs) {
		this.childs = childs;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getNodeWidth() {
		return nodeWidth;
	}
	public void setNodeWidth(int nodeWidth) {
		this.nodeWidth = nodeWidth;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Line getJoinParent() {
		return joinParent;
	}
	public void setJoinParent(Line joinParent) {
		this.joinParent = joinParent;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(PrintableItem item: items) {
			sb.append(item.getValue() + "-");
		}
		if(sb.length() > 0) {
			sb.delete(sb.length() - "-".length(), sb.length());
		}
		return sb.toString();
	}
	
}
