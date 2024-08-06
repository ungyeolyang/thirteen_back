package com.thirteen_back.dto;

import com.thirteen_back.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {
    private Long bno;
    private String title;
    private Long mno;
    private String content;
    private LocalDateTime bdate;
    private String category;
    private int warning; // 게시글 신고 수

    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .bdate(board.getBdate())
                .warning(board.getWarning())
                .category(String.valueOf(board.getCategory()))
                .mno(board.getMember().getMno())
                .build();
    }
}
