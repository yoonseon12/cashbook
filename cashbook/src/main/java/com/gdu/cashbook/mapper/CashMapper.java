package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	// 가계부 추가
	public void addCash(Cash cash);
	// 가계부 삭제
	public void removeCash(int cashNo);
	// 가계부 수정
	public void modifyCash(Cash cash);
	// 수정창 사용 가계부 내용 불러오기
	public Cash selectCashOne(int cashNo);
	// 오늘날짜의 가계부 관리
	public List<Cash> selectCashListByToday(Cash cash);
	// 일별 금액 합계 (일별 가계부 관리)
	public int selectTotPriceOfDay(Cash cash);
	// 일별 금액 리스트(월별 가계부 관리)
	public List<DayAndPrice> selectListPriceOfDay(Map<String, Object> map);
	// 월별 금액 합계(월별 가계부 관리)
	public int selectTotPriceOfMonth(Map<String, Object> map);
	// 이번년도의 월별 금액 리스트(월별 비교)
	public List<DayAndPrice> selectListPriceOfYear(Map<String, Object> map);
	// 이번년도 월별 금액 합계(월별 비교)
	public int selectTotPriceOfYear(Map<String, Object> map);
	// 회원 탈퇴시 가계부 삭제
	public void memberDeleteCash(String memberId);
}
