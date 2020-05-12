package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addMember(Member member) { // 폼에서 받을 이름이랑 VO 데이터 이름이랑 같으니 매개변수 member 사용
		System.out.println(member.toString()); // member.toString() getter,setter와 같이 이클립스에서 사용자 편의를 위해 제공해주는 서비스
		memberService.addMember(member);
		return "redirect:/index";
	}
	
}
