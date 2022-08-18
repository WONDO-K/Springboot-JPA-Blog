package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.ReplySaveRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //빌더 패턴
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // User 클래스가 MySQL에 자동으로 테이블이 생성된다.

public class Reply {

	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 오라클 시퀀스 , MySQL auto_increment
	
	@Column(nullable=false,length=200)
	private String content;
	
	@ManyToOne //여러개의 답변은 하나의 게시글에 존재 가능하다 //OneToOne 하나의 게시글에 하나의 답변
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", user=" + user + ", createDate="
				+ createDate + "]";
	}
	
//	public void update(User user, Board board, String content) {
//		setUser(user);
//		setBoard(board);
//		setContent(content);
//	}
	
	
}
