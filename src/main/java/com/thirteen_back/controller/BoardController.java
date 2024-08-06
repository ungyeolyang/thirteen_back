package com.thirteen_back.controller;

import com.thirteen_back.dto.BoardDto;
import com.thirteen_back.service.BoardService;
import com.thirteen_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/myboardlist")
    public ResponseEntity<List<BoardDto>> myBoardList(@RequestParam Long mno) {
        List<BoardDto> list = boardService.myBoardList(mno);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/board")
    public ResponseEntity<Boolean> createBoard(@RequestBody BoardDto board) {
        return ResponseEntity.ok(boardService.saveBoard(board));
    }
    @PostMapping("/delete/{bno}")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable Long bno) {
        boolean result = boardService.deleteBoard(bno);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> boardUpdate(@RequestBody BoardDto board) {
        return ResponseEntity.ok(boardService.updateBoard(board));
    }
}
