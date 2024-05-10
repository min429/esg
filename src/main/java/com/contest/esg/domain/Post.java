package com.contest.esg.domain;


import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    private String id;

    private Long postId;
    private Long userId;
    private String companyName;
    private String productName; // 게시글 명(제품 명)
    private String productCategory; // 제품군
    private String certification; // 영향범주
    private LocalDateTime postedDate;

    @Builder
    public Post(Long postId, Long userId, String companyName, String productName, String productCategory, String certification, LocalDateTime postedDate) {
        this.postId = postId;
        this.userId = userId;
        this.companyName = companyName;
        this.productName = productName;
        this.productCategory = productCategory;
        this.certification = certification;
        this.postedDate = postedDate;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
