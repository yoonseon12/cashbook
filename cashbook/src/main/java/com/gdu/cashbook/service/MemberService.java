package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberIdMapper;
import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberId;

@Service // 트랜잭션 처리 가능
@Transactional // 실행하다가 예외가 발생하면 트랜잭션 작업 취소
public class MemberService {
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberIdMapper memberIdMapper;
	@Autowired private JavaMailSender javaMailSender;
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
		System.out.println(loginMember);
		return memberMapper.selectLoginMember(loginMember);
	}
	// 회원 정보
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	// 회원 탈퇴
	public void removeMember(LoginMember loginMember) {
		//1.
		memberMapper.deleteMember(loginMember);
		//2.
		MemberId memberId= new MemberId();
		memberId.setMemberId(loginMember.getMemberId());
		memberIdMapper.insertMemberId(memberId);
	}
	// 회원 탈퇴시 정보 확인
	public int removeMemberPwChack(Member member) {
		System.out.println(member+" < member");
		return memberMapper.deleteMemberPwChack(member);
	}
	// 회원 정보 수정
	public void modifyMember(Member member) {
		System.out.println(member+" < member");
		memberMapper.updateMember(member);
	}
	// 아이디 찾기
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	// 비밀번호 찾기
	public int getMemberPw(Member member) { // member 매개변수안에는 id&email
		System.out.println();
		System.out.println();
		System.out.println(member.getMemberEmail());
		System.out.println();
		System.out.println();
		System.out.println();
		UUID uuid = UUID.randomUUID(); // 자바 랜덤 문자열 생성 ? 
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw); // pw를 추가해줘야 리턴가능
		int row = memberMapper.updateMemberPw(member);
		System.out.println(row+" <- 업데이트 유무");
		if(row==1) {
			System.out.println("업데이트 성공"); // JavaMailSender 메일객체
			System.out.println(memberPw+" <- 변경된 비밀번호");
			//메일보내기
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail()); // 받는사람주소
			simpleMailMessage.setFrom("a0105457331@gmail.com"); // 보내는사람주소
			simpleMailMessage.setSubject("[cashbook]비밀번호찾기"); // 제목
			simpleMailMessage.setText("변경된 비밀번호는[ "+memberPw+" ]입니다."); // 내용
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	// 비밀번호 변경
	public void modifyMemberInfoMemberPw(Member member) {
		memberMapper.updateMemberInfoMemberPw(member);
	}  
}
