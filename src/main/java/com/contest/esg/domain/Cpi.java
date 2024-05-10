package com.contest.esg.domain;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cpi")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cpi { // 인증제품정보

    @Id
    private String id;

    private Long cpiId;
    private String companyName;
    private String registrationNumber;
    private String certification;
    private String productCategory;
    private String productName;

    @Builder
    public Cpi(Long cpiId, String companyName, String registrationNumber, String certification, String productCategory, String productName) {
        this.cpiId = cpiId;
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.certification = certification;
        this.productCategory = productCategory;
        this.productName = productName;
    }
}
