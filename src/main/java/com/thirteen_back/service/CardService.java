package com.thirteen_back.service;

import com.thirteen_back.dto.CardDto;
import com.thirteen_back.entity.Card;
import com.thirteen_back.repository.BenefitRepository;
import com.thirteen_back.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final BenefitRepository benefitRepository;

    // 카드 생성 & 저장
    public boolean saveCard(CardDto cardDto) {
        Card card = Card.builder()
                .name(cardDto.getName())
                .annualFee(cardDto.getAnnualFee())
                .image(cardDto.getImage())
                .performance(cardDto.getPerformance())
                .build();
        cardRepository.save(card);
        return true;
    }
    // 카드 삭제
    public boolean deleteCard(Long cno) {
        try {
            Card card = cardRepository.findById(cno).orElseThrow(
                    () -> new RuntimeException("해당 카드는 존재하지 않습니다.")
            );
            cardRepository.delete(card);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 카드 수정
    public boolean updateCard(CardDto dto) {
        log.warn("updateCard 실행!");
        Card card = cardRepository.findById(dto.getCno()).orElseThrow(
                () -> new RuntimeException("해당 카드는 존재하지 않습니다."));
        card.setName(dto.getName());
        card.setImage(dto.getImage());
        card.setAnnualFee(dto.getAnnualFee());
        card.setPerformance(dto.getPerformance());
        cardRepository.save(card);
        return true;
    }
    // 카드 목록 보여주기
    public List<CardDto> cardList() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    private CardDto convertEntityToDto(Card card) {
        return CardDto.builder()
                .cno(card.getCno())
                .name(card.getName())
                .annualFee(card.getAnnualFee())
                .image(card.getImage())
                .performance(card.getPerformance())
                .build();
    }

    public List<CardDto> lowAnnualFeeAsc() {
        List<Card> cards = cardRepository.findAllByOrderByAnnualFeeAsc();
        return cards.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
}
