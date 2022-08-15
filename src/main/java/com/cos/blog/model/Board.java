package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable=false,length=100)
	private String title;
	
	@Lob //대용량 데이터를 사용할때 쓴다.
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.
	
	private int count;//조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // Board = Many user = One // 한명의 유저는 여러개의 게시글을 작성할 수 있다 //ManyToOne 기본전략 => EAGER
	@JoinColumn(name="userId") //필드 값은 userId로 만들어지고 연관관계는 ManyToOne으로 만들어 진다.
	private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy가 적혀있으면 연관관계의 주인이 아니다(난 FK가 아니다라는 뜻) DB에 컬럼을 만들지 말 것 (Reply클래스의 Board가 FK임) // OneToMany 기본전략 => LAZY= 필요하면 들고오고 필요치 않으면 들고 오지 않는다
	//상세보기 페이지에서는 Board와 Reply의 정보 모두를 가져와야 하기 때문에 EAGER전략을 사용한다
	//@JoinColumn(name="replyId") 필요가 없음(1정규화가 깨진다)
	private List<Reply> reply; // reply의 정보는 하나의 게시글에 여러개 존재 가능해야함 그래서 List사용
	
	@CreationTimestamp
	private Timestamp createDate;
}
