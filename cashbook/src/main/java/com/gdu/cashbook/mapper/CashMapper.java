package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;

@Mapper
public interface CashMapper {
	// 로그인 회원의 오늘 날짜 cash 목록
	public List<Cash> selectCashListByToday(Cash cash);
	// 가계부 내용 삭제
	public void removeCash(int cashNo);
	// 합계
	public int selectCashKindSum(Cash cash);
}
