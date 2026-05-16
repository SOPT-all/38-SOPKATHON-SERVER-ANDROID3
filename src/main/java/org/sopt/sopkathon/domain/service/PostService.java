package org.sopt.sopkathon.domain.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.entity.Post;
import org.sopt.sopkathon.domain.dto.request.PostCreateRequest;
import org.sopt.sopkathon.domain.dto.response.PostCreateResponse;
import org.sopt.sopkathon.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request) {
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .userId(1L)
                .build();

        Post savedPost = postRepository.save(post);
        return PostCreateResponse.from(savedPost.getId());
    }
}
