package org.sopt.sopkathon.domain.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.code.PostSuccessCode;
import org.sopt.sopkathon.domain.dto.request.PostCreateRequest;
import org.sopt.sopkathon.domain.dto.response.PostCreateResponse;
import org.sopt.sopkathon.domain.service.PostService;
import org.sopt.sopkathon.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<BaseResponse<PostCreateResponse>> createPost(
            @RequestBody @Valid PostCreateRequest request
    ) {
        PostCreateResponse response = postService.createPost(request);
        return ResponseEntity.status(PostSuccessCode.CREATE_POST.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.CREATE_POST, response));
    }
}
