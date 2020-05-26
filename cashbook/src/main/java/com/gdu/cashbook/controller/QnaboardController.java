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

import com.gdu.cashbook.service.QnaboardService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Qnaboard;

@Controller
public class QnaboardController {
	@Autowired private QnaboardService qnaboardService;
	// 게시글 목록
	@GetMapping({"/qnaboard","/board"})
	public String qnaboardList(HttpSession session, Model model, Qnaboard qnaboard,
								@RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(currentPage+" <- 현재페이지");
		model.addAttribute("currentPage", currentPage);
		final int rowPerPage = 10;
		
		String memberId = ((LoginMember) (session.getAttribute("loginMember"))).getMemberId();
		System.out.println(memberId+" <- memberId");
		Map<String, Object> map = new HashMap<>();
		map = qnaboardService.getQnaboardList(memberId, currentPage, rowPerPage);
		List<Qnaboard> qnaboardList = (List<Qnaboard>) map.get("qnaboardList");
		for(Qnaboard q : qnaboardList) {
			System.out.println(q);
		}
		model.addAttribute("qnaboardList", qnaboardList);
		model.addAttribute("lastPage", map.get("lastPage"));
		return "qnaboard";
	}
}
