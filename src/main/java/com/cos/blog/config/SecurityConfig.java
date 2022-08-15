package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration 		// bean등록
@EnableWebSecurity	// 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean //IoC가 된다 BCryptPasswordEncoder를 스프링이 관리
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()//csrf 토큰 비활성화 (테스티 시에 걸어주기)
		.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") 	// /auth이하 경로는 모두 접근 가능
			.permitAll()
			.anyRequest() 				// 다른 모든 접근은
			.authenticated()			// 인증이 필요하다
		.and()
			.formLogin()
			.loginPage("/auth/loginForm");
	}
}
