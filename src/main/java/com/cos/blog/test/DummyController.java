package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import org.springframework.data.domain.Sort;

// html 파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyController {
	
	@Autowired //의존성 주입(DI) //DummyController가 메모리에 뜰때 @Autowired도 같이 메모리에 뜬다 
	private UserRepository userRepository;
	
	//save 함수는 id를 전달하지 않으면 insert를 해준다
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해준다
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다
	// email, password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e){ //Exception를 하면 모든 오류를 잡아내기 때문에 정확하지 않을 수 있다
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다. id : "+ id;
	}
	
	@Transactional //save쓰지 않아도 업데이트 됨 // 함수 종료시에 자동으로 commit이 이루어진다.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser ) { // @RequestBody => json 데이터를 요청 => Java Object(MessageConverter의 Jackson 라이브러리가  변환하여 받아준다)
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user); 
		
		//더티 체킹 //변경을 감지해서 db에 반영
		return user;
		
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한 페이지당 2건의 데이터를 리턴받음
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있다.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4를 찾으면 내가 db에서 못찾아오면 user가 null이 되므로 return null이 리턴된다. 그럼 문제가 생김
		// Optinal로 User객체를 감싸사 가져오면 null인지 아닌지 판단해서 return한다 
		
//      람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 사용자는 없습습니다.");
//		});
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id:" + id); //에러 설명 출력
			}
		});
		// 요청 : 웹 브라우저
		// user 객체 = 자바 오브젝트
		// 변환 ( 웹 브라우저가 이해할 수 있는 데이터) = > json(Gson 라이브러리)
		// 스프링부트 = MessageConverter가 응답시 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
	
//	public User detail(@PathVariable int id) {
//	User user = userRepository.findById(id).orElseGet(new Supplier<User>() { //.get() null이 리턴될 일이 없을 때 사용 / user객체를 optinal에서 바로 뽑아준다  // orElseGet() 객체를 만들어서 user에 넣어서 널이 되지 않는 기능
//		@Override
//		public User get() {
//			// TODO Auto-generated method stub
//			return new User();//빈 객체 생성하여 User에 넣어주면 null은 아니게 된다
//		} 	 
//	}); 
//	return user;
//}

	
	

	// http://localhost:8000/blog/dummy/join(요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	//public String join(String username, String password, String email) 
	public String join(User user) { // key = value(약속된 규칙) 키 벨류 형태의 정보를 받아준다
		System.out.println("id : " + user.getId() );
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
