package com.example.studyplatform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 웹 소켓을 사용함
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 해당 경로로 들어오는것을 구독하는것으로 정한다.
        registry.enableSimpleBroker("/sub");
        // @MessageMapping("hello") 라면 경로는 -> /pub/hello
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("stomp/chat") // ex ) ws://localhost:8080/stomp/chat
                .setAllowedOriginPatterns("*") // 일단 모두 허용
                .withSockJS(); // 웹 소켓 사용
    }

// TODO : StompHandler 작업 후 추가 해야하는 부분
//    @Override
//    public void configureClientInboundChannel (ChannelRegistration registration){
//        registration.interceptors(stompHandler);
//    }

}
