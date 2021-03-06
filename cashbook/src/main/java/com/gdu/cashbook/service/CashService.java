package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper; 
	@Autowired private CategoryMapper categoryMapper;
	// 가계부 추가
	public void addCash(Cash cash) {
		cashMapper.addCash(cash);
	}
	// 가계부 삭제
	public void removeCash(int cashNo) {
		cashMapper.removeCash(cashNo);
	}
	// 가계부 수정
	public void modifyCash(Cash cash) {
		// 직접입력한 카테고리 추가
		Category category = new Category();
		category.setMemberId(cash.getMemberId());
		category.setCategoryName(cash.getCategoryName());
		System.out.println(category+" <- CashService.addCash: category");
		categoryMapper.addCategory(category);
		// 가계부 수정
		cashMapper.modifyCash(cash);
	}
	// 수정창 사용 가계부 내용 불러오기
	public Cash getSelectCashOne(int cashNo) {
		return cashMapper.selectCashOne(cashNo);
	}
	// 일별 가계부 관리
	public Map<String, Object> getCashListByDay(Cash cash){
		List<Cash> cashList = cashMapper.selectCashListByToday(cash); // 로그인 회원의 오늘 날짜 cash 목록
		int dayCashKindSum = cashMapper.selectTotPriceOfDay(cash); // 일별 금액 합계 (일별 가계부 관리)
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", dayCashKindSum);
		return map;
	}
	// 다이어리 (월별 가계부 관리)
	public Map<String, Object> getCashbookByMonth(String memberId, int year, int month){
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("memberId", memberId);
		inputMap.put("year", year);
		inputMap.put("month", month);
		Map<String, Object> outputMap = new HashMap<>();
		outputMap.put("dayAndPrice",cashMapper.selectListPriceOfDay(inputMap)); // 일별 금액 리스트(월별 가계부 관리)
		outputMap.put("monthAndPrice",cashMapper.selectTotPriceOfMonth(inputMap)); // 월별 금액 합계(월별 가계부 관리)
		return outputMap;
	}
	// 이번년도 월별 금액 합계(월별 비교)
	public Map<String, Object> getCashbookByYear(String memberId, int year){
		Map<String, Object> inputMap = new HashMap<>();
		inputMap.put("memberId", memberId);
		inputMap.put("year", year);
		Map<String, Object> outputMap = new HashMap<>();
		outputMap.put("yearPriceList",cashMapper.selectListPriceOfYear(inputMap)); // 이번년도의 월별 금액 리스트(월별 비교)
		outputMap.put("yearPriceTotal", cashMapper.selectTotPriceOfYear(inputMap)); // 이번년도의 월별 금액 합계(월별 비교)
		return outputMap;
	}
}
