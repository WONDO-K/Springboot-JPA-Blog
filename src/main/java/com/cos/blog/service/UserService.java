package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
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
	
}
/*
 * @Transactional(readOnly = true) //select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
 * 유지) public User 로그인(User user) { return
 * userRepository.findByUsernameAndPassword(user.getUsername(),
 * user.getPassword()); }
 */
