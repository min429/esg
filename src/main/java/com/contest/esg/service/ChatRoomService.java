package com.contest.esg.service;

import com.contest.esg.controller.dto.CreateRoomRequest;
import com.contest.esg.controller.dto.ExitRequest;
import com.contest.esg.domain.ChatMessage;
import com.contest.esg.domain.ChatRoom;
import com.contest.esg.domain.User;
import com.contest.esg.domain.UserChatRoom;
import com.contest.esg.repository.ChatCustomRedisRepository;
import com.contest.esg.repository.ChatRoomRepository;
import com.contest.esg.repository.UserChatRoomRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ChatRoomService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatCustomRedisRepository chatCustomRedisRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserService userService;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        for (ChatRoom room : rooms) {
            chatRooms.put(room.getRoomId(), room);
        }
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public List<ChatRoom> findAllRoomsByUserId(Long userId) {
        List<ChatRoom> chatRooms = new LinkedList<>();
        Set<UserChatRoom> userChatRooms = userChatRoomRepository.findByUser_UserId(userId);
        for (UserChatRoom userChatRoom : userChatRooms) {
            chatRooms.add(userChatRoom.getChatRoom());
        }
        return chatRooms;
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public List<ChatMessage> findChatMessages(String roomId) {
        return chatCustomRedisRepository.findAllByRoomId(roomId);
    }

    public ChatRoom createRoom(CreateRoomRequest createRoomRequest) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .roomName(createRoomRequest.getRoomName())
                .build();
        chatRooms.put(randomId, chatRoom);
        chatRoomRepository.save(chatRoom);

        Set<User> users = getUsers(createRoomRequest);
        users.forEach(user -> userChatRoomRepository.save(UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build()));

        applicationEventPublisher.publishEvent(new ChatRoomCreatedEvent(chatRoom)); // 이벤트 발행

        return chatRoom;
    }

    private Set<User> getUsers(CreateRoomRequest createRoomRequest) {
        User host = userService.getUserById(createRoomRequest.getHostId());
        User guest = userService.getUserById(createRoomRequest.getGuestId());
        Set<User> users = new HashSet<>();
        users.add(host);
        users.add(guest);
        return users;
    }

    public void exit(ExitRequest exitRequest) {
        ChatRoom chatRoom = chatRooms.get(exitRequest.getRoomId());
        chatRoom.removeSession(exitRequest.getUserId());

        Set<UserChatRoom> userChatRoom = userChatRoomRepository.findByUser_UserId(exitRequest.getUserId());
        userChatRoomRepository.deleteAll(userChatRoom);

        chatRooms.put(exitRequest.getRoomId(), chatRoom);
        chatRoomRepository.save(chatRoom);
    }

    // 순환참조를 피하기 위한 이벤트 기반 아키텍처
    public class ChatRoomCreatedEvent extends ApplicationEvent {
        public ChatRoomCreatedEvent(Object source) {
            super(source);
        }
    }
}
