package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;


@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
		
	@Transactional 
	public void 글쓰기(Board board, User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable); //findAll() 다 가져올수 있음
	}
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	@Transactional
	public void 글삭제하기(int id, PrincipalDetail principal) {
		System.out.println("글 삭제하기: " + id);
		Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("글 찾기 실패 : 해당 글이 존재하지 않습니다.");
        });

        if (board.getUser().getId() != principal.getUser().getId()) {
            throw new IllegalStateException("글 삭제 실패 : 해당 글을 삭제할 권한이 없습니다.");
        }
		 boardRepository.deleteById(id);
		 // boardRepository.delete(board);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestboard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestboard.getTitle());
		board.setContent(requestboard.getContent());
		//해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹 - 자동 업데이트 됨. db flush(commit)
	}
}

