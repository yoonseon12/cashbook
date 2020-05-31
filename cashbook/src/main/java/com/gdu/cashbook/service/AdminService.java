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
	public Map<String, Object> getQnaboardListAll(int currentPage, int rowPerPage){
		int beginRow = (currentPage-1)*rowPerPage;
		System.out.println(beginRow+" <- AdminService.getQnaboardListAll: beginRow");
		int totalCount = adminMapper.selectQnaboardCount();
		System.out.println(totalCount+" <- AdminService.getQnaboardListAll: totalCount");
		int lastPage = totalCount/rowPerPage;
		if(totalCount%rowPerPage!=0) {
			lastPage += 1;
		}
		System.out.println(lastPage+"<- AdminService.getQnaboardListAll: lastPage");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qnaboardList", adminMapper.selectQnaboardListAll(beginRow, rowPerPage));
		map.put("lastPage", lastPage);
		return map;
	}
}
