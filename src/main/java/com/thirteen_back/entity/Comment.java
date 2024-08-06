package com.thirteen_back.entity;

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
public class Comment {
    @Id
    @Column(name = "cno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno", nullable = false)
    private Board board;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime cdate;

    private int warning;
}
