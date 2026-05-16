package org.sopt.sopkathon.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MyPostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private List<String> reactions;

    @Builder
    public MyPostResponseDto(Long postId, String title, String content, int reactionCount, List<String> reactions) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.reactions = reactions;
    }
}