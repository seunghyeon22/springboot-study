package com.springboot.study.web.dto.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.springboot.study.domain.board.BoardMst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardInsertReqDto {
	@NotBlank
	private String title;
	@NotBlank //int는 사용 불가능 
	private String content;
	@NotNull
	private int usercode;
	
	public BoardMst toBoardMstEntity() {
		return BoardMst.builder()
				.board_title(title)
				.board_content(content)
				.board_wirter(usercode)
				.build();
	}
}	
