package com.thirteen_back.entity;

import com.thirteen_back.dto.CardDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Card {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cno;

    @Column(nullable = false, length = 250)
    private String name;

    @Column(length = 250)
    private String annualFee;

    @Column(length = 250)
    private String image;

    @Column(length = 250)
    private String performance; // 카드 기준 실적

    public CardDto toCardDto() {
        CardDto cardDto = new CardDto();
        cardDto.setCno(this.getCno());
        cardDto.setName(this.getName());
        cardDto.setImage(this.getImage());
        cardDto.setAnnualFee(this.getAnnualFee());
        cardDto.setPerformance(this.getPerformance());
        return cardDto;
    }
}
