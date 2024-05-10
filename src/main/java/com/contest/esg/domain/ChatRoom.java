    package com.contest.esg.domain;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.socket.WebSocketSession;

    import java.util.HashMap;
    import java.util.HashSet;
    import java.util.Map;
    import java.util.Set;

    @Slf4j
    @Getter
    @Entity
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Table(name = "chatroom")
    public class ChatRoom {

        @Id
        @Column(name = "room_id")
        private String roomId;
        private String roomName;

        @Transient
        @JsonIgnore
        private Map<Long, WebSocketSession> sessions = new HashMap<>(); // 해당 방의 세션 리스트

        @JsonIgnore
        @OneToMany(mappedBy = "chatRoom")
        private Set<UserChatRoom> userChatRooms = new HashSet<>();

        @Builder
        public ChatRoom(String roomId, String roomName) {
            this.roomId = roomId;
            this.roomName = roomName;
        }

        public boolean isSessionExist(WebSocketSession session) {
            return sessions.containsValue(session);
        }

        public void addSession(Long userId, WebSocketSession session) {
            sessions.put(userId, session);
        }

        public void removeSession(Long userId) {
            sessions.remove(userId);
        }
    }