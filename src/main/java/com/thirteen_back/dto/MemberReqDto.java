package com.thirteen_back.dto;

import com.thirteen_back.constant.Authority;
import com.thirteen_back.constant.Social;
import com.thirteen_back.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberReqDto {
    private String email;
    private String mid;
    private String pwd;
    private String nick;

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(email)
                .mid(mid)
                .pwd(passwordEncoder.encode(pwd))
                .nick(nick)
                .social(Social.COMMON)
                .authority(Authority.ROLL_USER)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(mid,pwd);
    }

    public static MemberReqDto of(Member member){
        return MemberReqDto.builder()
                .mid(member.getMid())
                .pwd(member.getPwd())
                .build();
    }
}
