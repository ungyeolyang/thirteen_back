package com.thirteen_back.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDto {
    private Long cno;
    private String name;
    private String annualFee;
    private String image;
    private String performance; // 카드 기준 실적
}
