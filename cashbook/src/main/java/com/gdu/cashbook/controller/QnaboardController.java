package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.QnaboardService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Qnaboard;

@Controller
public class QnaboardController {
	@Autowired private QnaboardService qnaboardService;
	// 게시글 목록
	@GetMapping({"/qnaboard","/qnaboardList"})
	public String qnaboardList(HttpSession session, Model model, Qnaboard qnaboard,
								@RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(currentPage+" <- 현재페이지");
		model.addAttribute("currentPage", currentPage);
		final int rowPerPage = 10;
		
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(memberId+" <- memberId");
		Map<String, Object> map = new HashMap<>();
		map = qnaboardService.getQnaboardListAll(memberId, currentPage, rowPerPage);
		List<Qnaboard> qnaboardList = (List<Qnaboard>) map.get("qnaboardList");
		/*for(Qnaboard q : qnaboardList) {
			System.out.println(q);
		}*/
		model.addAttribute("qnaboardList", qnaboardList);
		model.addAttribute("lastPage", map.get("lastPage"));
		return "qnaboard/qnaboardList";
	}
	// 게시글 추가
	@GetMapping("/addQnaboard")
	public String addQnaboard(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		String memberId= ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		model.addAttribute("memberId", memberId);
		return "qnaboard/addQnaboard";
	}
	@PostMapping("/addQnaboard")
	public String addQnaboard(HttpSession session, Qnaboard qnaboard) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(qnaboard+" <- Qnaboard.addQnaboard: qnaboard");
		qnaboardService.addQnaboardList(qnaboard);
		return "redirect:/qnaboard";
	}
	// 게시글 상세보기
	@GetMapping("/qnaboardOne")
	public String qnabroadOne(HttpSession session, Model model, 
							@RequestParam(value="qnaboardNo") int qnaboardNo) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(qnaboardNo+" <- QnaboardController.qnabroadOne: qnaboardNo");
		
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(memberId+" <- QnaboardController.qnabroadOne: memberId");
		
		Map<String, Object> map = qnaboardService.getQnaboardListOne(qnaboardNo, memberId);
		System.out.println(map.get("nextQnaboardNo")+" <- 다음 게시글 번호");
		System.out.println(map.get("previousQnaboardNo")+" <- 이전 게시글 번호");
		model.addAttribute("qnaboard", map.get("qnaboardOne")); // 게시글 내용
		model.addAttribute("lastQnaboardNo", map.get("lastQnaboardNo")); // 마지막 게시글 번호
		model.addAttribute("startQnaboardNo", map.get("startQnaboardNo")); // 처음 게시글 번호
		model.addAttribute("nextQnaboardNo", map.get("nextQnaboardNo")); // 다음 게시글 번호
		model.addAttribute("previousQnaboardNo", map.get("previousQnaboardNo")); // 이전 게시글 번호
		return "qnaboard/qnaboardOne";
	}
	// 게시글 삭제
	@GetMapping("/removeQnaboard")
	public String removeQnaboard(HttpSession session, @RequestParam(value="qnaboardNo") int qnaboardNo){
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(qnaboardNo+" <-QnaboardController.removeQnaboard: qnaboardNo");
		qnaboardService.removeQnaboard(qnaboardNo);
		return "redirect:/qnaboard";
	}
	// 게시글 수정
	@GetMapping("/modifyQnaboard")
	public String modifyQnaboard(HttpSession session, Model model,
									@RequestParam(value="qnaboardNo") int qnaboardNo){
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(qnaboardNo+" <-QnaboardController.modifyQnaboard: qnaboardNo");
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(memberId+" <- QnaboardController.modifyQnaboard: memberId");
		
		Map<String, Object> map = qnaboardService.getQnaboardListOne(qnaboardNo, memberId);
		model.addAttribute("qnaboard", map.get("qnaboardOne")); // 게시글 내용
		return "qnaboard/modifyQnaboard";
	}
	@PostMapping("/modifyQnaboard")
	public String modifyQnaboard(HttpSession session, Qnaboard qnaboard) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(qnaboard+" <- Qnaboard.modifyQnaboard: qnaboard");
		qnaboardService.modifyQnaboard(qnaboard);
		return "redirect:/qnaboardOne?qnaboardNo="+qnaboard.getQnaboardNo();
	}
}
