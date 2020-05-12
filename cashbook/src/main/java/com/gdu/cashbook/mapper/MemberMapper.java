package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	public void insertMember(Member member);
}
