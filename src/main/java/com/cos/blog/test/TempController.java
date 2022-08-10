package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//@RestController //텍스트 리턴
@Controller // 파일을 리턴 
public class TempController {

	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일 리턴 기본경로: src/main/resources/static
		//리턴명을 : /home.html을 해야 경로를 의식함
		//풀 경로: src/main/resources/static/home.html
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/a.png";
	}
	//jsp는 정적인 파일이 아니기 때문에 static 폴더에 위치하면 찾을 수 없다
	@GetMapping("/temp/jsp")
	public String tempJsp() {
	    //prefix: /WEB-INF/views/
	    //suffix: .jsp
		//풀네임 : /WEB-INF/views/test.jsp
		return "test";
	}
}
