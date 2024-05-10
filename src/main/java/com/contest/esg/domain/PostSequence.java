package com.contest.esg.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post_sequence")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSequence {

    @Id
    private String id;

    private Long postId;

    @Builder
    public PostSequence(String id, Long postId) {
        this.id = id;
        this.postId = postId;
    }
}
