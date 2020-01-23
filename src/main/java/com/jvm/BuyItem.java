package com.jvm;

import java.util.Date;

public class BuyItem {
	private Long id;
	private Long productId;
	private Date createDate;
	private String remark;
	public BuyItem() {
		System.out.println(this.getClass() + " - 默认构造函数执行了");
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
