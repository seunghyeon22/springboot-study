package com.springboot.study.domain.user;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
	private int usercode;
	private String email;
	private String name;
	private String username;
	private String oAuth2_username;
	private String password;
	private String roles; //ROlE_USER, ROLE_MANAGER, ROLE_ADMIN
	private String provider;
	private String profile_img_url;
	
	public List<String> getRoleList(){
		if(this.roles.length()>0) {
			return Arrays.asList(this.roles.split(","));//Arrays.asList - 배열을 list로 바꿔준다, split- 문자열을 배열로 바꿔준다			
		}
		return new ArrayList<String>();
	}
}
