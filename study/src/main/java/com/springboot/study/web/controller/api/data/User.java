package com.springboot.study.web.controller.api.data;

import lombok.Data;

@Data
public class User {
	private int usercode;
	private String username;
	private String password;
	private String email;
	private String name;
	
	public User() {
		usercode= 20220001;
		username = "tmdgus7983";
		password = "aaaa1234!";
		email = "aaa@gmail.com";
		name = "송승현";
		
	}
}
