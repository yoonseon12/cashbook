package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;	
import com.gdu.cashbook.vo.LoginAdmin;
import com.gdu.cashbook.vo.Qnaboard;

@Mapper
public interface AdminMapper {
	// 관리자 로그인
	public LoginAdmin selectLoginAdmin(LoginAdmin loginAdmin);
	// 게시글 출력(모든회원 게시글 확인)
	public List<Qnaboard> selectQnaboardListAll(int beginRow, int rowPerPage);
	// 작성된 게시글 총 개수
	public int selectQnaboardCount();
}
