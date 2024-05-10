package com.contest.esg.repository;

import com.contest.esg.domain.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRedisRepository extends CrudRepository<ChatMessage, String> {
}
