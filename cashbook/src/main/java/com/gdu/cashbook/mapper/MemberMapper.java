package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper // @Component+ @Mapper
public interface MemberMapper {
	// 아이디 중복확인
	public String selectMemberId(String memberIdCheck);
	// 회원가입(맴버 추가)
	public int insertMember(Member member);
	// 로그인
	public LoginMember selectLoginMember(LoginMember loginMember);
	// 회원 정보
	public Member selectMemberOne(LoginMember loginMember);
	// 회원 탈퇴
	public void deleteMember(LoginMember loginMember);
	// 회원 탈퇴시 폴더에 저장된 프로필사진 조회
	public String selectMemberPic(String memberPic);
	// 회원 탈퇴시 비밀번호 확인
	public int deleteMemberPwChack(Member member);
	// 회원 수정
	public int updateMember(Member member);
	// 아이디 찾기
	public String selectMemberIdByMember(Member member);
	// 비밀번호 찾기
	public int updateMemberPw(Member member);
	// 비밀번호 변경
	public void updateMemberInfoMemberPw(Member member);
}
