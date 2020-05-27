package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Category;

@Mapper
public interface CategoryMapper {
	// 카테고리 추가
	public void addCategory(Category category);
	// 자신의 카테고리 가져오기
	public List<String> myCategoryList(String memberId);
	// 카테고리 중복 확인
	public int myCategoryCheck(Category category);
	// 회원탈퇴시 카테고리 삭제
	public void memberDeleteCategory(String memberId);
}
