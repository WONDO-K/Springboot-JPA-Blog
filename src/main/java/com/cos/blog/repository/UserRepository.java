package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO
//자동으로 bean으로 등록이 된다.
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{ //JpaRepository는 user가 관리하는 Repository이고 user테이블의 기본키는 integer(정수,숫자)라는 뜻
																	  // JpaRepository는 findAll()을 가지고 있는데 user테이블이 가지고 있는 모든 행을 리턴한다
																	  // 기본적인 crud 관련 함수를 들고 있음
	//JPA쿼리
	// SELECT * FROM user WHERE username=1? AND password=2?; 
	User findByUsernameAndPassword(String username, String password);

	/*
	 * @Query(
	 * value="SELECT * FROM user WHERE username=?1 AND password=?2, nativeQuery=true"
	 * ) User login(String username, String password);
	 */
}
