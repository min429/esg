package com.contest.esg.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_chatroom")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_chatroom_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Builder
    public UserChatRoom(Long id, User user, ChatRoom chatRoom) {
        this.id = id;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
