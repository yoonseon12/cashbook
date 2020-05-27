package com.gdu.cashbook.service;

import java.io.File;	
import java.io.IOException;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.mapper.MemberIdMapper;
import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.QnaboardMapper;
import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.MemberId;

@Service // 트랜잭션 처리 가능
@Transactional // 실행하다가 예외가 발생하면 트랜잭션 작업 취소
public class MemberService {
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberIdMapper memberIdMapper;
	@Autowired private JavaMailSender javaMailSender;
	@Autowired private CommentMapper commentMapper;
	@Autowired private QnaboardMapper qnaboardMapper;
	@Autowired private CashMapper cashMapper;
	@Autowired private CategoryMapper categoryMapper;
	@Value("C:\\GIT_CASHBOOK\\cashbook\\src\\main\\resources\\static\\upload\\")
	private String path; // 회원가입시 프로필사진 저장 경로
	// 아이디 중복확인
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck); // 아이디없으면 null, 있으면 
	}
	// 회원가입
	public int addMember(MemberForm memberForm) {
		MultipartFile multipartFile = memberForm.getMemberPic();
		String originName = multipartFile.getOriginalFilename();
		System.out.println(originName+" <-첨부한 파일명.확장자");
		int row=0;
		if(!originName.equals("") 
			&& !memberForm.getMemberPic().getContentType().equals("image/png")
			&& !memberForm.getMemberPic().getContentType().equals("image/jpg")
			&& !memberForm.getMemberPic().getContentType().equals("image/jpeg")
			&& !memberForm.getMemberPic().getContentType().equals("image/gif")) {
			System.out.println("사진파일 아님.");
			row = 2; // 회원가입 성공하면 1, 실패하면 0, 확장자 틀리면 2 리턴
			return row;
		}
		String memberPic = null;
		if(originName.equals("")) {
			System.out.println("파일첨부안함");
			memberPic="default.jpg";
		}else {
			int lastIndex = originName.lastIndexOf(".");
			String extension = originName.substring(lastIndex); // 확장자 확인을 위해 .부터 문자열 자름
			System.out.println(extension+" <- MemberService.addMember: extension");
			memberPic = memberForm.getMemberId()+extension; // 이름 생성
		}
		// 1. DB에 저장
		System.out.println(memberPic+" <- memberPic");
		Member member = new Member();
		memberForm.memberSetBymemberForm(member, memberForm, memberPic);
		System.out.println(member+" <- MemberService.addMember: member");
		row = memberMapper.insertMember(member);
		// 2. 파일저장
		File file = new File(path+memberPic); 
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			// Exception
			// 1. 예외처리를 해야만 문법적으로 이상없는 예외
			// 2. 예외처리를 코드에서 구현하지 않아도 아무문제 없는 예외 : RuntimeException
		}
		return row;
	}
	// 로그인
	public LoginMember login(LoginMember loginMember) {
		System.out.println(loginMember+" <- MemberService.login: loginMember");
		return memberMapper.selectLoginMember(loginMember);
	}
	// 회원 정보
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	// 회원 탈퇴
	public void removeMember(LoginMember loginMember) {
		System.out.println("서비스접근");
		// 1. 회원 기록 삭제
		// 1-1. 파일이름 조회
		System.out.println(loginMember.getMemberId()+"<<<<<");
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		System.out.println(memberPic+" <- MemberService.removeMember: memberPic");
		// 1-1-2. 파일삭제
		File file = new File(path+memberPic);
		if(file.exists()) {
			file.delete();
		}
		System.out.println("사진삭제 완료");
		// 1-2 회원 댓글 삭제
		String loginMemberId = loginMember.getMemberId();
		commentMapper.memberDeleteComment(loginMemberId);
		System.out.println("댓글삭제 완료");
		// 1-3 회원 게시글 삭제
		qnaboardMapper.memberMeleteQnaboard(loginMemberId);
		System.out.println("게시글삭제 완료");
		// 1-4 회원 카테고리 삭제
		categoryMapper.memberDeleteCategory(loginMemberId);
		// 1-5 회원 가계부 삭제
		cashMapper.memberDeleteCash(loginMemberId);
		System.out.println("가계부삭제 완료");
		// 2. 회원탈퇴 시키고
		memberMapper.deleteMember(loginMember);
		System.out.println("회원 탈퇴 완료");
		// 3. 회원탈퇴한 아이디  추가
		MemberId memberId= new MemberId();
		memberId.setMemberId(loginMember.getMemberId());
		memberIdMapper.insertMemberId(memberId);
		System.out.println("백업 아이디 추가완료");
	}
	// 회원 탈퇴시 정보 확인
	public int removeMemberPwChack(Member member) {
		System.out.println(member+" <- MemberService.removeMemberPwChack: member");
		return memberMapper.deleteMemberPwChack(member);
	}
	// 회원 정보 수정
	public int modifyMember(MemberForm memberForm) {
		System.out.println(memberForm+" <- MemberService.modifyMember: member");
		MultipartFile multipartFile = memberForm.getMemberPic();
		String originName = multipartFile.getOriginalFilename();
		System.out.println(originName+" <-첨부한 파일명.확장자");
		int row=0;
		if(!originName.equals("") 
			&& !memberForm.getMemberPic().getContentType().equals("image/png")
			&& !memberForm.getMemberPic().getContentType().equals("image/jpg")
			&& !memberForm.getMemberPic().getContentType().equals("image/jpeg")
			&& !memberForm.getMemberPic().getContentType().equals("image/gif")) {
			System.out.println("사진파일 아님.");
			row = 2; // 수정 성공하면 1, 실패하면 0, 확장자 틀리면 2 리턴
			return row;
		}
		String memberPic = null;
		if(originName.equals("")) {
			System.out.println("파일첨부안함");
			memberPic="default.png";
		}else {
			int lastIndex = originName.lastIndexOf(".");
			String extension = originName.substring(lastIndex); // 확장자 확인을 위해 .부터 문자열 자름
			System.out.println(extension+" <- MemberService.addMember: extension");
			memberPic = memberForm.getMemberId()+extension; // 이름 생성
		}
		// 1. DB에 저장
		System.out.println(memberPic+" <- memberPic");
		Member member = new Member();
		memberForm.memberSetBymemberForm(member, memberForm, memberPic);
		System.out.println(member+" <- MemberService.addMember: member");
		row = memberMapper.updateMember(member);
		// 2. 파일저장
		File file = new File(path+memberPic); 
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		System.out.println(row+"<- <<<<<<<<<<<<<<<");
		return row;
	}
	// 아이디 찾기
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	// 비밀번호 찾기
	public int getMemberPw(Member member) { // member 매개변수안에는 id&email
		System.out.println(member.getMemberEmail()+" <- MemberService.getMemberPw: getMemberEmail");
		UUID uuid = UUID.randomUUID(); // 자바 랜덤 문자열 생성 ? 
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw); // pw를 추가해줘야 리턴가능
		int row = memberMapper.updateMemberPw(member);
		System.out.println(row+" <- 업데이트 유무");
		if(row==1) {
			System.out.println("업데이트 성공"); // JavaMailSender 메일객체
			System.out.println(memberPw+" <- 변경된 비밀번호");
			//메일보내기
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail()); // 받는사람주소
			simpleMailMessage.setFrom("a0105457331@gmail.com"); // 보내는사람주소
			simpleMailMessage.setSubject("[cashbook]비밀번호찾기"); // 제목
			simpleMailMessage.setText("변경된 비밀번호는[ "+memberPw+" ]입니다."); // 내용
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	// 비밀번호 변경
	public void modifyMemberInfoMemberPw(Member member) {
		memberMapper.updateMemberInfoMemberPw(member);
	}  
}
