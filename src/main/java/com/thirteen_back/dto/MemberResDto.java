package com.thirteen_back.dto;

import com.thirteen_back.constant.Social;
import com.thirteen_back.constant.TF;
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
    private TF tf;
    private Long pay;

    public static MemberResDto of (Member member){
        return MemberResDto.builder()
                .mid(member.getMid())
                .nick(member.getNick())
                .email(member.getEmail())
                .image(member.getImage())
                .social(member.getSocial())
                .tf(member.getWithdrawal())
                .pay(member.getPay())
                .build();
    }
}