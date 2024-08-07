package com.thirteen_back.repository;

import com.thirteen_back.constant.Authority;
import com.thirteen_back.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("관리자 만들기")
    public void adminModify(){
        Optional<Member> member = memberRepository.findByMid("12345");
        if (member.isPresent()){
            Member moMember = member.get();
            moMember.setAuthority(Authority.ROLE_ADMIN);
            memberRepository.save(moMember);
        }
        Optional<Member> member1= memberRepository.findByMid("12345");
        log.info("결과는 : "+member1.get().getAuthority().toString());
    }

    @Test
    public void boardFAQSave(){
    }
}