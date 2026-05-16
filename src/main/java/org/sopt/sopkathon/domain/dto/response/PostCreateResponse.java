package org.sopt.sopkathon.domain.dto.response;

public record PostCreateResponse(
        Long postId
) {
    public static PostCreateResponse from(Long postId) {
        return new PostCreateResponse(postId);
    }
}
