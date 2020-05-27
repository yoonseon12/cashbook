package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Comment;

@Mapper
public interface CommentMapper {
	// 댓글 추가
	public void insertComment(Comment comment);
	// 댓글리스트 출력
	public List<Comment> selectCommentListAll(Map<String, Object> map);
	// 게시글 별 댓글 총 개수
	public int selectCommentCount(Map<String, Object> map);
	// 댓글 삭제
	public void deleteComment(int qnaboardNo, int commentNo);
	// 댓글 수정 시 댓글 내용 출력
	public Comment selectCommentListOne(Map<String, Object> map);
	// 댓글 수정
	public void updateComment(Comment comment);
}
