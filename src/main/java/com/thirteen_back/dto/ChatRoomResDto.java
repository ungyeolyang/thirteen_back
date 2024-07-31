package com.thirteen_back.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
public class ChatRoomResDto {
    private String roomId; // 채팅방 ID
    //    private String name; // 채팅방 이름
    private LocalDateTime regDate; // 채팅방 생성 시간
    private Long pno;

    @JsonIgnore // 이 어노테이션으로 WebSocketSession의 직렬화를 방지
    private Set<WebSocketSession> sessions; // 채팅방에 입장한 세션 정보를 담을 Set // Set은 중복 허용 안함, 객체들의 집합을 중복없이 관리
    // 직렬화가 필요하지 않거나 직렬화가 불가능한 정보를 포함하고 있을 수 있어서 직렬화를 방지함으로써, 불필요하거나 위험한 데이터 변환을 막기 위해 사용
    // 세션 수가 0인지 확인하는 메서드
    public boolean isSessionEmpty() {
        return this.sessions.isEmpty(); // session이 아무도 없는지 확인
    }

    @Builder // 빌더 패턴 적용
    public ChatRoomResDto(String roomId, Long pno, LocalDateTime regDate) {
        this.roomId = roomId;
        this.pno = pno;
        this.regDate = regDate;
        this.sessions = Collections.newSetFromMap(new ConcurrentHashMap<>()); // 동시성 문제를 해결하기 위해 ConcurrentHashMap 사용
    }
}
