
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

    @GetMapping("/bdetail/{bno}")
    public ResponseEntity<BoardDto> getBoardDetail(@PathVariable("bno") Long bno){
        return ResponseEntity.ok(boardService.getBoardDetail(bno));
    }

    @PostMapping("/bsave")
    public ResponseEntity<Boolean> insertBoard(@RequestBody BoardDto dto){
        return ResponseEntity.ok(boardService.boardSave(dto));
    }

    @PostMapping("/userback/{mid}")
    public ResponseEntity<Boolean> userComeBack(@PathVariable("mid") String mid){
        return ResponseEntity.ok(boardService.memberComeBack(mid));
    }

    @PostMapping("/bupdate")
    public ResponseEntity<Boolean> boardUpdate(@RequestBody BoardDto dto){
        return ResponseEntity.ok(boardService.modifyBoard(dto));
    }

    @PostMapping("/bdelete/{bno}")
    public ResponseEntity<Boolean> boardDelete(@PathVariable("bno") Long bno){
        return ResponseEntity.ok(boardService.deleteBoard(bno));
    }
}

