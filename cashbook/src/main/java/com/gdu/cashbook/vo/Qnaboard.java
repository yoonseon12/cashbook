package com.gdu.cashbook.vo;

public class Qnaboard {
	private int qnaboardNo;
	private String memberId;
	private String qnaboardTitle;
	private String qnaboardContent;
	private String qnaboardDate;
	private int qnaboardParent;
	private int qnaboardReNo;
	private int qnaboardReLevel;
	public int getQnaboardNo() {
		return qnaboardNo;
	}
	public void setQnaboardNo(int qnaboardNo) {
		this.qnaboardNo = qnaboardNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getQnaboardTitle() {
		return qnaboardTitle;
	}
	public void setQnaboardTitle(String qnaboardTitle) {
		this.qnaboardTitle = qnaboardTitle;
	}
	public String getQnaboardContent() {
		return qnaboardContent;
	}
	public void setQnaboardContent(String qnaboardContent) {
		this.qnaboardContent = qnaboardContent;
	}
	public String getQnaboardDate() {
		return qnaboardDate;
	}
	public void setQnaboardDate(String qnaboardDate) {
		this.qnaboardDate = qnaboardDate;
	}
	public int getQnaboardParent() {
		return qnaboardParent;
	}
	public void setQnaboardParent(int qnaboardParent) {
		this.qnaboardParent = qnaboardParent;
	}
	public int getQnaboardReNo() {
		return qnaboardReNo;
	}
	public void setQnaboardReNo(int qnaboardReNo) {
		this.qnaboardReNo = qnaboardReNo;
	}
	public int getQnaboardReLevel() {
		return qnaboardReLevel;
	}
	public void setQnaboardReLevel(int qnaboardReLevel) {
		this.qnaboardReLevel = qnaboardReLevel;
	}
	@Override
	public String toString() {
		return "Qnaboard [qnaboardNo=" + qnaboardNo + ", memberId=" + memberId + ", qnaboardTitle=" + qnaboardTitle
				+ ", qnaboardContent=" + qnaboardContent + ", qnaboardDate=" + qnaboardDate + ", qnaboardParent="
				+ qnaboardParent + ", qnaboardReNo=" + qnaboardReNo + ", qnaboardReLevel=" + qnaboardReLevel + "]";
	}
}
