package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML 파일)
//@Controller

// 사용자가 요청 -> 응답(DATA)
@RestController //텍스트 리턴
public class HttpController {
	
	private static final String TAG = "HttpController:" ;
	//롬복의 빌더 장점 순서를 지키지 않아도 된다
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m = new Member("ssar","1234","email");
		Member m = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		
		System.out.println(TAG+"getter: " + m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"setter: " + m.getUsername());
		return "lombok test 완료";
	}
	
	//인터넷 브라우저는 get요청만 가능하다
	//http://localhost:9090/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청: "+ m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	//http://localhost:9090/http/post(insert)
	@PostMapping("/http/post") //text/plain, application/json
	public String postTest(@RequestBody Member m) { // MessageConverter(스프링부트)가 자동으로 매핑해준다
		return "post 요청: "+ m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	//http://localhost:9090/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청" + m.getId() + "," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	//http://localhost:9090/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}

}
