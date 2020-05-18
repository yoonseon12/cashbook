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
import com.gdu.cashbook.vo.MemberForm;

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
			model.addAttribute("memberIdCheckMsg", msg);
		}else { // 아이디 사용 불가
			String msg = "사용할 수 없는 아이디 입니다.";
			model.addAttribute("memberIdCheckMsg", msg);
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
	public String addMember(HttpSession session, Model model, MemberForm memberForm) { // 폼에서 받을 이름이랑 VO 데이터 이름이랑 같으니 매개변수 member 사용
		if(session.getAttribute("loginMember")!=null){ // 로그인 O
			return "redirect:/index";
		}
		System.out.println(memberForm.getMemberPic()+" <<<<<<<<<<<<<<<");
		System.out.println(memberForm.toString()+" <- toString"); // member.toString() getter,setter와 같이 이클립스에서 사용자 편의를 위해 제공해주는 서비스
		System.out.println(memberForm+" <- MemberController.addMember.Post: memberForm");
		int row = memberService.addMember(memberForm); 
		System.out.println(row+" <- MemberController.addMember.Post: row");
		if(row==0) { // 회원가입 실패
			System.out.println("회원가입 실패");
			String addMemberMsg = "회원가입에 실패했습니다.";
			model.addAttribute("addMemberMsg", addMemberMsg);
			return "addMember";
		}
		if(row==2) { // 사진이 아닌파일 첨부
			System.out.println("첨부한파일이 사진파일이 아님");
			String memberPicMsg = "png, jpg, gif 파일만 첨부할 수 있습니다.";
			model.addAttribute("memberPicMsg", memberPicMsg);
			return "addMember";
		}
		System.out.println("회원가입 성공");
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
	public String login(HttpSession session, Model model, LoginMember loginMember) { // 모델2에서의 HttpSession session = request.getSession();
		if(session.getAttribute("loginMember")!=null){ // 로그인 상태 O
			return "redirect:/index";
		}
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println(returnLoginMember+" <- returnLoginMember");
		if(returnLoginMember == null) {
			System.out.println("로그인 실패");
			String msg="아이디 또는 비밀번호가 일치하지 않습니다.";
			model.addAttribute("msg", msg);
			return "login";
		}
		if(returnLoginMember != null) {
			System.out.println("로그인 성공");
			session.setAttribute("loginMember", returnLoginMember);
			System.out.println(loginMember+" <- loginMember");
		}
		return "redirect:/home";
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
	
	// 회원정보
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null){ // 로그인 상태 X
			return "redirect:/index";
		}
		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		Member memberOne = memberService.getMemberOne(loginMember); 
		model.addAttribute("memberOne", memberOne);
		return "memberInfo";
	}
	// 회원 탈퇴
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam("inputMemberPw") String inputMemberPw, HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태 X
			return "redirect:/index";
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		member.setMemberId(loginMember.getMemberId()); // 세션에 저장되있던 아이디값 주입
		member.setMemberPw(inputMemberPw); // 입력한 비밀번호 값 주입
		int debugingCount = memberService.removeMemberPwChack(member);
		System.out.println(debugingCount);
		if(debugingCount==0) {
			System.out.println("비밀번호틀림");
			String removeMsg = "비밀번호가 일치하지 않습니다.";
			model.addAttribute("removeMsg", removeMsg);
			Member memberOne = memberService.getMemberOne(loginMember); 
			model.addAttribute("memberOne", memberOne);
			return "memberInfo";
		}
		memberService.removeMember(loginMember);
		return "redirect:/logout";
	}
	// 회원 수정
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태 X
			return "redirect:/index";
		}
		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		Member memberOne = memberService.getMemberOne(loginMember); 
		model.addAttribute("memberOne", memberOne);
		return"modifyMember";
	}
	@PostMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model, MemberForm memberForm) {
		System.out.println(memberForm+" <- modifyMember Member member");
		if(session.getAttribute("loginMember")==null) { // 로그인상태 X
			return "redirect:/index";
		}
		int row = memberService.modifyMember(memberForm);
		System.out.println(row+" <- MemberController.modifyMember: row");
		if(row==2) {
			System.out.println("첨부한파일이 사진파일이 아님");
			LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
			Member memberOne = memberService.getMemberOne(loginMember); 
			model.addAttribute("memberOne", memberOne);
			String modifyMsg = "png, jpg, gif 파일만 첨부할 수 있습니다.";
			model.addAttribute("modifyMsg", modifyMsg);
			return "modifyMember";
		}
		if(row==1) {
			System.out.println("수정완료");
		}
		return "redirect:/memberInfo";
	}
	// 아이디 찾기
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember")!=null) { // 로그인상태 O
			return "redirect:/index";
		}
		return "findMemberId";
	}
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember")!=null) { // 로그인상태 O
			return "redirect:/index";
		}
		String memberIdPart = memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart);
		if(memberIdPart==null) {
			String msg = "입력한 정보로 아이디를 찾을 수 없습니다.";
			model.addAttribute("msg", msg);
			return "findMemberId";
		}
		model.addAttribute("memberIdPart", memberIdPart);
		return "memberIdView";
	}
	// 비밀번호 찾기
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if(session.getAttribute("loginMember")!=null) { // 로그인상태 O
			return "redirect:/index";
		}
		return "findMemberPw";
	}
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember")!=null) { // 로그인상태 O
			return "redirect:/index";
		}
		int row = memberService.getMemberPw(member);
		if(row==0) { 
			String msg = "가입되지 않은 정보입니다.";
			model.addAttribute("msg", msg);
			return"findMemberPw";
		}
		return "memberPwView";
	}
	// 비밀번호 변경
	@PostMapping("/modifyMemberPw")
	public String modifyMemberPw(@RequestParam("originMemberPw") String originMemberPw, 
								 @RequestParam("modifyMemberPw") String modifyMemberPw, 
								 HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태 X
			return "redirect:/index";
		}
		System.out.println(originMemberPw+" <- 입력한 비밀번호");
		System.out.println(modifyMemberPw+" <- 수정할 비밀번호");
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		System.out.println(loginMember.getMemberId()+" session Id?");
		member.setMemberId(loginMember.getMemberId()); // 세션에 저장되있던 아이디값 주입
		member.setMemberPw(originMemberPw); // 입력한 기존비밀번호 값 주입
		System.out.println(member+"<- 주입확인");
		int debugingCount = memberService.removeMemberPwChack(member);
		System.out.println(debugingCount+" <- 비밀번호 일치 유무");
		if(debugingCount==0) {
			System.out.println("비밀번호틀림");
			String modifyMsg = "비밀번호가 일치하지 않습니다.";
			model.addAttribute("modifyMsg", modifyMsg);
			Member memberOne = memberService.getMemberOne(loginMember); 
			model.addAttribute("memberOne", memberOne);
			return "memberInfo";
		}
		member.setMemberPw(modifyMemberPw); // 입력한 변경비밀번호 주입(덮어쓰기)
		System.out.println(member+" <- 덮어쓰기확인");
		memberService.modifyMemberInfoMemberPw(member);
		return "redirect:/logout";
	}
}
