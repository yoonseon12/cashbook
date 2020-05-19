package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model) {
		if(session.getAttribute("loginMember")==null) { // 로그인 상태 X
			return "redirect:/index";
		}
		// 로그인 아이디
		String loginMemberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		System.out.println(loginMemberId+" <- CashController.getCashListByDate: loginMemberId");
		// 오늘 날짜를 구해서 원하는 문자열 형태로 변경
		Date today = new Date();
		System.out.println(today+" <- CashController.getCashListByDate:today");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 원하는 문자열 형태로 변경
		String strToday = simpleDateFormat.format(today);
		System.out.println(strToday+" <- CashController.getCashListByDate: strToday");
		model.addAttribute("strToday", strToday);
		//String year = 
		Cash cash = new Cash(); // + id, + date(yyyy-mm-dd)
		cash.setMemberId(loginMemberId);
		cash.setCashDate(strToday);
		
		List<Cash> cashList = cashService.getCashListByDate(cash);
		for(Cash c : cashList) {
			System.out.println(c);
		}
		model.addAttribute("cashList", cashList);
		return "getCashListByDate";
	}
}
