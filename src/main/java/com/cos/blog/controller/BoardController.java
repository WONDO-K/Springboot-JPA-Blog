package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	//@AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"","/"})
	public String index(Model model,@PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) { 
		model.addAttribute("boards",boardService.글목록(pageable)); //boards가 index라는 페이지로 뿌려줌 //model은 jsp에서 request 정보라고 생각하면 된다
		return "index"; // viewResolver 작동 : 해당 인덱스 페이지로 model의 정보를 들고 이동한다
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) { // model= 해당 데이터를 가지고 view로 이동
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
}
