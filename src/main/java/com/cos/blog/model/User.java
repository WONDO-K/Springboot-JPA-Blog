package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(다른언어 포함) Object -> 테이블로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
@Entity // User 클래스가 MySQL에 자동으로 테이블이 생성된다.
//@DynamicInsert //insert시에 null값을 제외해주는 기능
public class User {

	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 오라클 시퀀스 , MySQL auto_increment
	
	@Column(nullable = false, length=200, unique=true)
	private String username; //아이디
	
	@Column(nullable = false, length=100) // 123456 => 해쉬 (비밀번호를 암호화)
	private String password;
	  
	@Column(nullable = false, length=50)
	private String email; // myemail =>>> PhysicalNamingStrategyStandardImpl => myEmail // SpringPhysiclaNamingStrategy=> my_email
	
	
	//DB는  RoleType이 없다
	@Enumerated(EnumType.STRING)
	//@ColumnDefault("user")
	private RoleType role; //Enum을 쓰는게 좋다.(도메인(범위) 설정가능) // RoleType => ADMIN(관리자),USER 만 입력할 수 있게 고정
	
	private String oauth; // kakao, google ....
	
	
	@CreationTimestamp //시간이 자동입력
	private Timestamp createDate;
}
