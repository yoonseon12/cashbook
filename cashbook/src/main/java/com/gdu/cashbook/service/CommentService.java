package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Comment;

@Service
@Transactional
public class CommentService {
	@Autowired private CommentMapper commentMapper;
	// 댓글 추가
	public void addComment(Comment comment){
		commentMapper.insertComment(comment);
	}
	// 댓글 목록 출력
	public Map<String, Object> getCommentList(String memberId, int qnaboardNo, int currentPage, int rowPerPage){
		System.out.println(memberId+" "+qnaboardNo+" "+currentPage+" "+rowPerPage);
		int beginRow = (currentPage-1)*rowPerPage;
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("memberId", memberId);
		inputMap.put("qnaboardNo", qnaboardNo);
		inputMap.put("beginRow", beginRow);
		inputMap.put("rowPerPage", rowPerPage);
		int totalCount = commentMapper.selectCommentCount(inputMap);
		System.out.println(totalCount+" <- CommentService.getcommentList: totalCount(댓글 총개수)");
		int lastPage = totalCount/rowPerPage;
		if(totalCount%rowPerPage!=0) {
			lastPage += 1;
		}
		System.out.println(lastPage+"<- CommentService.getcommentList: lastPage(댓글 마지막페이지)");
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("commentList", commentMapper.selectCommentListAll(inputMap));// 댓글목록
		outputMap.put("lastPage", lastPage); // 마지막페이지
		outputMap.put("totalCount", totalCount); // 데이터 총개수
		return outputMap;
	}
	// 댓글삭제
	public void removeComment(int qnaboardNo, int commentNo) {
		commentMapper.deleteComment(qnaboardNo, commentNo);
	}
	// 댓글 수정 시 댓글 내용 출력
	public Comment getCommentListOne(int qnaboardNo, int commentNo, String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qnaboardNo", qnaboardNo);
		map.put("commentNo", commentNo);
		map.put("memberId", memberId);
		return commentMapper.selectCommentListOne(map);
	}
	// 댓글 수정
	public void modifyComment(Comment comment) {
		commentMapper.updateComment(comment);
	}
}
