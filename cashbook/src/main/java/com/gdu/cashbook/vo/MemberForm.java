package com.gdu.cashbook.vo;

import org.springframework.web.multipart.MultipartFile;

public class MemberForm {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberAddr;
	private String memberPhone;
	private String memberEmail;
	private MultipartFile memberPic;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberAddr() {
		return memberAddr;
	}
	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public MultipartFile getMemberPic() {
		return memberPic;
	}
	public void setMemberPic(MultipartFile memberPic) {
		this.memberPic = memberPic;
	}
	@Override
	public String toString() {
		return "MemberForm [memberId=" + memberId + ", memberPw=" + memberPw + ", memberName=" + memberName
				+ ", memberAddr=" + memberAddr + ", memberPhone=" + memberPhone + ", memberEmail=" + memberEmail
				+ ", memberPic=" + memberPic + "]";
	}
	// memberForm을 member에 주입해주는 사용자 정의 메서드
	public Member memberSetBymemberForm(Member member, MemberForm memberForm, String memberPic) {
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberPic(memberPic);
		return member;
	}
}
