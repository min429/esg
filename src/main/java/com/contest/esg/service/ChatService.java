package com.contest.esg.service;

import com.contest.esg.domain.ChatMessage;
import com.contest.esg.domain.ChatRoom;
import com.contest.esg.repository.ChatCustomRedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {

    private final ObjectMapper objectMapper;
    private final ChatRoomService chatRoomService;
    private final ChatCustomRedisRepository chatCustomRedisRepository;
    private List<WebSocketSession> sessions = new ArrayList<>(); // 전체 세션 리스트

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void broadcastMessage(ChatRoom chatRoom, String message) {
        Map<Long, WebSocketSession> sessions = chatRoom.getSessions();
        sessions.values().parallelStream()
                .forEach(session -> sendMessage(session, message));
    }

    /** 채팅 메세지 처리 **/
    public void handlerActions(WebSocketSession session, ChatMessage chatMessage) {
        ChatRoom chatRoom = chatRoomService.findRoomById(chatMessage.getRoomId());
        if(!chatRoom.isSessionExist(session)) {
            chatRoom.addSession(chatMessage.getUserId(), session);
        }
        sendMessage(chatMessage, chatRoom);
    }

    private <T> void sendMessage(T message, ChatRoom chatRoom) {
        chatRoom.getSessions().values().parallelStream()
                .filter(session -> session.isOpen()) // 열려 있는 세션만 필터링
                .forEach(session -> sendMessage(session, message));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void save(ChatMessage chatMessage) {
        Long messageId = chatCustomRedisRepository.generateMessageId(chatMessage.getUserId()); // 자동 증가

        String key = chatMessage.getRoomId() + ":" + messageId;
        LocalDateTime now = LocalDateTime.now();
        chatMessage.setKey(key);
        chatMessage.setTimestamp(now);

        chatCustomRedisRepository.save(chatMessage);
    }

    // 이벤트 구독
    @EventListener
    public void handleChatRoomCreatedEvent(ChatRoomService.ChatRoomCreatedEvent event) {
        ChatRoom chatRoom = (ChatRoom) event.getSource();
        broadcastMessage(chatRoom, "new room"); // chatRoom guest에게 채팅방 생성 알림
    }
}