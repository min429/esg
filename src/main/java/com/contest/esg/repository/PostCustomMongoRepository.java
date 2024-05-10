package com.contest.esg.repository;

import com.contest.esg.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostCustomMongoRepository {
    private final MongoTemplate mongoTemplate;

    public List<Post> findByCompanyName(String companyName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("companyName").regex(".*" + companyName + ".*"));
        return mongoTemplate.find(query, Post.class, "post");
    }

    public List<Post> findByProductName(String productName) {
        System.out.println(productName);
        Query query = new Query();
        query.addCriteria(Criteria.where("productName").regex(".*" + productName + ".*", "i"));
        System.out.println(query);
        return mongoTemplate.find(query, Post.class, "post");
    }
}
