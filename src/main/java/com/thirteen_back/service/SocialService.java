package com.thirteen_back.service;

import com.thirteen_back.dto.KakaoDto;
import com.thirteen_back.dto.MemberReqDto;
import com.thirteen_back.entity.Member;
import com.thirteen_back.repository.MemberRepository;
import com.thirteen_back.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {
    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;
    private final PasswordEncoder passwordEncoder;

//    public static String extractNickname(String input) {
//        int index = input.indexOf('#');
//        if (index != -1) {
//            return input.substring(0, index);
//        } else {
//            return input; // '#' 문자가 없으면 원본 문자열 반환
//        }
//    }
//    public static String extractRest(String input) {
//        int index = input.indexOf('#');
//        if (index != -1) {
//            return input.substring(index + 1);
//        } else {
//            return ""; // '#' 문자가 없으면 빈 문자열 반환
//        }
//    }
    public MemberReqDto kakaoUserInfo(String kakaoToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + kakaoToken);
        String url = "https://kapi.kakao.com/v2/user/me";
        MemberReqDto memberReqDto = new MemberReqDto();
        try {
            ResponseEntity<KakaoDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KakaoDto.class
            );
            KakaoDto kakaoDto = responseEntity.getBody();
            String mid = kakaoDto.getKakaoAccount().getEmail();
            String pwd = kakaoDto.getId();
            String nick = kakaoDto.getKakaoAccount().getProfile().getNick();
            String image = kakaoDto.getKakaoAccount().getProfile().getProfileImageUrl();
            memberReqDto.setMid(mid);
            memberReqDto.setPwd(pwd);

            if(socialRepository.findByMid(mid).isPresent()){
                Member member = socialRepository.findByMid(mid).get();
                if(member.getNick().equals(nick)){
                    member.setNick(nick);
                    socialRepository.save(member);
                }else if(!socialRepository.findByMid(mid).get().getImage().equals(image)) {
                    member.setImage(image);
                    socialRepository.save(member);
                }
                return memberReqDto;
            }
            else{
                saveKakaoEntity(kakaoDto);
                return memberReqDto;
            }
        }catch(Exception e) {
            log.error("카카오 가입 시도 중 오류 발생(카카오 서비스)");
            return null;
        }
    }

    private void saveKakaoEntity(KakaoDto kakaoDto) {
        Member member = kakaoDto.toEntity(passwordEncoder);
        memberRepository.save(member);
    }
}
