package com.thirteen_back.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long mno;
    private Long bno;
    private Long cno;
    private String text;
    private LocalDateTime cdate;
    private int warning;
}
