package com.gdu.cashbook.vo;

public class Category {
	public String categoryName;
	public String memberId;
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", memberId=" + memberId + "]";
	}
}
