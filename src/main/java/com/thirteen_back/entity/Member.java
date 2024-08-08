package com.thirteen_back.entity;

import com.thirteen_back.constant.Authority;
import com.thirteen_back.constant.Social;
import com.thirteen_back.constant.TF;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "mno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mno;
    @Column(unique = true,nullable = false)
    private String mid;
    @Column(nullable = false)
    private String nick;
    @Column(nullable = false)
    private String pwd;
    @Column(unique = true)
    private String email;
    private String image;
    private LocalDateTime mdate;
    private TF withdrawal;
    private Long pay;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    @Enumerated(EnumType.STRING)
    private Social social;
    @Column(name="refresh_token")
    private String refreshToken;
    @Column(name="refresh_token_exp")
    private Long refreshTokenExpiresIn;

    @Builder
    public Member(String nick, String mid, String pwd, String email, String image, Social social, Authority authority) {
        this.nick = nick;
        this.mid = mid;
        this.pwd = pwd;
        this.email = email;
        this.image = image;
        this.social = social;
        this.authority = authority;
        this.mdate = LocalDateTime.now();
        this.withdrawal = TF.TRUE;
    }
}