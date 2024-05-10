package com.contest.esg.repository;

import com.contest.esg.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ChatCustomRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRedisRepository chatRedisRepository;

    public void save(ChatMessage chatMessage) {
        String key = chatMessage.getKey();
        double score = chatMessage.getTimestamp().toEpochSecond(ZoneOffset.UTC);

        chatRedisRepository.save(chatMessage);
        // 채팅 시간에 따라 키 값 저장 (해시 데이터는 순서를 보장하지 않으므로 데이터 순서를 별도로 관리해주어야 함)
        redisTemplate.opsForZSet().add("chatMessage:timestamp:" + chatMessage.getRoomId(), key, score);
    }

    public Optional<ChatMessage> findByKey(String key) {
        return chatRedisRepository.findById(key);
    }

    public List<ChatMessage> findAllByRoomId(String roomId) {
        Set<String> keys = redisTemplate.opsForZSet().rangeByScore("chatMessage:timestamp:" + roomId, Double.MIN_VALUE, Double.MAX_VALUE)
                .stream().map(Object::toString).collect(Collectors.toSet());

        List<ChatMessage> chatMessages = new LinkedList<>();
        for (String key : keys) {
            chatMessages.add(chatRedisRepository.findById(key)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅 리스트")));
        }
        return chatMessages;
    }

    public Long generateMessageId(Long userId) {
        String key = "messageId:" + userId;
        return redisTemplate.opsForValue().increment(key);
    }
}
