package com.contest.esg.service;

import com.contest.esg.domain.Cpi;
import com.contest.esg.repository.CpiMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CpiService {

    private final CpiMongoRepository cpiMongoRepository;

    public List<Cpi> findAllByRegistrationNumber(String registrationNumber) {
        return cpiMongoRepository.findByRegistrationNumber(registrationNumber);
    }
}
