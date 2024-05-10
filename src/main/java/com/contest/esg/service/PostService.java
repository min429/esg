package com.contest.esg.service;

import com.contest.esg.domain.Post;
import com.contest.esg.domain.PostSequence;
import com.contest.esg.repository.PostCustomMongoRepository;
import com.contest.esg.repository.PostMongoRepository;
import com.contest.esg.repository.PostSequenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostMongoRepository postMongoRepository;
    private final PostSequenceRepository postSequenceRepository;
    private final PostCustomMongoRepository postCustomMongoRepository;

    public void create(Post post) {
        setPostId(post);
        postMongoRepository.save(post);
    }

    public void delete(String id) {
        postMongoRepository.deleteById(id);
    }

    public Post findById(String id) {
        return postMongoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
    }

    public List<Post> findAll() {
        return postMongoRepository.findAll();
    }

    public List<Post> findByCompanyName(String companyName) {
        return postCustomMongoRepository.findByCompanyName(companyName);
    }

    public List<Post> findByProductName(String productName) {
        return postCustomMongoRepository.findByProductName(productName);
    }

    public Post setPostId(Post post) {
        List<PostSequence> sequences = postSequenceRepository.findAll();
        PostSequence sequence;

        if (sequences == null || sequences.isEmpty()) {
            sequence = PostSequence.builder()
                    .postId(1L)
                    .build();
        } else {
            sequence = sequences.get(0);
            sequence.setPostId(sequence.getPostId() + 1);
        }
        postSequenceRepository.save(sequence);
        post.setPostId(sequence.getPostId());

        return post;
    }
}
