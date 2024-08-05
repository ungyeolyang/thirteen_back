package com.thirteen_back.dto;

import com.thirteen_back.constant.Social;
import com.thirteen_back.entity.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String nick;
    private String email;
    private String mid;
    private String image;
    private Social social;

    public static MemberResDto of (Member member){
        return MemberResDto.builder()
                .mid(member.getMid())
                .nick(member.getNick())
                .email(member.getEmail())
                .image(member.getImage())
                .social(member.getSocial())
                .build();
    }
}