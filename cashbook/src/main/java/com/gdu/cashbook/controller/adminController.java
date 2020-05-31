package com.gdu.cashbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.vo.Qnaboard;

import org.springframework.ui.Model;
@Controller
public class adminController {
	@Autowired private AdminService adminService;
	
	@GetMapping("/adminHome")
	// 관리자 홈
	public String adminHome(HttpSession session) {
		if(session.getAttribute("loginAdmin")==null && session.getAttribute("loginMember")==null) { // 관리자 , 회원 로그인상태 X
			return "redirect:/login";
		}
		return "/admin/adminHome";
	}
	
	@GetMapping("/adminQnaboard")
	//관리자 게시판(모든 회원 게시글 확인)
	public String adminQnaboard(HttpSession session, Model model,
								@RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginAdmin")==null && session.getAttribute("loginMember")==null) { // 관리자 , 회원 로그인상태 X
			return "redirect:/login";
		}
		System.out.println(currentPage+" <- 현재페이지");
		model.addAttribute("currentPage", currentPage);
		final int rowPerPage = 10;
		
		Map<String, Object> map = new HashMap<>();
		map = adminService.getQnaboardListAll(currentPage, rowPerPage);
		List<Qnaboard> qnaboardList = (List<Qnaboard>) map.get("qnaboardList");
		model.addAttribute("qnaboardList", qnaboardList); // 작성된 모든 게시글 목록
		model.addAttribute("lastPage", map.get("lastPage")); // 마지막페이지
		for(Qnaboard q : qnaboardList) {
			System.out.println(q+" <<");
		}
		return "qnaboard/qnaboardList";
	}
}
