package com.gdu.cashbook;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
	/*
	 * servlet API 
	 * 1. servlet : 요청처리
	 * 2. filter : 요청 전후 처리 
	 * 3. listner : 이벤트 발생시
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CashbookApplication.class);
	}

}
