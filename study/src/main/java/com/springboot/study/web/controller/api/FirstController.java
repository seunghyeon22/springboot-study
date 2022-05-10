package com.springboot.study.web.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // view를 리턴할 수 없다. 무조건 데이터를 리턴, spring에서는 Responsebody사용해야함
public class FirstController {
	@GetMapping("/hello") // @RequestMapping(value ="/hello", method = RequestMethod.GET)와 동일
	public String hello() {
		return "hello";
	}

	@GetMapping("/add")
	public int add(@RequestParam("a") List<Integer>values) {
		int result= 0;
		for(int i:values) {
			result += i;
		}
		return result;
	}
	@GetMapping("/sub")
	public int sub(int a, int b) {
		return a-b;
	}
	@GetMapping("/mul")
	public int mul(int a, int b) {
		return a*b;
	}
	@GetMapping("/div")
	public String div(int a, int b) {	
		if(a==0||b==0) {
			return "div 파라미터가 둘중 하나라도 0이면 0으로 계산 할 수 없습니다.";
		}else {
			int div =a/b;
			return Integer.toString(div);
		}
		
	}
}
