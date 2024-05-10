package com.contest.esg.repository;

import com.contest.esg.domain.Cpi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CpiMongoRepository extends MongoRepository<Cpi, String> {
    List<Cpi> findByRegistrationNumber(String registrationNumber);
}
