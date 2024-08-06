package com.thirteen_back.controller;

import com.thirteen_back.dto.CommentDto;
import com.thirteen_back.entity.Comment;
import com.thirteen_back.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    // 댓글 저장
    @PostMapping("/new")
    public ResponseEntity<Boolean> saveComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.saveComment(commentDto));
    }
    // 댓글 삭제
    @DeleteMapping("/delete/{cno}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long cno){
        return ResponseEntity.ok(commentService.commentDelete(cno));
    }

    // 댓글 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> modifyComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.commentModify(commentDto));
    }
}
