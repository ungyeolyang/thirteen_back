package com.thirteen_back.service;

import com.thirteen_back.dto.BenefitDto;
import com.thirteen_back.entity.Benefit;
import com.thirteen_back.repository.BenefitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BenefitService {

    private final BenefitRepository benefitRepository;

    // 혜택 생성 & 저장
    public boolean saveBenefit(BenefitDto benefitDto) {
        Benefit benefit = Benefit.builder()
                .benefits(benefitDto.getBenefits())
                .build();
        benefitRepository.save(benefit);
        return true;
    }
    // 혜택 삭제
    public boolean deleteBenefit(Long bbno) {
        try {
            Benefit benefit = benefitRepository.findById(bbno).orElseThrow(
                    () -> new RuntimeException("해당 혜택은 존재하지 않습니다.")
            );
            benefitRepository.delete(benefit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 혜택 수정
    public boolean updateBenefit(BenefitDto dto) {
        log.warn("updateBenefit 실행!");
        Benefit benefit = benefitRepository.findById(dto.getBbno()).orElseThrow(
                () -> new RuntimeException("해당 혜택은 존재하지 않습니다."));
        benefit.setBenefits(dto.getBenefits());
        benefitRepository.save(benefit);
        return true;
    }
    // 혜택 목록 보여주기
    public List<BenefitDto> benefitList() {
        List<Benefit> benefits = benefitRepository.findAll();
        return benefits.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    private BenefitDto convertEntityToDto(Benefit benefit) {
        return BenefitDto.builder()
                .bbno(benefit.getBbno())
                .benefits(benefit.getBenefits())
                .build();
    }
}
