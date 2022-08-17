package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@RestController
public class ReplyController {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get(); //Jackson 라이브러리 작동 (오브젝트를 json으로 리턴) => 모델의 getter를 호출 board의 모든 getter를 호출하면서 replys에서 보드 유저 등등을 다시 리턴하면서 무한참조 현상 발생
	}
	
	@GetMapping("/test/reply")
	public List<Reply> getReply() {
		return replyRepository.findAll(); //Jackson 라이브러리 작동 (오브젝트를 json으로 리턴) => 모델의 getter를 호출 board의 모든 getter를 호출하면서 replys에서 보드 유저 등등을 다시 리턴하면서 무한참조 현상 발생
	}
}
