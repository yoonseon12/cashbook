package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.Category;

@Service
@Transactional
public class CategoryService {
	@Autowired private CategoryMapper categoryMapper;
	// 자신의 카테고리 가져오기
	public List<String> getMyCategoryList(String memberId){
		return categoryMapper.myCategoryList(memberId);
	}
	// 카테고리 중복 확인
	public int getMyCategoryCheck(Category category) {
		return categoryMapper.myCategoryCheck(category);
	}
	// 직접 입력한 카테고리 추가
	public void addCategory(Category category) {
	categoryMapper.addCategory(category);
	}
}
