package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	// 로그인 회원의 오늘 날짜 cash 목록
	public List<Cash> selectCashListByToday(Cash cash);
	// 가계부 내용 삭제
	public void removeCash(int cashNo);
	// 금액 합계 (일별 가계부 관리)
	public int selectDayCashKindSum(Cash cash);
	// 일별 금액 (월별 가계부 관리)
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	// 월별 금액 합계(월별 가계부 관리)
	public int selectMonthAndPriceList(Map<String, Object> map);
	// 가계부 추가
	public void addCash(Cash cash);
	// 수정창 사용 가계부 내용 불러오기
	public Cash selectCashOne(int cashNo);
	// 가계부 수정
	public void modifyCash(Cash cash);
}
