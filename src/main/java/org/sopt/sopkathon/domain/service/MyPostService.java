package org.sopt.sopkathon.domain.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.dto.response.MyPostListResponseDto;
import org.sopt.sopkathon.domain.dto.response.MyPostResponseDto;
import org.sopt.sopkathon.domain.entity.Post;
import org.sopt.sopkathon.domain.entity.Reaction;
import org.sopt.sopkathon.domain.repository.PostRepository;
import org.sopt.sopkathon.domain.repository.ReactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPostService {

    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public MyPostListResponseDto getMyPostsAndReactions() {
        List<Post> myPosts = postRepository.findByUserIdAndIsDeletedFalse(1L);
        List<MyPostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : myPosts) {
            List<Reaction> reactions = reactionRepository.findByPostId(post.getId());

            List<String> reactionContents = reactions.stream()
                    .map(Reaction::getContent)
                    .collect(Collectors.toList());

            MyPostResponseDto postDto = MyPostResponseDto.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .reactions(reactionContents)
                    .build();

            postResponseDtos.add(postDto);
        }

        return new MyPostListResponseDto(postResponseDtos);
    }
}