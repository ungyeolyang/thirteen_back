
package com.thirteen_back.controller;

import com.thirteen_back.dto.BoardDto;
import com.thirteen_back.dto.MemberResDto;
import com.thirteen_back.service.BoardService;
import com.thirteen_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    public final MemberService memberService;
    public final BoardService boardService;

    @GetMapping("/alluser")
    public ResponseEntity<List<MemberResDto>> getAllUser(){
        return ResponseEntity.ok(memberService.allUsers());
    }

    @GetMapping("/blist/{cate}")
    public ResponseEntity<List<BoardDto>> boardList(@PathVariable("cate") String cate){
        return ResponseEntity.ok(boardService.selectBoard(cate));
    }

    @PostMapping("/bsave")
    public ResponseEntity<Boolean> insertBoard(@RequestBody BoardDto boardDto){
        return ResponseEntity.ok(boardService.boardSave(boardDto));
    }
}

