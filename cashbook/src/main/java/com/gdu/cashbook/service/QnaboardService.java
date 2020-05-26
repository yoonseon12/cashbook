package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.QnaboardMapper; 
import com.gdu.cashbook.vo.Qnaboard;

@Service
@Transactional
public class QnaboardService {
	@Autowired private QnaboardMapper qnaboardMapper;
	// 게시글 목록
	public Map<String, Object> getQnaboardList(String memberId, int currentPage, int rowPerPage){
		int beginRow = (currentPage-1)*rowPerPage;
		System.out.println(beginRow+" <- QnaboardService.getQnaboardList: beginRow");
		int totalCount = qnaboardMapper.selectQnaboardCount(memberId);
		System.out.println(totalCount+" <- QnaboardService.getQnaboardList: totalCount");
		int lastPage = totalCount/rowPerPage;
		if(totalCount%rowPerPage!=0) {
			lastPage += 1;
		}
		System.out.println(lastPage+"<- QnaboardService.getQnaboardList: lastPage");
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
}
