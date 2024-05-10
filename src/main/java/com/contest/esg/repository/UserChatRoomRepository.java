package com.contest.esg.repository;

import com.contest.esg.domain.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    Set<UserChatRoom> findByUser_UserId(Long userId);
}
