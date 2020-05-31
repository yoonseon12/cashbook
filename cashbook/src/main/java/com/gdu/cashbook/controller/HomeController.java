package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/home")
	public String home(HttpSession session) {
		if(session.getAttribute("loginMember")==null && session.getAttribute("loginAdmin")==null) {
			return "redirect:/login";
		}
		System.out.println(session.getAttribute("loginMember")+" 로그인 회원");
		System.out.println(session.getAttribute("loginAdmin")+" 로그인 관리자");
		return "home";
	}
}
