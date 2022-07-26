package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

//나중에 앱에서도 사용 가능
//데이터만 전달해주기 때문에
@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // user => username, password, email
		System.out.println("UserApiController : save 호출됨");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 된다.
		
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바 오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) { //key=value, x-www-form-urlencoded를 받고 싶으면 @RequestBody 삭제
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료되기 때문에 db의 값은 변경되었지만
		// 세션의 값은 변경되지 않은 상태이기 때문에  직접 세션의 값을 변경해주어야 한다.
		
		// 세션등록 (이 위치에서 해야 DB값이 변경된 정보를 세션을 등록가능)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
			
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); 
	}

}

/*
 * @PostMapping("/api/user/login") public ResponseDto<Integer>
 * login(@RequestBody User user,HttpSession session){
 * System.out.println("UserApiController : login 호출됨"); User principal =
 * userService.로그인(user); //principal 접근주체 if(principal!=null) {
 * session.setAttribute("principal", principal); } return new
 * ResponseDto<Integer>(HttpStatus.OK.value(),1); }
 */
