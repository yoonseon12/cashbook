package com.gdu.cashbook.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.service.CategoryService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/getCashListByDate")
	// 가계부 출력
	public String getCashListByDate(HttpSession session, Model model,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		if (session.getAttribute("loginMember") == null) { // 로그인 상태 X
			return "redirect:/index";
		}
		if (date == null) {
			date = LocalDate.now();
		}
		System.out.println(date + " <- CashController.getCashListByDate: date");
		model.addAttribute("date", date);

		// 로그인 아이디
		String loginMemberId = ((LoginMember) (session.getAttribute("loginMember"))).getMemberId();
		System.out.println(loginMemberId + " <- CashController.getCashListByDate: loginMemberId");
		// 오늘 날짜를 구해서 원하는 문자열 형태로 변경
		Cash cash = new Cash(); // + id, + date(yyyy-mm-dd)
		cash.setMemberId(loginMemberId);
		cash.setCashDate(date.toString());
		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		model.addAttribute("cashList", map.get("cashList"));
		return "getCashListByDate";
	}

	// 가계부 삭제
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session, @RequestParam(value = "cashNo") int cashNo,
			@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		if (session.getAttribute("loginMember") == null) { // 로그인상태 X
			return "redirect:/login";
		}
		System.out.println(cashNo + " <- CashController.removeCash: cashNo");
		System.out.println(date + " <- CashController.removeCash: day");
		cashService.removeCash(cashNo);
		return "redirect:/getCashListByDate?day=" + date;
	}

	// 다이어리
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		if (session.getAttribute("loginMember") == null) { // 로그인상태 X
			return "redirect:/login";
		}
		System.out.println(date + " <- CashController.getCashListByMonth: date");
		Calendar cDate = Calendar.getInstance(); // 오늘날짜
		if (date == null) {
			date = LocalDate.now();
			// date -> cDate 형변환
		} else {
			cDate.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()); // 오늘날짜에서 day값과 동일한 값으로
		}

		String memberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId();
		int year = cDate.get(Calendar.YEAR);
		int month = cDate.get(Calendar.MONTH) + 1;
		System.out.println(memberId);
		System.out.println(year);
		System.out.println(month);
		// 일별 수입 지출 총액
		List<DayAndPrice> dayAndPriceList = cashService.getCashPriceList(memberId, year, month);
		for (DayAndPrice dp : dayAndPriceList) {
			System.out.println(dp + " <- ㅇㅁㄴ");
		}
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		/*
		 * 0. 오늘날짜 LocalDate타입 1. 오늘날짜 Calendar타입 2. 이번달의 마지막 일 3. 이번달 1일의 요일
		 */
		model.addAttribute("date", date);
		model.addAttribute("month", cDate.get(Calendar.MONTH) + 1); // 월
		model.addAttribute("lastDay", cDate.getActualMaximum(Calendar.DATE)); // 월별 마지막 일

		Calendar firstDay = cDate;
		firstDay.set(Calendar.DATE, 1); // cDate에서 일 만 1일로 변경
		System.out.println(firstDay.get(Calendar.YEAR) + "," + (firstDay.get(Calendar.MONTH) + 1) + ","
				+ firstDay.get(Calendar.DATE));
		System.out.println("firstDay.get(Calendar.DAY_OF_WEEK):" + firstDay.get(Calendar.DAY_OF_WEEK));// 1 일요일, 2월요일,
																										// ....7 토요일
		model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
		return "getCashListByMonth";
	}

	// 가계부 추가
	@GetMapping("/addCash")
	public String addCash(HttpSession session, Model model,
			@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		System.out.println(date + " <- CashController.addCash: date");
		if (session.getAttribute("loginMember") == null) { // 로그인 X
			return "redirect:/login";
		}
		model.addAttribute("date", date);
		// 나의 카테고리 가져오기
		String memberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId();
		System.out.println(memberId + " <- CashController.addCash: memberId");
		List<String> categoryList = categoryService.getMyCategoryList(memberId);
		model.addAttribute("categoryList", categoryList);
		return "addCash";
	}

	@PostMapping("/addCash")
	public String addCash(HttpSession session, Model model, Cash cash,
			@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@RequestParam(value = "categoryNameDirect") String categoryNameDirect) {
		if (session.getAttribute("loginMember") == null) { // 로그인 X
			return "redirect:/login";
		}
		System.out.println(cash + " <- CashController.addCash: cash");
		System.out.println(date + " <- CashController.addCash: date");
		System.out.println(categoryNameDirect + " <- CashController.addCash: categoryNameDirect(직접입력한 카테고리 명)");
		String memberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId();
		//카테고리를 직접입력했다면
		if (categoryNameDirect != "") {
			System.out.println("카테고리를 직접 입력함");
			// 카테고리 중복 검사
			Category category = new Category();
			category.setMemberId(memberId);
			category.setCategoryName(categoryNameDirect);
			System.out.println(category + " <- CashController.addCash: category");
			
			int categoryNameCheck = categoryService.getMyCategoryCheck(category);
			System.out.println(categoryNameCheck+" <-  CashController.addCash: categoryNameCheck");
			if(categoryNameCheck == 0) { // 중복되는 카테고리 없음
				// 카테고리 추가
				categoryService.addCategory(category);
			}
			if(categoryNameCheck != 0) { // 중복되는 카테고리 있음
				System.out.println("중복된 카테고리 존재"); model.addAttribute("date", date);
	
				String categoryCheacMsg = "이미있는 카테고리 입니다. 다른 카테고리를 입력해주세요.";
				model.addAttribute("categoryCheacMsg", categoryCheacMsg);
	
				List<String> categoryList = categoryService.getMyCategoryList(memberId);
				model.addAttribute("categoryList", categoryList);
				 
				return "addCash";
			}
			System.out.println("사용가능한 카테고리 이름");
			cash.setCategoryName(categoryNameDirect); // 직접입력한 categoryName 주입
		}
		System.out.println(cash + " <- CashController.addCash: cash (memberId 주입전)");
		cash.setMemberId(memberId);
		System.out.println(cash + " <- CashController.addCash: cash (memberId 주입후)");
		cashService.addCash(cash);
		return "redirect:/getCashListByDate";
	}

	// 가계부 수정
	@GetMapping("/modifyCash")
	public String modifyCash(HttpSession session, Model model, 
							@RequestParam(value = "cashNo") int cashNo,
							@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		if (session.getAttribute("loginMember") == null) { // 로그인상태 X
			return "redirect:/login";
		}
		System.out.println(cashNo + " <- CashController.modifyCash: cashNo");
		model.addAttribute("cashNo", cashNo);
		System.out.println(date + " <- CashController.modifyCash: date");
		model.addAttribute("date", date);
		// 회원의 카테고리 받아오기
		String memberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId();
		System.out.println(memberId + " <- CashController.addCash: memberId");
		List<String> categoryList = categoryService.getMyCategoryList(memberId);
		model.addAttribute("categoryList", categoryList);
		return "modifyCash";
	}

	@PostMapping("/modifyCash")
	public String modifyCash(HttpSession session, Model model, Cash cash,
			@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@RequestParam(value = "categoryNameDirect") String categoryNameDirect) {
		if (session.getAttribute("loginMember") == null) { // 로그인상태 X
			return "redirect:/login";
		}
		System.out.println(cash + " <- CashController.modifyCash: cash");
		System.out.println(date + " <- CashController.modifyCash: date");
		System.out.println(cash.getCashNo() + " <- CashController.modifyCash: cashNo");
		System.out.println(categoryNameDirect + " <- CashController.modifyCash: categoryNameDirect(직접입력한 카테고리 명)");
		String memberId = ((LoginMember) session.getAttribute("loginMember")).getMemberId();
		//카테고리를 직접입력했다면
		if (categoryNameDirect != "") {
			System.out.println("카테고리를 직접 입력함");
			// 카테고리 중복 검사
			Category category = new Category();
			category.setMemberId(memberId);
			category.setCategoryName(categoryNameDirect);
			System.out.println(category + " <- CashController.addCash: category");
			
			int categoryNameCheck = categoryService.getMyCategoryCheck(category);
			System.out.println(categoryNameCheck+" <-  CashController.modifyCash: categoryNameCheck");
			if(categoryNameCheck == 0) { // 중복되는 카테고리 없음
				// 카테고리 추가
				categoryService.addCategory(category);
			}
			if(categoryNameCheck != 0) { // 중복되는 카테고리 있음
				System.out.println("중복된 카테고리 존재"); model.addAttribute("date", date);
	
				String categoryCheacMsg = "이미있는 카테고리 입니다. 다른 카테고리를 입력해주세요.";
				model.addAttribute("categoryCheacMsg", categoryCheacMsg);
	
				List<String> categoryList = categoryService.getMyCategoryList(memberId);
				model.addAttribute("categoryList", categoryList);
				 
				return "modifyCash";
			}
			System.out.println("사용가능한 카테고리 이름");
			cash.setCategoryName(categoryNameDirect); // 직접입력한 categoryName 주입
		}
		System.out.println(cash + " <- CashController.modifyCash: cash (memberId 주입전)");
		cash.setMemberId(memberId);
		System.out.println(cash + " <- CashController.addCash: cash (memberId 주입후)");
		cashService.modifyCash(cash);
		return "redirect:/getCashListByDate";
	}
}
