package org.sopt.sopkathon.domain.dto.response;

import org.sopt.sopkathon.domain.entity.Post;

import java.util.List;
import java.util.Map;

public record PostGetResponse(
        List<PostInfo> posts
) {
    public static PostGetResponse of(List<Post> posts, Map<Long, List<String>> hashtagMap) {
        return new PostGetResponse(
                posts.stream()
                        .map(post -> PostInfo.of(post, hashtagMap.getOrDefault(post.getId(), List.of())))
                        .toList()
        );
    }

    public record PostInfo(
            Long postId,
            String title,
            String content,
            List<String> hashtags
    ) {
        public static PostInfo of(Post post, List<String> hashtags) {
            return new PostInfo(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    hashtags
            );
        }
    }
}
