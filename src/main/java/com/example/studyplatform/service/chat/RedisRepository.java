package com.example.studyplatform.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
public class RedisRepository {
    private static final String ENTER_INFO = "ENTER_INFO";

    // "ENTER_INFO", roomId, userId (유저가 입장한 채팅방 정보)
    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, String> chatRoomInfo;

    
}
