package com.thirteen_back.dto;

import com.thirteen_back.constant.BoardCategory;
import com.thirteen_back.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long bno;
    private MemberResDto member;
    private String title;
    private String content;
//    private String warning; // 신고
    private LocalDateTime bdate;
    private String cate;

    public static BoardDto of(Board board){
        String category = "";
        if(board.getCategory()== BoardCategory.BOARD_FAQ){
            category="faq";
        } else if (board.getCategory()== BoardCategory.BOARD_GONG) {
            category="gong";
        } else if (board.getCategory()== BoardCategory.BOARD_MOUN) {
            category="moun";
        }

        return BoardDto.builder()
                .bno(board.getBno())
                .member(MemberResDto.of(board.getMemberbno()))
                .title(board.getTitle())
                .content(board.getContent())
//                .warning(board.getWarning())
                .bdate(board.getBdate())
                .cate(category)
                .build();
    }
}
