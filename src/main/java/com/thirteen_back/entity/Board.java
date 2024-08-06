package com.thirteen_back.entity;

import com.thirteen_back.constant.Category;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Board {
    @Id
    @Column(name = "bno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bno;

    @Column(nullable = false, length = 250)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime bdate;
    private int warning; // 게시글 신고 수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 250)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "mno", nullable = false)
    private Member member;
}
