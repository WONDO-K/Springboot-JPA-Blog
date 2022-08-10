package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //게터 세터 한번에 만들기
//@AllArgsConstructor //생성자
@NoArgsConstructor // 빈 생성자
public class Member {

	//final 불변성 유지하기 위해 사용
	private  int id;
	private  String username;
	private  String password;
	private  String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
		
}
