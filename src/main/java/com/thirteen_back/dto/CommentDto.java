package com.thirteen_back.dto;

import com.thirteen_back.entity.Comment;
import com.thirteen_back.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
//    private Long mno;
    private MemberResDto member;
    private BoardDto board;
    private Long cno;
    private String comment;

    public static CommentDto of (Comment comment){
        return CommentDto.builder()
                .board(BoardDto.of(comment.getBoardcno()))
//                .mno(comment.getMembercno().getMno())
                .member(MemberResDto.of(comment.getMembercno()))
                .cno(comment.getCno())
                .comment(comment.getComment())
                .build();
    }
}
