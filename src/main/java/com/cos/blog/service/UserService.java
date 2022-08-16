package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해준다. IoC를 해준다.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional //하나의 서비스로 묶음
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 비번 원문
		String encPassword = encoder.encode(rawPassword); // 비번 해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER); //user에 role이 없기 때문에 설정해주어야 한다
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(User user) { // user는 외부로부터 받은 것임
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정하는 것이 좋음
		//select를 해서 User 오브젝트를 db로 부터 가져오는 이유는 영속화를 위해서
		// 영속화된 오브젝트를 변경하면 자동으로 db에 update문을 날려준다.
		
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
	
		
		// 회원 수정 함수 종료 시 = 서비스 종료 시 = 트랜잭션 종료 = commit이 자동으로 됨.
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
		
		
	}
	
}
/*
 * @Transactional(readOnly = true) //select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
 * 유지) public User 로그인(User user) { return
 * userRepository.findByUsernameAndPassword(user.getUsername(),
 * user.getPassword()); }
 */
