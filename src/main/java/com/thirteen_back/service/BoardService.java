
package com.thirteen_back.service;

import com.thirteen_back.constant.BoardCategory;
import com.thirteen_back.dto.BoardDto;
import com.thirteen_back.entity.Board;
import com.thirteen_back.entity.Member;
import com.thirteen_back.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public BoardCategory reCate(String cate){
        if(cate.equals("faq")){
            return BoardCategory.BOARD_FAQ;
        } else if (cate.equals("gong")) {
            return BoardCategory.BOARD_GONG;
        } else {
            return BoardCategory.BOARD_MOUN;
        }
    }

    public boolean boardSave(BoardDto dto) {
        try {
            Member member = memberService.memberIdFindMember();
            BoardCategory boardCategory =reCate(dto.getCate());

            Board board = Board.builder()
                    .bdate(LocalDateTime.now())
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .category(boardCategory)
                    .memberbno(member)
                    .build();

            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BoardDto> selectBoard(String cate){
        List<BoardDto> list = new ArrayList<>();
        try{
            BoardCategory boardCategory =reCate(cate);
            List<Board> boards = boardRepository.findByCategory(boardCategory);
            for (Board b : boards) {
                BoardDto dto = BoardDto.of(b);
                list.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}