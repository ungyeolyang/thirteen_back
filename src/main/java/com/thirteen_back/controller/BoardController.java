
package com.thirteen_back.controller;

import com.thirteen_back.constant.TF;
import com.thirteen_back.dto.BoardDto;
import com.thirteen_back.dto.CommentDto;
import com.thirteen_back.dto.MemberResDto;
import com.thirteen_back.service.BoardService;
import com.thirteen_back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    public final MemberService memberService;
    public final BoardService boardService;

    @GetMapping("/alluser/{page}")
    public ResponseEntity<Map<String, Object>> getAllUser(@PathVariable("page") int page){
        return ResponseEntity.ok(memberService.allUsers(page));
    }

    @GetMapping("/blist/{cate}/{page}")
    public ResponseEntity<Map<String, Object>> boardList(@PathVariable("cate") String cate, @PathVariable("page") int page){
        return ResponseEntity.ok(boardService.selectBoard(cate,page));
    }

    @GetMapping("/bdetail/{bno}")
    public ResponseEntity<BoardDto> getBoardDetail(@PathVariable("bno") Long bno){
        return ResponseEntity.ok(boardService.getBoardDetail(bno));
    }

    @PostMapping("/bsave")
    public ResponseEntity<Boolean> insertBoard(@RequestBody BoardDto dto){
        return ResponseEntity.ok(boardService.boardSave(dto));
    }

    @PostMapping("/userback/{mid}/{tf}")
    public ResponseEntity<Boolean> userComeBack(@PathVariable("mid") String mid, @PathVariable("tf") boolean tf){
        return ResponseEntity.ok(boardService.memberComeBack(mid,tf));
    }

    @PostMapping("/bupdate")
    public ResponseEntity<Boolean> boardUpdate(@RequestBody BoardDto dto){
        return ResponseEntity.ok(boardService.modifyBoard(dto));
    }

    @PostMapping("/bdelete/{bno}")
    public ResponseEntity<Boolean> boardDelete(@PathVariable("bno") Long bno){
        return ResponseEntity.ok(boardService.deleteBoard(bno));
    }

    @GetMapping("/bcomment/{bno}")
    public ResponseEntity<CommentDto> viewComment(@PathVariable("bno") Long bno){
        return ResponseEntity.ok(boardService.selectCnoComment(bno));
    }

    @PostMapping("/csave")
    public ResponseEntity<Boolean> commentSave(@RequestBody CommentDto dto){
        return ResponseEntity.ok(boardService.createComment(dto));
    }

    @PostMapping("/cupdate")
    public ResponseEntity<Boolean> commentUpdate(@RequestBody CommentDto dto){
        return ResponseEntity.ok(boardService.modifyComment(dto));
    }

    @GetMapping("/graph")
    public ResponseEntity<List<MemberResDto>> graphMemberList(){
        return ResponseEntity.ok(boardService.allUserTf());
    }
}

