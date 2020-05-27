package com.gdu.cashbook.vo;

public class Comment {
	private int commentNo;
	private int qnaboardNo;
	private String memberId;
	private String commentContent;
	private String commentDate;
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
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
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", qnaboardNo=" + qnaboardNo + ", memberId=" + memberId
				+ ", commentContent=" + commentContent + ", commentDate=" + commentDate + "]";
	}
	
}
