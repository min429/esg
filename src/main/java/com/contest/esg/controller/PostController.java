package com.contest.esg.controller;

import com.contest.esg.config.CommonResponse;
import com.contest.esg.domain.Post;
import com.contest.esg.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public CommonResponse<String> create(@RequestBody Post post) {
        postService.create(post);
        return CommonResponse.success();
    }

    @PostMapping("/delete/{id}")
    public CommonResponse<String> delete(@Parameter(name = "id", description = "게시글 번호", in = ParameterIn.PATH) @PathVariable("id") String id) {
        postService.delete(id);
        return CommonResponse.success();
    }

    @GetMapping("/{id}")
    public CommonResponse<Post> findByPostId(@Parameter(name = "id", description = "게시글 번호", in = ParameterIn.PATH) @PathVariable("id") String id) {
        return CommonResponse.success(postService.findByPostId(id));
    }

    @GetMapping("/all")
    public CommonResponse<List<Post>> findAll() {
        return CommonResponse.success(postService.findAll());
    }

    @GetMapping("/{companyName}")
    public CommonResponse<List<Post>> findByCompanyName(@Parameter(name = "companyName", description = "회사명", in = ParameterIn.PATH) @PathVariable("companyName") String companyName) {
        return CommonResponse.success(postService.findByCompanyName(companyName));
    }

    @PostMapping("/{productName}")
    public CommonResponse<List<Post>> findByProductName(@Parameter(name = "productName", description = "제품명", in = ParameterIn.PATH) @PathVariable("productName") String productName) {
        return CommonResponse.success(postService.findByProductName(productName));
    }
}
