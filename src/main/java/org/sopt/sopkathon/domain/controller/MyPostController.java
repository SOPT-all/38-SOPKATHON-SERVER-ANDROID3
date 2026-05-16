package org.sopt.sopkathon.domain.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.code.PostSuccessCode;
import org.sopt.sopkathon.domain.dto.response.MyPostListResponseDto;
import org.sopt.sopkathon.domain.service.MyPostService;
import org.sopt.sopkathon.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class MyPostController {

    private final MyPostService myPostService;

    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<MyPostListResponseDto>> getMyPosts() {
        MyPostListResponseDto responseData = myPostService.getMyPostsAndReactions();
        return ResponseEntity.status(PostSuccessCode.GET_MY_POST.getHttpStatus())
                .body(BaseResponse.success(PostSuccessCode.GET_MY_POST, responseData));
    }
}