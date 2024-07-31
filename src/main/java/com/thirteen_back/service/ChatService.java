//package com.thirteen_back.service;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.thirteen_back.dto.ChatRoomResDto;
//import com.thirteen_back.entity.ChattingRoom;
//import com.thirteen_back.repository.ChattingRepository;
//import com.thirteen_back.repository.ChattingRoomRepository;
//import com.thirteen_back.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.*;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class ChatService {
//    private final ObjectMapper objectMapper;
//    private final ChattingRoomRepository chatRoomRepository;
//    private final ChattingRepository chattingRepository;
//    private final MemberRepository memberRepository;
//    private  Map<String, List<WebSocketSession>> roomSessions = new HashMap<>();
//    private Map<String , ChatRoomResDto> chatRooms; // 채팅방 정보를 담을 앱
//    @PostConstruct // 의존성 주입 이후 초기화를 수행하는 메소드
//    private void init() { // 채팅방 정보를 담을 맵을 초기화 // init(): 메서드 이름은 보통 **초기화(Initialization)**를 의미합니다. 여기서는 chatRooms 변수를 초기화하는 역할을 합니다.
//        chatRooms = new LinkedHashMap<>(); // 채팅방 정보를 담을 맵 // LinkedHashMap: 이는 자바의 Map 인터페이스를 구현한 클래스 중 하나로, 삽입 순서를 유지하는 맵
//        // chatRooms 변수를 LinkedHashMap으로 초기화함으로써, 새로운 ChatRoomResDto 객체들이 삽입된 순서대로 정렬된 상태로 저장됩니다.
//        List<ChattingRoom> roomList = chatRoomRepository.findAll();
//        for (ChattingRoom cr : roomList) {
//            ChatRoomResDto dto = ChatRoomResDto.builder()
//                    .roomId(cr.getRoomId())
////                    .name(cr.getRoomName())
//                    .build();
//            chatRooms.put(cr.getRoomId(), dto);
//        }
//    }
//    public List<ChatRoomResDto> findAllRoom() { // 채팅방 리스트 반환
//        return new ArrayList<>(chatRooms.values()); // chatRooms은 Map으로 key와 value로 정의 되어었음. chatRooms의 value를 list로 뽑기.
//    }
//    public ChatRoomResDto findRoomById(String roomId) { // chatRooms의 key( roomId : Entity의 ID : type이 String )값을 통해 ChatRoomResDto타입의 value 값 반환하기
//        return chatRooms.get(roomId);
//    }
//
//    // 방 개설하기
//    public ChatRoomResDto createRoom(Party party) { //
//        String randomId = UUID.randomUUID().toString(); // roomId 생성 PK, String 타입 // 반환이 UUID 타입 객체라 toString()을 사용해 문자열로 만들어줌.
//        // UUID: UUID는 Universally Unique Identifier의 약자로, 전 세계적으로 고유한 식별자를 생성하는 데 사용됩니다. 이는 주로 중복될 가능성이 거의 없는 고유한 ID를 필요로 하는 시스템에서 사용됩니다.
//        // randomUUID(): UUID 클래스의 randomUUID 메서드는 랜덤한 UUID를 생성합니다. 이 메서드는 표준에 따라 128비트의 임의의 값을 생성하며, 이 값은 매우 낮은 확률로 중복될 수 있습니다.
//        log.info("UUID : " + randomId);
//
//        ChatRoomResDto chatRoom = ChatRoomResDto.builder() // 채팅방 생성
//                .roomId(randomId)
//                .regDate(LocalDateTime.now())
//                .pno(party.getPno())
//                .build();
//        chatRooms.put(randomId, chatRoom);  // 방 생성, 키를 UUID로 하고 방 정보를 값으로 저장 // chatRooms(채팅방 리스트)에 생성한 채팅방 추가함.
//
//        ChattingRoom room = ChattingRoom.builder()
//                .roomId(randomId)
//                .chatPno(party)
//                .createdAt(LocalDateTime.now())
//                .build();
//        chatRoomRepository.save(room);
//
//        // DB에 새로운 채팅방 저장
//        ChattingRoom newChatRoom = new ChattingRoom();
//        newChatRoom.setRoomId(chatRoom.getRoomId());
////        newChatRoom.setRoomName(chatRoom.getName());
//        newChatRoom.setCreatedAt(chatRoom.getRegDate());
//        chatRooms.put(randomId, chatRoom);
////        chatRoomRepository.save(newChatRoom);
//
//        return chatRoom; // 생성한 채팅방을 리턴
//    }
//    public void removeRoom(String roomId) { // 방 삭제
//        ChatRoomResDto room = chatRooms.get(roomId); // 방 정보 가져오기
////        if (room != null) { // 방이 존재하면
////            if (room.isSessionEmpty()) { // 방에 세션이 없으면
////                chatRooms.remove(roomId); // 방 삭제
////                // DB에서도 채팅방 삭제
////                chatRoomRepository.deleteById(roomId);
////            }
////        }
//    }
//    // 채팅방에 입장한 세션 추가
//    public void addSessionAndHandleEnter(String roomId, WebSocketSession session, ChatMessageDto chatMessage) {
//        ChatRoomResDto room = findRoomById(roomId); // key 값을 이용해 채팅방 정보 가져오기 // 채팅방 이름과 생성일자, 포함된 사람(세션)들
//        if (room != null) {
//            room.getSessions().add(session); // 채팅방에 입장한 세션 추가
//            if (chatMessage.getSender() != null) { // 채팅방에 입장한 사용자가 있으면
//                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
//                sendMessageToAll(roomId, chatMessage); // 채팅방에 입장 메시지 전송
//            }
//            log.debug("New session added: " + session);
//        }
//    }
//    // 채팅방에서 퇴장한 세션 제거
//    public void removeSessionAndHandleExit(String roomId, WebSocketSession session, ChatMessageDto chatMessage) {
//        ChatRoomResDto room = findRoomById(roomId); // 채팅방 정보 가져오기
//        if (room != null) {
//            room.getSessions().remove(session); // 채팅방에서 퇴장한 세션 제거 // Sessions는 Set<WebSocketSession> 타입, Set에서 특정 session 제거
//            if (chatMessage.getSender() != null) { // 채팅방에서 퇴장한 사용자가 있으면
//                chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다.");
//                sendMessageToAll(roomId, chatMessage); // 채팅방에 퇴장 메시지 전송
//            }
//            log.debug("Session removed: " + session);
////            if (room.isSessionEmpty()) {
////                removeRoom(roomId);
////            }
//        }
//    }
//
//    public void sendMessageToAll(String roomId, ChatMessageDto message) {
//        ChatRoomResDto room = findRoomById(roomId);
//        if (room != null) {
//            for (WebSocketSession session : room.getSessions()) { // Sessions는 Set<WebSocketSession> 타입이라 session은 WebSocketSession 타입으로 반복
//                sendMessage(session, message);
//            }
//        }
//    }
//
//    public <T> void sendMessage(WebSocketSession session, T message) {
//        try {
//            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
//        } catch(IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//    // 메세지 저장
//    public void saveMessage(String roomId, String senderNick, String message){
//        ChattingRoom chatRoom = chatRoomRepository.findById(roomId)
//                .orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));
//        Member user =memberRepository.findByNick(senderNick)
//                .orElseThrow(() -> new RuntimeException("해당 닉네임이 존재하지 않습니다."));
//        Chatting chat = Chatting.builder()
//                .chatRoom(chatRoom)
//                .message(message)
//                .sender(user)
//                .sentAt(LocalDateTime.now())
//                .build();
//        chattingRepository.save(chat);
//    }
//    // 세션 수 가져오기
//    public int getSessionCount(String roomId) {
//        List<WebSocketSession> sessions = roomSessions.get(roomId);
//        return sessions != null ? sessions.size() : 0;
//    }
//    // 이전 채팅 가져오기
//    public List<Chatting> getRecentMessages(String roomId) {
////        return chattingRepository.findRecentMessages(roomId);
//        ChattingRoom chat = chatRoomRepository.findById(roomId)
//                .orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));;
//        return chattingRepository.findByChatRoom(chat);
//    }
//}
