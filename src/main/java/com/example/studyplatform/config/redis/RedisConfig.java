package com.example.studyplatform.config.redis;

import com.example.studyplatform.service.chat.RedisSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    /*
    redis의 pub/sub 기능을 이용하기 위해 MessageListener 설정 추가
    메시지 발행이 오면 Listener가 처리함
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter,
            ChannelTopic channelTopic
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // RedisMessageListenerContainer 에 Bean 으로 등록한 listenerAdapter, channelTopic 추가
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }

    /*
    pub/sub 통신에 사용할 redisTemplate 설정
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    /*
      실제 메시지를 처리하는 subscriber 설정 추가
      sendMessage 라는 메소드가 -> RedisSubscriber 클래스 안에 오버라이딩 된 onMessage 로 처리하도록 함
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "sendMessage");
    }

    /*
      단일 Topic 사용을 위한 Bean 설정
      사실 중요한 부분인지 모르겠음
      계속해서 생성할 필요가 없어 보여 빈으로 등록함
     */
    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }
}
