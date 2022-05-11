/*
 * ClassName: CMRespDto(CustomResponseDto)
 * 
 * Version Information :1.0
 * 
 * Copyright Notice : song(2022.05.11 ~ 2027.05.11)
 * 
 */

//패키지 정보
package com.springboot.study.web.dto;

//import 정보 
import java.util.Scanner; //왜 들고 왔는지

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class Description(클래스 정보) 
 * @author 누가 작생했는지(보통 github name을 사용)
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CMRespDto<T> {
	/*
	 * 1 OR -1 return
	 * 1 -> 성공
	 * -1 -> 실패
	 */
	private int code;
	/*
	 * 응답 메세지 내용
	 */
	private String msg;
	/*
	 * 응답 데이터
	 */
	private T data;
}
