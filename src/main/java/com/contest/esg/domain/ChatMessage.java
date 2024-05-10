package com.contest.esg.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash("chat")
public class ChatMessage {

    @Id
    private String key; // roomId + messageId
    private String roomId;
    private Long messageId;

    private Long userId;
    private String userName; // 닉네임
    private String message;
    private LocalDateTime timestamp;

    @Builder
    public ChatMessage(String key, String roomId, Long userId, Long messageId, String userName, String message, LocalDateTime timestamp) {
        this.key = key;
        this.roomId = roomId;
        this.userId = userId;
        this.messageId = messageId;
        this.userName = userName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
