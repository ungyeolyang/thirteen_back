package com.thirteen_back.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {
    public enum MessageType{
        ENTER, TALK, CLOSE;
    }
    private MessageType type;
    private String roomId;
    private String sender; // 유저 이름
    private String message;
    private LocalDateTime sentAt;
    private String image;
}
