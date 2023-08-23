package com.shopmax.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	//String uploadPath = "file:///C:/shop/";  //업로드할 경로
	
	@Value("${uploadPath}")
	String uploadPath;
	
	//웹 브라우저에서 URL이 /images. 로 시작하는 경우 uploadPath 에 설정한 폴더를 기준으로 파일을 읽어온다.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("images/**")   //이렇게 들어오는 이미지 주소를
				.addResourceLocations(uploadPath);	//c: 폴더에서 이미지 찾기  => 이미지를 c: 에 넣어줘야 이미지 보임
	}
	
	
	
}
