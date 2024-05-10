package com.contest.esg.repository;

import com.contest.esg.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMongoRepository extends MongoRepository<Post, String> {
}
