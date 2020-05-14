package com.gdu.cashbook.vo;

public class MemberId {
	public int memberIdNo;
	public String memberId;
	public int getMemberIdNo() {
		return memberIdNo;
	}
	public void setMemberIdNo(int memberIdNo) {
		this.memberIdNo = memberIdNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		return "MemberId [memberIdNo=" + memberIdNo + ", memberId=" + memberId + "]";
	}
	
}
