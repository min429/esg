package com.contest.esg.repository;

import com.contest.esg.domain.Cpi;
import com.contest.esg.domain.PostSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostSequenceRepository extends MongoRepository<PostSequence, String> {
    PostSequence findByPostId(Long postId);
}
