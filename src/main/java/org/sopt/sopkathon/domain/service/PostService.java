package org.sopt.sopkathon.domain.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.entity.HashTag;
import org.sopt.sopkathon.domain.entity.Post;
import org.sopt.sopkathon.domain.dto.request.PostCreateRequest;
import org.sopt.sopkathon.domain.dto.response.PostCreateResponse;
import org.sopt.sopkathon.domain.dto.response.PostGetResponse;
import org.sopt.sopkathon.domain.repository.HashTagRepository;
import org.sopt.sopkathon.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

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

    @Transactional(readOnly = true)
    public PostGetResponse getPosts() {
        List<Post> posts = postRepository.findAllByUserIdNot(1L);

        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .toList();

        Map<Long, List<String>> hashtagMap = hashTagRepository.findAllByPostIdIn(postIds)
                .stream()
                .collect(Collectors.groupingBy(
                        HashTag::getPostId,
                        Collectors.mapping(HashTag::getContent, Collectors.toList())
                ));

        return PostGetResponse.of(posts, hashtagMap);
    }
}
