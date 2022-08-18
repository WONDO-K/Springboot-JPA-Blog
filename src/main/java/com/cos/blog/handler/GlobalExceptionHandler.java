 package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice //모든 Exception이 발생시 이 클래스로 들어온다
@RestController
public class GlobalExceptionHandler {

//	@ExceptionHandler(value=IllegalArgumentException.class) //IllegalArgumentException이 발생하면 
//	public String handleArgumentException(IllegalArgumentException e) { //스프링이 e 함수에 전달해준다
//		return "<h1>"+e.getMessage()+"</h1>";
//	}
	
	@ExceptionHandler(value=Exception.class) //IllegalArgumentException이 발생하면 
	public ResponseDto<String> handleArgumentException(Exception e) { //스프링이 e 함수에 전달해준다
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage()); // INTERNAL_SERVER_ERROR : 500
	}
}
