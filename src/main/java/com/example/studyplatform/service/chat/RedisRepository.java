package com.example.studyplatform.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
public class RedisRepository {
    private static final String ENTER_INFO = "ENTER_INFO";
    private static final String USER_INFO = "USER_INFO";

    /**
     * "ENTER_INFO", roomId, userId (유저가 입장한 채팅방 정보)
     */
    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Long> chatRoomInfo;

    /**
     * 채팅방 마다 유저가 읽지 않은 메세지 갯수 저장
     * 1:1 채팅에서만 사용 O, 그륩채팅에서 사용 X
     * roomId, userId, 안 읽은 메세지 갯수
     */
    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Integer> chatRoomUnReadMessageInfo;

    /**
     * 상대 정보는 sessionId 로 저장, 나의 정보는 userId에 저장
     * "USER_INFO", sessionId, userId
     */
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Long> userInfo;

    // step1
    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void userEnterRoomInfo(Long userId, Long chatRoomId) {
        chatRoomInfo.put(ENTER_INFO, userId, chatRoomId);
    }

    // 사용자가 채팅방에 입장해 있는지 확인
    public boolean existChatRoomUserInfo(Long userId) {
        return chatRoomInfo.hasKey(ENTER_INFO, userId);
    }

    // 사용자가 입장해 있는 채팅방 ID 조회
    public Long getUserEnterRoomId(Long userId) {
        return chatRoomInfo.get(ENTER_INFO, userId);
    }

    // 사용자가 입장해 있는 채팅방 ID 조회
    public void exitUserEnterRoomId(Long userId) {
        chatRoomInfo.delete(ENTER_INFO, userId);
    }

    // step2
    // 채팅방에서 사용자가 읽지 않은 메세지의 갯수 초기화
    public void initChatRoomMessageInfo(String chatRoomId, Long userId) {
        chatRoomUnReadMessageInfo.put(chatRoomId, userId, 0);
    }

    // 채팅방에서 사용자가 읽지 않은 메세지의 갯수 추가
    public void addChatRoomMessageCount(Long chatRoomId, Long userId) {
        chatRoomUnReadMessageInfo.put(chatRoomId+"", userId, chatRoomUnReadMessageInfo.get(chatRoomId+"", userId) + 1);
    }

    //
    public int getChatRoomMessageCount(String chatRoomId, Long userId) {
        return chatRoomUnReadMessageInfo.get(chatRoomId, userId);
    }

    // step3
    // 나의 대화상대 정보 저장
    public void saveMyInfo(String sessionId, Long userId) {
        userInfo.put(USER_INFO, sessionId, userId);
    }

    public boolean existMyInfo(String sessionId) {
        return userInfo.hasKey(USER_INFO, sessionId);
    }

    public Long getMyInfo(String sessionId) {
        return userInfo.get(USER_INFO, sessionId);
    }

    // 나의 대화상대 정보 삭제
    public void deleteMyInfo(String sessionId) {
        userInfo.delete(USER_INFO, sessionId);
    }

}
