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
@Table(name = "Chatting_Room")

public class ChattingRoom {
    @Id // UUID로 String 타입 RoomID 반환
    @Column(name="room_id")
    private String roomId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
