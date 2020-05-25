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
	// 가계부 리스트 출력
	public Map<String, Object> getCashListByDate(Cash cash){
		List<Cash> cashList = cashMapper.selectCashListByToday(cash);
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
	// 가계부 삭제
	public void removeCash(int cashNo) {
		cashMapper.removeCash(cashNo);
	}
	// 월별 가계부
	public List<DayAndPrice> getCashPriceList(String memberId, int year, int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cashMapper.selectDayAndPriceList(map);
	}
	// 가계부 추가
	public void addCash(Cash cash) {
		// 직접입력한 카테고리 추가
		Category category = new Category();
		category.setMemberId(cash.getMemberId());
		category.setCategoryName(cash.getCategoryName());
		System.out.println(category+" <- CashService.addCash: category");
		categoryMapper.addCategory(category);
		// 가계부 추가
		cashMapper.addCash(cash);
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
}
