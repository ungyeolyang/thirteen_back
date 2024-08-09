package com.thirteen_back.entity;

import com.thirteen_back.constant.BoardCategory;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno")
    private Member memberbno;

    private String title;
    private String content;
    private LocalDateTime bdate;

    @Enumerated(EnumType.STRING)
    private BoardCategory category; // q&a인지 게시판인지
}
