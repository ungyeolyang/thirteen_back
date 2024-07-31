//package com.thirteen_back.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.thirteen_back.dto.ChatMessageDto;
//import com.thirteen_back.dto.ChatRoomResDto;
//import com.thirteen_back.service.ChatService;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@RequiredArgsConstructor
//@Slf4j
//@Component
//// WebSocket 연결과 메시지 수신/발신을 처리하는 역할을 합니다.
//// WebSocket의 세션 관리를 담당합니다.
//public class WebSocketHandler extends TextWebSocketHandler{
//    // JSON 데이터를 Java 객체로, 또는 그 반대로 변환하는 데 사용됩니다.
//    private final ObjectMapper objectMapper;
//    private final ChatService chatService;
//    // 특정 채팅방에 속한 모든 사용자의 WebSocket 세션을 관리합니다.
//    @Getter // 특정 방에 있는 모든 사용자에게 메시지를 브로드캐스트할 때 사용 // ConcurrentHashMap을 사용하여 스레드 안전성을 보장
//    private final Map<String, List<WebSocketSession>> userSessionMap = new ConcurrentHashMap<>();
//    // 각 WebSocket 세션이 속한 채팅방의 ID를 관리
//    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>(); // 세션이 속한 채팅방(roomID) 보기.
//
//    @Override // 클라이언트가 보낸 텍스트 메시지를 처리하는 메서드
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception { // 클라이언트가 보낸 텍스트 메시지를 처리하는 메서드
//        try {
//            String payload = message.getPayload(); // 클라이언트가 전송한 메시지
//            log.warn("payload : {}", payload);
//            // JSON 문자열을 ChatMessageDto 객체로 변환
//            ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
//            log.warn("chatMessage : {}", chatMessage.getRoomId());
//            //payload를 ChatMessageDto 객체로 변환합니다. objectMapper의 readValue 메서드를 사용하여 JSON 문자열을 지정된 클래스의 객체로 변환
//            ChatRoomResDto chatRoom = chatService.findRoomById(chatMessage.getRoomId());
////            log.warn("chatMessage : {}", chatMessage.getRoodId());
//            if (chatMessage.getRoomId() != null) {
//                sessionRoomIdMap.put(session, chatMessage.getRoomId()); // 세션과 채팅방 ID를 매핑
//                String roomId = chatRoom.getRoomId();
//                if (chatMessage.getType() == ChatMessageDto.MessageType.ENTER) { // 메시지 타입이 ENTER이면
//                    chatService.addSessionAndHandleEnter(roomId, session, chatMessage); // 채팅방에 입장한 세션 추가
//                } else if (chatMessage.getType() == ChatMessageDto.MessageType.CLOSE) {
//                    chatService.removeSessionAndHandleExit(roomId, session, chatMessage); // 채팅방 퇴장
//                } else { // ChatMessageDto.MessageType.TALK
//                    chatService.sendMessageToAll(roomId, chatMessage); // 내가 보낸 메세지가 모두에게 보이게 해, 원활한 채팅하기
//                    log.warn("여기까진됨");
//                }
//            }
//        } catch (Exception e) {
//            log.error("Error handling message: ", e);
//        }
//    }
//
//    @Override // 웹소켓 연결이 닫힐 때 호출되는 메서드
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // 세션과 매핑된 채팅방 ID 가져오기
//        log.warn("afterConnectionClosed : {}", session); // 로그에 경고 수준으로 웹소켓 세션이 종료되었음을 기록합니다.
//        String roomId = sessionRoomIdMap.remove(session); // sessionRoomIdMap은 Map<WebSocketSession, String> 타입
//        ChatRoomResDto chatRoom = chatService.findRoomById(roomId);
//        // Map의 remove(key) 함수는 제거하는 value 값을 반환함.
//        if (roomId != null) {
//            ChatMessageDto chatMessage = new ChatMessageDto();
//            chatMessage.setType(ChatMessageDto.MessageType.CLOSE);
//            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
//            if(chatRoom != null){
//                log.debug("Session closed: " + session);
//            } else {
//                // chatRoom이 null인 경우에 대한 처리
//                log.warn("Chat room not found for ID: {}", roomId);
//            }
//        }
//    }
//}
