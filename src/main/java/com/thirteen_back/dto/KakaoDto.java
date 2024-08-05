package com.thirteen_back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thirteen_back.constant.Authority;
import com.thirteen_back.constant.Social;
import com.thirteen_back.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoDto {
    private String id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private Profile profile;
        private String email;

        @Data
        public static class Profile {
            @JsonProperty("nickname")
            private String nick;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }

    public KakaoDto() {}

    public Member toEntity(PasswordEncoder passwordEncoder) {

//        Random random = new Random();
//        int min = 111111;
//        int max = 999999;
//        String certification = String.valueOf(random.nextInt(max - min + 1) + min);

        return Member.builder()
                .pwd(passwordEncoder.encode(id))
                .mid(kakaoAccount.getEmail())
                .nick(kakaoAccount.getProfile().getNick())
                .image(kakaoAccount.getProfile().getProfileImageUrl())
                .social(Social.KAKAO)
                .authority(Authority.ROLL_USER)
                .build();
    }
}

