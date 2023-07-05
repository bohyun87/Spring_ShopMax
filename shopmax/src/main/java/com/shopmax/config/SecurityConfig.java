package com.shopmax.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
					
@Configuration		//bean 객체를 싱글톤으로 공유할 수 있게 해준다.  // 싱글톤으로 객체를 관리해준다.(싱글톤: 하나의 객체를 공유해서 사용한다.)
@EnableWebSecurity    //Spring Security filterChain 이 자동으로 포함되게 한다.
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//login에 crsf 추가
		//memberForm / memberLoginForm.html 오타  MemberFormDto ->  memberFormDto 변경  
		
		/*로그인에 대한 설정
		http.formLogin()
			.loginPage("/members/login") //로그인 화면 url 설정
			.defaultSuccessUrl("/")  //로그인 성공시 이동할 페이지 url
			.usernameParameter("email")  //로그인시 id로 사용할 파라메터 이름
			.failureUrl("/members/login/error")   //로그인 실패시 이동할 url
		*/
		
		
		//로그인에 대한 설정  => 람다로 변경됨		
		//1. 페이지 접근에 권한   
		http.authorizeHttpRequests(authorize -> authorize  			
				//모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
				.requestMatchers("/css/**","/js/**", "/img/**", "/images/**", "/fonts/**").permitAll()   // permitAll() 누구든지 접근할 수 있다
				.requestMatchers("/","/members/**", "/item/**").permitAll() 	 //회원가입을 하지 않고 볼 수 있는 페이지
				.requestMatchers("/favicon.ico", "/error").permitAll()
				//'admin' 으로 시작하는 경로는 관리자만 접근가능하도록 설정
				.requestMatchers("/admin/**").hasRole("ADMIN")		//.hasRole()  -> 관리자로 로그인 한 사용자만 접근할 수 있는 페이지
				// 그 외의 페이지는 모두 로그인(인증을 받아야 한다.)
				.anyRequest().authenticated()	
				)   
		
		//2. 로그인에 관련된 설정
			.formLogin(formLogin -> formLogin
					.loginPage("/members/login")             //로그인 페이지 띄어주기
					.defaultSuccessUrl("/")   	             //로그인 성공시 이동할 페이지 url
					.usernameParameter("email")              //★★★★★★★로그인시 id로 사용할 파라메터 이름
					.failureUrl("/members/login/error")      //로그인 실패시 이동할 url
					//.permitAll()							 //모든 사람이 로그인페이지 접근 가능하게 설정
					)	
			
		//3. 로그아웃에 관련된 설정
			.logout(logout -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))	//로그아웃시 이동할 URL
					.logoutSuccessUrl("/")	//로그아웃 성공시 이동할 URL
					//.permitAll()
					)
		//4. 인증되지 않은 사용자가 리소스에 접근했을때 설정(ex. 로그인 안했는데 orde, cart 페이지 접근...etc)
			.exceptionHandling(handling -> handling
					.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
					)
			.rememberMe(Customizer.withDefaults());		//어플리케이션이 사용자를 기억하는 기능->자동 로그인 기능 같은 역할
		
		return http.build();				
	}
	
	@Bean    //빈 객체
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}