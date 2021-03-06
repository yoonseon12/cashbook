package com.gdu.cashbook.mapper;

import java.util.List;	
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Qnaboard;

@Mapper
public interface QnaboardMapper {
	// 게시글 목록
	public List<Qnaboard> selectQnaboardListAll(Map<String, Object> map);
	// 회원의 게시글 총 개수
	public int selectQnaboardCount(String memberId);
	// 게시글 추가
	public void insestQnaboard(Qnaboard qnaboard);
	// 게시글 상세보기
	public Qnaboard selectQnaboardListOne(Map<String, Object> map);
	// 마지막 게시글 번호 조회(회원용)
	public int selectQnaboardNoMax(String memberId);
	// 처음 게시글 번호 조회(회원용)
	public int selectQnaboardNoMin(String memberId);
	// 다음 게시글 번호 조회(회원용)
	public int selectQnaboardNoNext(Map<String, Object> map);
	// 이전 게시글 번호 조회(회원용)
	public int selectQnaboardNoPrevious(Map<String, Object> map);
	// 게시글 삭제
	public void deleteQnaboard(int qnaboardNo);
	// 게시글 수정
	public void updateQnaboard(Qnaboard qnaboard);
	// 회원탈퇴 시 게시글 삭제
	public void memberMeleteQnaboard(String memberId);
	// 게시글 번호로 회원 아이디 출력(관리자모드일 때 memberId 찾기위해서)
	public String selectMemberId(int qnaboardNo);
	// 마지막 게시글 번호 조회(관리자용)
	public int selectAdminQnaboardNoMax();
	// 처음 게시글 번호 조회(관리자용)
	public int selectAdminQnaboardNoMin();
	// 다음 게시글 번호 조회(관리자용)
	public int selectAdminQnaboardNoNext(int qnaboardNo);
	// 이전 게시글 번호 조회(관리자용)
	public int selectAdminQnaboardNoPrevious(int qnaboardNo);
}
