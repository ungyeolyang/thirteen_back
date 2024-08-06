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
public class Benefit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bbno;

    @Column(nullable = false)
    private String benefits;
}
