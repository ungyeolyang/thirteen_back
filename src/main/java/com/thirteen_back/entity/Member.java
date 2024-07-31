package com.thirteen_back.entity;

import com.thirteen_back.constant.Authority;
import com.thirteen_back.constant.Social;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "uno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uno;
    @Column(unique = true,nullable = false)
    private String mid;
    @Column(unique = true,nullable = false)
    private String nick;
    @Column(nullable = false)
    private String pwd;
    @Column(unique = true)
    private String email;
    private String image;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    @Enumerated(EnumType.STRING)
    private Social social;
    @Column(name="refresh_token")
    private String refreshToken;
    @Column(name="refresh_token_exp")
    private Long refreshTokenExpiresIn;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE)
    private List<Chatting> sentMessages;

    @Builder
    public Member(String nick, String mid, String pwd, String email, String image, Social social, Authority authority) {
        this.nick = nick;
        this.mid = mid;
        this.pwd = pwd;
        this.email = email;
        this.image = image;
        this.social = social;
        this.authority = authority;
    }
}