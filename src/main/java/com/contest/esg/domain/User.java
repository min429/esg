package com.contest.esg.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String email;
    private String userPwd;
    private String userName;
    private String registrationNumber; // 사업자등록번호
    private String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserChatRoom> userChatRooms = new HashSet<>();

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
