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
	// 게시글 상세보기(게시글 하나 정보 가져오기)
	public Qnaboard selectQnaboardListOne(Map<String, Object> map);
	// 마지막 게시글 번호 조회
	public int selectQnaboardNoMax(String memberId);
	// 처음 게시글 번호 조회
	public int selectQnaboardNoMin(String memberId);
	// 다음 게시글 번호 조회
	public int selectQnaboardNoNext(Map<String, Object> map);
	// 이전 게시글 번호 조회
	public int selectQnaboardNoPrevious(Map<String, Object> map);
	// 게시글 삭제
	public void deleteQnaboard(int qnaboardNo);
	// 게시글 수정
	public void updateQnaboard(Qnaboard qnaboard);
	// 회원탈퇴 시 게시글 삭제
	public void memberMeleteQnaboard(String memberId);
}
