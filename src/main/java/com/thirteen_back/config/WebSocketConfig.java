package com.thirteen_back.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// WebSocket의 설정을 관리합니다.
// WebSocket 엔드포인트를 등록하고, 특정 경로에서 WebSocketHandler를 매핑하는 역할을 합니다.
// CORS 설정이나 WebSocket의 다양한 속성을 설정할 수 있습니다.
@Configuration // Spring의 설정 클래스임을 나타내고 이 클래스를 빈으로 등록하고, 관련된 다른 빈들과의 의존성 주입을 처리할 수 있습니다.
@RequiredArgsConstructor // final 필드나 @NonNull으로 선언된 필드에 대한 생성자를 자동으로 생성하여 의존성 주입을 편리하게 처리할 수 있습니다.
@EnableWebSocket // Spring이 WebSocket 관련 구성을 인식하고 처리하기 위한 어노테이션
public class WebSocketConfig implements WebSocketConfigurer{
    private final WebSocketHandler webSocketHandler;
    //    registerWebSocketHandlers 메서드는 실제 WebSocket 핸들러를 등록하는 메서드입니다. WebSocketHandlerRegistry 객체를 통해 핸들러를 등록하고 설정할 수 있습니다.
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //    registry.addHandler(webSocketHandler, "ws/chat"): webSocketHandler를 "ws/chat" 경로에 등록합니다. 이 경로는 클라이언트가 WebSocket 연결을 시도할 때 사용됩니다.
        registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
//        registry.addHandler()
//    .setAllowedOrigins("*"): 모든 오리진에서의 접근을 허용합니다. 이 부분을 특정 도메인이나 URL 패턴으로 설정할 수도 있습니다.
    } // 이 설정을 통해 클라이언트는 "ws://도메인주소/ws/chat" 경로를 통해 WebSocket 연결을 시도할 수 있으며, 이 연결은 webSocketHandler에서 처리될 것입니다.
}
