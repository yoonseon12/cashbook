package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;	

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired // @Component가 없다면 객체를 주입할 곳이 없다. 오류가 발생할 것이다.
	private MemberService memberService;
	// 아이디 중복 체크
	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck") String memberIdCheck) {
		if(session.getAttribute("loginMember")!=null){ // 로그인 O
			return "redirect:/index";
		}
		String confirmMemberId = memberService.checkMemberId(memberIdCheck); // null : 사용할 수 있는 아이디
									 		// memberIdCheck : 내가 입력한 아이디	 // null 아님 : 사용중인 아이디(이미 사용한 아이디)
		System.out.println(confirmMemberId+" <- checkMemberId.memberId");
		if(confirmMemberId == null) { // 아이디 사용 가능
			model.addAttribute("availableMemberId", memberIdCheck);
			String msg = "사용할 수 있는 아이디 입니다.";
			model.addAttribute("msg", msg);
		}else { // 아이디 사용 불가
			String msg = "사용할 수 없는 아이디 입니다.";
			model.addAttribute("msg", msg);
		}
		return"addmember";
	}
	// 회원가입
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		if(session.getAttribute("loginMember")!=null){ // 로그인 O
			return "redirect:/index";
		}
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addMember(HttpSession session, Member member) { // 폼에서 받을 이름이랑 VO 데이터 이름이랑 같으니 매개변수 member 사용
		if(session.getAttribute("loginMember")!=null){ // 로그인 O
			return "redirect:/index";
		}
		System.out.println(member.toString()); // member.toString() getter,setter와 같이 이클립스에서 사용자 편의를 위해 제공해주는 서비스
		memberService.addMember(member); // 
		return "redirect:/index";
	}
	
	// 로그인
	@GetMapping("/login")
	public String login(HttpSession session) {
		System.out.println(session.getAttribute("loginMember")+" <- 로그인상태 확인 디버깅");
		if(session.getAttribute("loginMember")!=null){ // 로그인 상태 O
			return "redirect:/index";
		}
		return "login";
	}
	@PostMapping("/login")
	public String login(LoginMember loginMember, Model model, HttpSession session) { // 모델2에서의 HttpSession session = request.getSession();
		if(session.getAttribute("loginMember")!=null){ // 로그인 상태 O
			return "redirect:/index";
		}
		LoginMember returnLoginMember = memberService.login(loginMember);
		if(returnLoginMember == null) {
			System.out.println("로그인 실패");
			String msg="아이디 또는 비밀번호가 일치하지 않습니다.";
			model.addAttribute("msg", msg);
			return "login";
		}
		if(returnLoginMember != null) {
			System.out.println("로그인 성공");
			session.setAttribute("loginMember", loginMember);
			System.out.println(loginMember+" <- loginMember");
		}
		return "redirect:/index";
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("loginMember")==null){ // 로그인 상태 X
			return "redirect:/index";
		}
		session.invalidate(); // session 초기화
		return "redirect:/index";
	}
}
