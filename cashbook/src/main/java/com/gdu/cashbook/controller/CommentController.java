package com.gdu.cashbook.controller;

import java.util.HashMap;	
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CommentService;
import com.gdu.cashbook.service.QnaboardService;
import com.gdu.cashbook.vo.*;

@Controller
public class CommentController {
	@Autowired private CommentService commentService;
	@Autowired private QnaboardService qnaboardService;
	// 댓글 추가
	@PostMapping("/addComment")
	public String addComment(HttpSession session, Comment comment) {
		System.out.println(comment+" <- CommentController.addComment: comment");
		if(session.getAttribute("loginMember")==null && session.getAttribute("loginAdmin")==null) { // 회원, 관리자 로그인상태  X
			return "redirect:/login";
		}
		if(session.getAttribute("loginAdmin")!=null) {
			System.out.println(((LoginAdmin)(session.getAttribute("loginAdmin"))).getAdminId() + " <-- 관리자 아이디");
			comment.setMemberId(((LoginAdmin)(session.getAttribute("loginAdmin"))).getAdminId());
		}
		System.out.println(comment.getMemberId()+"<<<?????!!!");
		commentService.addComment(comment);
		return "redirect:/qnaboardOne?qnaboardNo="+comment.getQnaboardNo();
	}
	// 댓글 삭제
	@GetMapping("/removeComment")
	public String removeComment(HttpSession session, int qnaboardNo, int commentNo) {
		if(session.getAttribute("loginMember")==null && session.getAttribute("loginAdmin")==null) { // 회원, 관리자 로그인상태  X
			return "redirect:/login";
		}
		commentService.removeComment(qnaboardNo, commentNo);
		return "redirect:/qnaboardOne?qnaboardNo="+qnaboardNo;
	}
	// 댓글 수정
	@GetMapping("/modifyComment")
	public String modifyComment(HttpSession session, Model model, int qnaboardNo, int commentNo,
								@RequestParam(value="commentCurrentPage", defaultValue = "1") int commentCurrentPage) {
		model.addAttribute("modifyCommentNo", commentNo);
		if(session.getAttribute("loginMember")==null && session.getAttribute("loginAdmin")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		String sessionId = null;
		if(session.getAttribute("loginAdmin")!=null) { // 관리자 로그인상태
			sessionId = ((LoginAdmin)(session.getAttribute("loginAdmin"))).getAdminId();
			System.out.println(sessionId+" <- 접속중인 관리자아이디");
			System.out.println(commentService.getMemberId(commentNo)+" <- 수정하려는 댓글의 작성자");
			if(!sessionId.equals(commentService.getMemberId(commentNo))) {
				System.out.println("관리자가 회원 댓글 수정 감지");
				return "redirect:/adminHome";
			}
		}
		if(session.getAttribute("loginMember")!=null) { // 회원 로그인상태
			sessionId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
			System.out.println(sessionId+" <- 접속중인 회원아이디");
			System.out.println(commentService.getMemberId(commentNo)+" <- 수정하려는 댓글의 작성자");
			if(!sessionId.equals(commentService.getMemberId(commentNo))) {
				System.out.println("회원이 관리자 댓글 수정 감지");
				return "redirect:/home";
			}
		}
		String memberId = null;
		if(session.getAttribute("loginMember")!=null) { // 회원이 댓글수정
			memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
			Map<String, Object> qnaboardMap = qnaboardService.getQnaboardListOne(qnaboardNo, memberId);
			model.addAttribute("qnaboard", qnaboardMap.get("qnaboardOne")); // 게시글 내용
			model.addAttribute("lastQnaboardNo", qnaboardMap.get("lastQnaboardNo")); // 마지막 게시글 번호(회원용)
			model.addAttribute("startQnaboardNo", qnaboardMap.get("startQnaboardNo")); // 처음 게시글 번호(회원용)
			model.addAttribute("nextQnaboardNo", qnaboardMap.get("nextQnaboardNo")); // 다음 게시글 번호(회원용)
			model.addAttribute("previousQnaboardNo", qnaboardMap.get("previousQnaboardNo")); // 이전 게시글 번호(회원용)
		}
		if(session.getAttribute("loginAdmin")!=null) { // 관리자가 댓글수정
			memberId = qnaboardService.getMemberId(qnaboardNo);
			Map<String, Object> qnaboardMap = qnaboardService.getAdminQnaboardListOne(qnaboardNo, memberId);
			model.addAttribute("qnaboard", qnaboardMap.get("qnaboardOne")); // 게시글 내용
			model.addAttribute("lastQnaboardNo", qnaboardMap.get("lastQnaboardNo")); // 마지막 게시글 번호(관리자용)
			model.addAttribute("startQnaboardNo", qnaboardMap.get("startQnaboardNo")); // 처음 게시글 번호(관리자용)
			model.addAttribute("nextQnaboardNo", qnaboardMap.get("nextQnaboardNo")); // 다음 게시글 번호(관리자용)
			model.addAttribute("previousQnaboardNo", qnaboardMap.get("previousQnaboardNo")); // 이전 게시글 번호(관리자용)
			
			memberId = ((LoginAdmin)(session.getAttribute("loginAdmin"))).getAdminId(); // 아래 관리자 댓글 수정메서드 매개변수 때문에 memberId에 관리자이름 주입
		}
		model.addAttribute("comment", commentService.getCommentListOne(qnaboardNo, commentNo, memberId)); // 수정할 댓글 내용
		return "comment/modifyComment";
	}
	@PostMapping("/modifyComment")
	public String modifyComment(HttpSession session, Comment comment) {
		System.out.println(session.getAttribute("loginAdmin")+" <- 관리자 로그인 세션");
		if(session.getAttribute("loginMember")==null && session.getAttribute("loginAdmin")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		System.out.println(comment+" CommentController.modifyComment: comment");
		commentService.modifyComment(comment);
		System.out.println("수정완료");
		return "redirect:/qnaboardOne?qnaboardNo="+comment.getQnaboardNo();
	}
}
