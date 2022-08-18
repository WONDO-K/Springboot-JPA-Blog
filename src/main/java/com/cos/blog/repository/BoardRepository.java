package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;


public interface BoardRepository extends JpaRepository<Board, Integer>{ //JpaRepository가 다 들고있음
							

	@Modifying
	@Query(value="UPDATE board b SET b.count = b.count + 1 WHERE b.id = ?", nativeQuery = true)
	int updateCount(int id);
	
}
