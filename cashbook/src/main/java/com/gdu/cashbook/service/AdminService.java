package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;		
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.*;
import com.gdu.cashbook.vo.*;
@Service
@Transactional
public class AdminService {
	@Autowired private AdminMapper adminMapper;
	// 관리자 로그인
	public LoginAdmin login(LoginAdmin loginAdmin) {
		return adminMapper.selectLoginAdmin(loginAdmin);
	}
	// 게시글 출력(모든회원 게시글 확인)
	public Map<String, Object> getQnaboardListAll(int currentPage, int rowPerPage, String searchWord){
		int beginRow = (currentPage-1)*rowPerPage;
		System.out.println(beginRow+" <- AdminService.getQnaboardListAll: beginRow");
		int totalCount = adminMapper.selectQnaboardCount();
		System.out.println(totalCount+" <- AdminService.getQnaboardListAll: totalCount");
		int lastPage = totalCount/rowPerPage;
		if(totalCount%rowPerPage!=0) {
			lastPage += 1;
		}
		System.out.println(lastPage+"<- AdminService.getQnaboardListAll: lastPage");
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("beginRow", beginRow);
		inputMap.put("rowPerPage", rowPerPage);
		inputMap.put("searchWord", searchWord);
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("qnaboardList", adminMapper.selectQnaboardListAll(inputMap));
		outputMap.put("lastPage", lastPage);
		outputMap.put("totalCount", totalCount);
		return outputMap;
	}
	// 회원 목록 확인
	public Map<String, Object> getMemberListAll(int currentPage, int rowPerPage, String searchWord){
		int beginRow = (currentPage-1)*rowPerPage;
		int totalCount = adminMapper.selectMemberCount();
		System.out.println(totalCount+" <- AdminService.getMemberListAll: totalCount");
		int lastPage = totalCount/rowPerPage;
		if(totalCount%rowPerPage!=0) {
			lastPage += 1;
		}
		System.out.println(lastPage+"<- AdminService.getMemberListAll: lastPage");
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("beginRow", beginRow);
		inputMap.put("rowPerPage", rowPerPage);
		inputMap.put("searchWord", searchWord);
		Map<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("memberList", adminMapper.selectMemberListAll(inputMap)); // 회원 목록
		outputMap.put("totalCount", totalCount); // 전체 회원 수
		outputMap.put("lastPage", lastPage); // 마지막 페이지
		return outputMap;
	}
	// 회원 정보 확인
	public Member getMemberOne(String memberId) {
		return adminMapper.selectMemberOne(memberId);
	}
}
