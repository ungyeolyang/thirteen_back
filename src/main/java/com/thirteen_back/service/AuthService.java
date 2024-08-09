package com.thirteen_back.service;

import com.thirteen_back.constant.TF;
import com.thirteen_back.dto.MemberReqDto;
import com.thirteen_back.dto.MemberResDto;
import com.thirteen_back.dto.TokenDto;
import com.thirteen_back.entity.Member;
import com.thirteen_back.jwt.TokenProvider;
import com.thirteen_back.repository.MemberRepository;
import com.thirteen_back.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    public Boolean existInfo(String info, int type){
        Boolean res = true;
        switch (type){
            case 1: return memberRepository.existsByMid(info);
            case 2:  return memberRepository.existsByEmail(info);
            case 3: return memberRepository.existsByNick(info);
            default: return res;
        }
    }
    public String findInfo(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()){
            Member mem = member.get();
            return mem.getMid();
        }
        else return null;
    }

    public MemberResDto signup(MemberReqDto requestDto) {
        Member member = requestDto.toEntity(passwordEncoder);
        return MemberResDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberReqDto requestDto) {
        Optional<Member> mem = memberRepository.findByMid(requestDto.getMid());
        if(mem.isPresent()){
            if(mem.get().getWithdrawal().equals(TF.FALSE)){
              return null;
            }
        }
        try{
            UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            TokenDto token = tokenProvider.generateTokenDto(authentication);
            log.info(String.valueOf(token));

            Member member = memberRepository.findByMid(requestDto.getMid()).get();
            String encodedToken = token.getRefreshToken();
            member.setRefreshToken(encodedToken.concat("="));
            member.setRefreshTokenExpiresIn(token.getRefreshTokenExpiresIn());

            memberRepository.save(member);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("현재 인증 정보: " + auth);

            return token;

        } catch (Exception e) {
            log.error("로그인 중 에러 발생 : ", e);
            throw new RuntimeException("로그인 중 에러 발생", e);
        }
    }

    public TokenDto refreshAccessToken(String refreshToken) {
        log.info("refreshToken : {}", refreshToken);
        log.info("일반refreshExist : {}", memberRepository.existsByRefreshToken(refreshToken));

        //DB에 일치하는 refreshToken이 있으면
        if(memberRepository.existsByRefreshToken(refreshToken)){
            // refreshToken 검증
            try {
                if(tokenProvider.validateToken(refreshToken)) {
                    return tokenProvider.generateTokenDto(tokenProvider.getAuthentication(refreshToken));
                }
            }catch (RuntimeException e) {
                log.error("토큰 유효성 검증 중 예외 발생 : {}", e.getMessage());
            }
        }
        return null;
    }
    public Boolean resetPw(MemberReqDto memberReqDto){
        if (memberRepository.findByMid(memberReqDto.getMid()).isEmpty()) {
            return false;
        }
        Member member = memberRepository.findByMid(memberReqDto.getMid()).get();
        member.setPwd(passwordEncoder.encode(memberReqDto.getPwd()));
        memberRepository.save(member);
        return true;
    }
}
