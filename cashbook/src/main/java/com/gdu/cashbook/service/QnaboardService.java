package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.mapper.QnaboardMapper; 
import com.gdu.cashbook.vo.Qnaboard;

@Service
@Transactional
public class QnaboardService {
	@Autowired private QnaboardMapper qnaboardMapper;
	@Autowired private CommentMapper commentMapper;
	// 게시글 목록
	public Map<String, Object> getQnaboardListAll(String memberId, int currentPage, int rowPerPage){
		int beginRow = (currentPage-1)*rowPerPage;
		System.out.println(beginRow+" <- QnaboardService.getQnaboardListAll: beginRow");
		int totalCount = qnaboardMapper.selectQnaboardCount(memberId);
		System.out.println(totalCount+" <- QnaboardService.getQnaboardListAll: totalCount");
		int lastPage = totalCount/rowPerPage;
		if(totalCount%rowPerPage!=0) {
			lastPage += 1;
		}
		System.out.println(lastPage+"<- QnaboardService.getQnaboardListAll: lastPage");
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("memberId", memberId);
		inputMap.put("beginRow", beginRow);
		inputMap.put("rowPerPage", rowPerPage);
		
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("qnaboardList", qnaboardMapper.selectQnaboardListAll(inputMap));
		outputMap.put("lastPage", lastPage);
		return outputMap;
	}
	// 게시글 추가
	public void addQnaboardList(Qnaboard qnaboard) {
		qnaboardMapper.insestQnaboard(qnaboard);
	}
	// 게시글 상세보기
	public Map<String, Object> getQnaboardListOne(int qnaboardNo, String memberId){
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("qnaboardNo", qnaboardNo);
		inputMap.put("memberId", memberId);
		Map<String, Object> outputMap = new HashMap<>();
		outputMap.put("qnaboardOne", qnaboardMapper.selectQnaboardListOne(inputMap)); // 게시글 상세보기		
		outputMap.put("lastQnaboardNo", qnaboardMapper.selectQnaboardNoMax(memberId)); // 마지막 게시글 번호
		outputMap.put("startQnaboardNo", qnaboardMapper.selectQnaboardNoMin(memberId)); // 처음 게시글 번호
		outputMap.put("nextQnaboardNo", qnaboardMapper.selectQnaboardNoNext(inputMap)); // 다음 게시글 번호
		outputMap.put("previousQnaboardNo", qnaboardMapper.selectQnaboardNoPrevious(inputMap)); // 이전 게시글 번호
		return outputMap;
	}
	// 게시글 삭제
	public void removeQnaboard(int qnaboardNo) {
		// 1. 댓글삭제
		commentMapper.qnaboardDeleteComment(qnaboardNo);
		// 2. 게시글 삭제
		qnaboardMapper.deleteQnaboard(qnaboardNo);
	}
	// 게시글 수정
	public void modifyQnaboard(Qnaboard qnaboard) {
		qnaboardMapper.updateQnaboard(qnaboard);
	}
	// 게시글 번호로 회원 아이디 출력(관리자모드일 때 memberId 찾기위해서)
	public String getMemberId(int qnaboardNo) {
		return qnaboardMapper.selectMemberId(qnaboardNo);
	}
}
