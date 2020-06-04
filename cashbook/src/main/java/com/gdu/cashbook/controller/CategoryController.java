package com.gdu.cashbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CategoryService;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CategoryController {
	@Autowired private CategoryService categoryService;
	// 카테고리 목록
	@GetMapping("/categoryList")
	public String categoryList(HttpSession session, Model model) {
		if(session.getAttribute("loginAdmin")!=null) { // 관리자 로그인 O
			return "redirect:/adminHome";
		}
		if(session.getAttribute("loginMember")==null ) { // 회원 로그인상태 X
			return "redirect:/login";
		}
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(memberId+" <- MemberController.categoryList: memberId");
		
		List<Category> categoryList = categoryService.getCategoryList(memberId);
		model.addAttribute("categoryList", categoryList);
		
		return "member/categoryList";
	}
	// 카테고리 삭제
	@GetMapping("/removeCategory")
	public String removeCategory(HttpSession session, @RequestParam(value = "categoryName") String categoryName) {
		System.out.println(categoryName+" <- MemberController.removeCategory: categoryName");
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(memberId);
		categoryService.removeCategory(categoryName, memberId);
		return "redirect:categoryList";
	}
}
