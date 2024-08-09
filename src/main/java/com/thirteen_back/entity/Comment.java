package com.thirteen_back.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Member membercno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")
    private Board boardcno;

    private String comment;
}
