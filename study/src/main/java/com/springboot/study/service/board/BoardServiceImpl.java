package com.springboot.study.service.board;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.springboot.study.domain.board.BoardMst;
import com.springboot.study.domain.board.BoardRespository;
import com.springboot.study.web.dto.board.BoardInsertReqDto;
import com.springboot.study.web.dto.board.BoardRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // spring의 @Autowired와 같은 용도 final을 붙여줘야함
public class BoardServiceImpl implements BoardService {
	
	private final BoardRespository boardRespository;
	@Override
	public int createBoard(BoardInsertReqDto boardInsertReqDto) throws Exception {
		BoardMst boardMst =boardInsertReqDto.toBoardMstEntity();
		int result = boardRespository.insertBoard(boardMst);
		if(result>0) {
			return boardMst.getBoard_code();
		}
		
		return 0;
	}
	@Override
	public BoardRespDto getBoard(int boardCode) throws Exception {
		Map<String,Object> boardMap= boardRespository.getBoardByBoardCode(boardCode);
		return BoardRespDto.builder()
				.boardCode((Integer)(boardMap.get("board_code")))
				.title((String) (boardMap.get("board_title")))
				.content((String) (boardMap.get("board_content")))
				.usercode((Integer)(boardMap.get("board_wirter")))
				.username((String)(boardMap.get("board_username")))
				.build();
	}
}
