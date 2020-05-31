package com.gdu.cashbook.vo;

public class LoginAdmin {
	private String adminId;
	private String adminPw;
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminPw() {
		return adminPw;
	}
	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}
	@Override
	public String toString() {
		return "LoginAdmin [adminId=" + adminId + ", adminPw=" + adminPw + "]";
	}
	
}
