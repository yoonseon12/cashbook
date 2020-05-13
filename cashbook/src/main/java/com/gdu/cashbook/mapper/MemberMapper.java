package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	// 아이디 중복확인
	public String selectMemberId(String memberIdCheck);
	// 회원가입(맴버 추가)
	public void insertMember(Member member);
	// 로그인
	public LoginMember selectLoginMember(LoginMember loginMember);
}
