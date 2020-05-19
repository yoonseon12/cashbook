package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	@GetMapping("/getCashListByDate")
	//가계부 출력
	public String getCashListByDate(HttpSession session, Model model,@RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(session.getAttribute("loginMember")==null) { // 로그인 상태 X
			return "redirect:/index";
		}
		if(day == null) {
			day = LocalDate.now();
		}
		System.out.println(day+" <- CashController.getCashListByDate: day");
		model.addAttribute("day", day);
		// 로그인 아이디
		String loginMemberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(loginMemberId+" <- CashController.getCashListByDate: loginMemberId");
		// 오늘 날짜를 구해서 원하는 문자열 형태로 변경
		
		Cash cash = new Cash(); // + id, + date(yyyy-mm-dd)
		cash.setMemberId(loginMemberId);
		cash.setCashDate(day.toString());
		
		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		model.addAttribute("cashList", map.get("cashList"));
		System.out.println(map.get("cashKindSum")+"<<<<<<<<<<<<<<<<<<");
		return "getCashListByDate";
	}
	// 가계부 삭제
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session, @RequestParam(value="cashNo") int cashNo) { //, @RequestParam(value="") {
		if(session.getAttribute("loginMember")==null) { // 로그인상태 X
			return "login";
		}
		System.out.println(cashNo+" <- CashController.removeCash: cashNo");
		cashService.removeCash(cashNo);
		return "redirect:/getCashListByDate";
	}
}
