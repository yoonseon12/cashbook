package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.MemberId;

@Mapper
public interface MemberIdMapper {
	public void insertMemberId(MemberId memberId);
}
