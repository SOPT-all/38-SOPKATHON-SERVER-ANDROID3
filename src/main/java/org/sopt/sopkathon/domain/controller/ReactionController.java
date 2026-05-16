package org.sopt.sopkathon.domain.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.code.ReactionSuccessCode;
import org.sopt.sopkathon.domain.dto.request.ReactionRequestDto;
import org.sopt.sopkathon.domain.service.ReactionService;
import org.sopt.sopkathon.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/{postId}/reactions")
    public ResponseEntity<BaseResponse<Void>> createReaction(
            @PathVariable Long postId,
            @RequestBody ReactionRequestDto requestDto
    ) {
        reactionService.createReaction(postId, requestDto);

        return ResponseEntity
                .status(ReactionSuccessCode.CREATE_REACTION_SUCCESS.getHttpStatus())
                .body(BaseResponse.success(
                        ReactionSuccessCode.CREATE_REACTION_SUCCESS
                ));
    }
}