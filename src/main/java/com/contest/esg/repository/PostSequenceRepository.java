package com.contest.esg.repository;

import com.contest.esg.domain.PostSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostSequenceRepository extends MongoRepository<PostSequence, String> {
}
