package com.thirteen_back.controller;

import com.thirteen_back.dto.CardDto;
import com.thirteen_back.entity.Member;
import com.thirteen_back.repository.BenefitRepository;
import com.thirteen_back.service.BenefitService;
import com.thirteen_back.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService cardService;

    // 카드 생성 & 저장
//    @PostMapping("/create")
//    public ResponseEntity<Boolean> createCard(@RequestBody CardDto cardDto) {
//        return ResponseEntity.ok(cardService.saveCard(cardDto));
//    }
//
//    // 카드 정보 수정
//    @PostMapping("/update")
//    public ResponseEntity<Boolean> updateCard(@RequestBody CardDto cardDto) {
//        return ResponseEntity.ok(cardService.updateCard(cardDto));
//    }
//
//    // 카드 삭제
//    @DeleteMapping("/delete/{cno}")
//    public ResponseEntity<Boolean> deleteCard(@PathVariable Long cno) {
//        return ResponseEntity.ok(cardService.deleteCard(cno));
//    }
//
//    // 카드 전체 목록 조회
//    @GetMapping("/list")
//    public ResponseEntity<List<CardDto>> cardList() {
//        return ResponseEntity.ok(cardService.cardList());
//    }
//
//    // 연회비 적은 순으로 조회
//    @GetMapping("/low-annual-fee")
//    public ResponseEntity<List<CardDto>> lowAnnualFeeCard() {
//        return ResponseEntity.ok(cardService.lowAnnualFeeAsc());
//    }
}
