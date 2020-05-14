package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Service // 트랜잭션 처리 가능
@Transactional // 실행하다가 예외가 발생하면 트랜잭션 작업 취소
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	// 아이디 중복확인
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck); // 아이디없으면 null, 있으면 
	}
	// 회원가입
	public void addMember(Member member) {
		memberMapper.insertMember(member);
	}
	// 로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	// 회원 정보
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	// 회원 탈퇴
	public void removeMember(LoginMember loginMember) {
		memberMapper.deleteMember(loginMember);
	}
	// 회원 탈퇴시 정보 확인
	public int removeMemberPwChack(Member member) {
		System.out.println(member+" < member");
		return memberMapper.removeMemberPwChack(member);
	}
}
