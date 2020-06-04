package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;	
import com.gdu.cashbook.vo.LoginAdmin;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Qnaboard;

@Mapper
public interface AdminMapper {
	// 관리자 로그인
	public LoginAdmin selectLoginAdmin(LoginAdmin loginAdmin);
	// 게시글 출력(모든회원 게시글 확인)
	public List<Qnaboard> selectQnaboardListAll(Map<String, Object> map);
	// 작성된 게시글 총 개수
	public int selectQnaboardCount();
	// 회원 목록(모든회원 조회)
	public List<Member> selectMemberListAll(Map<String, Object> map);
	// 전체 회원 수
	public int selectMemberCount();
	// 회원정보 확인
	public Member selectMemberOne(String memberId);
}
