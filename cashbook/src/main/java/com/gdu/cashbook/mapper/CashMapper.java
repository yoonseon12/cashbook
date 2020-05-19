package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;

@Mapper
public interface CashMapper {
	// 로그인 회원의 오늘 날짜 cash 목록
	public List<Cash> selectCashListByToday(Cash cash);
	//
}
