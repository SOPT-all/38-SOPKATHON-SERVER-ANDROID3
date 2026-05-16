package org.sopt.sopkathon.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.code.PostSuccessCode;
import org.sopt.sopkathon.domain.dto.request.PostCreateRequest;
import org.sopt.sopkathon.domain.dto.response.PostCreateResponse;
import org.sopt.sopkathon.domain.dto.response.PostGetResponse;
import org.sopt.sopkathon.domain.service.PostService;
import org.sopt.sopkathon.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<BaseResponse<PostGetResponse>> getPosts() {
        PostGetResponse response = postService.getPosts();
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.GET_POSTS, response));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<PostCreateResponse>> createPost(
            @RequestBody @Valid PostCreateRequest request
    ) {
        PostCreateResponse response = postService.createPost(request);
        return ResponseEntity.status(PostSuccessCode.CREATE_POST.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.CREATE_POST, response));
    }
}
