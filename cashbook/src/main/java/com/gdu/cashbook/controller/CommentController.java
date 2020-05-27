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
import com.gdu.cashbook.vo.Comment;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CommentController {
	@Autowired private CommentService commentService;
	@Autowired private QnaboardService qnaboardService;
	// 댓글 추가
	@PostMapping("/addComment")
	public String addComment(HttpSession session, Comment comment) {
		System.out.println(comment+" <- CommentController.addComment: comment");
		if (session.getAttribute("loginMember") == null) { // 로그인 상태 X
			return "redirect:/login";
		}
		commentService.addComment(comment);
		return "redirect:/qnaboardOne?qnaboardNo="+comment.getQnaboardNo();
	}
	// 댓글 삭제
	@GetMapping("/removeComment")
	public String removeComment(HttpSession session, int qnaboardNo, int commentNo) {
		if (session.getAttribute("loginMember") == null) { // 로그인 상태 X
			return "redirect:/login";
		}
		commentService.removeComment(qnaboardNo, commentNo);
		return "redirect:/qnaboardOne?qnaboardNo="+qnaboardNo;
	}
	// 댓글 수정
	@GetMapping("/modifyComment")
	public String modifyComment(HttpSession session, Model model, int qnaboardNo, int commentNo,
								@RequestParam(value="commentCurrentPage", defaultValue = "1") int commentCurrentPage) {
		if(session.getAttribute("loginMember")==null) { // 로그인상태  X
			return "redirect:/login";
		}
		model.addAttribute("modifyCommentNo", commentNo);
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		Map<String, Object> qnaboardMap = qnaboardService.getQnaboardListOne(qnaboardNo, memberId);
		model.addAttribute("qnaboard", qnaboardMap.get("qnaboardOne")); // 게시글 내용
		model.addAttribute("lastQnaboardNo", qnaboardMap.get("lastQnaboardNo")); // 마지막 게시글 번호
		model.addAttribute("startQnaboardNo", qnaboardMap.get("startQnaboardNo")); // 처음 게시글 번호
		model.addAttribute("nextQnaboardNo", qnaboardMap.get("nextQnaboardNo")); // 다음 게시글 번호
		model.addAttribute("previousQnaboardNo", qnaboardMap.get("previousQnaboardNo")); // 이전 게시글 번호
	
		model.addAttribute("comment", commentService.getCommentListOne(qnaboardNo, commentNo, memberId)); // 수정할 댓글 내용
		return "comment/modifyComment";
	}
	@PostMapping("/modifyComment")
	public String modifyComment(Comment comment) {
		System.out.println(comment+" CommentController.modifyComment: comment");
		commentService.modifyComment(comment);
		return "redirect:/qnaboardOne?qnaboardNo="+comment.getQnaboardNo();
	}
}
