package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper // @Component+ @Mapper
public interface MemberMapper {
	// 아이디 중복확인
	public String selectMemberId(String memberIdCheck);
	// 회원가입(맴버 추가)
	public void insertMember(Member member);
	// 로그인
	public LoginMember selectLoginMember(LoginMember loginMember);
	// 회원 정보
	public Member selectMemberOne(LoginMember loginMember);
	// 회원 탈퇴
	public void deleteMember(LoginMember loginMember);
	// 회원 탈퇴시 정보 확인
	public int deleteMemberPwChack(Member member);
	// 회원 수정
	public void updateMember(Member member);
}
