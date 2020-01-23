package com.jvm;

import java.util.Date;
import java.util.List;

public class User {
	private Long id;
	private int i;
	private double d;
	private long l;
	private String name;
	private String username;
	private Date createDate;
	private List<BuyItem> buyItems;
	private BuyItem[] buyItemArray;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public double getD() {
		return d;
	}
	public void setD(double d) {
		this.d = d;
	}
	public long getL() {
		return l;
	}
	public void setL(long l) {
		this.l = l;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<BuyItem> getBuyItems() {
		return buyItems;
	}
	public void setBuyItems(List<BuyItem> buyItems) {
		this.buyItems = buyItems;
	}
	public BuyItem[] getBuyItemArray() {
		return buyItemArray;
	}
	public void setBuyItemArray(BuyItem[] buyItemArray) {
		this.buyItemArray = buyItemArray;
	}
	
}
