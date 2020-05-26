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
}
