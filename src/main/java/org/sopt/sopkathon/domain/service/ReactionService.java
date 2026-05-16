package org.sopt.sopkathon.domain.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sopkathon.domain.dto.request.ReactionRequestDto;
import org.sopt.sopkathon.global.exception.BusinessException;
import org.sopt.sopkathon.global.exception.CommonErrorCode;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.sopkathon.domain.entity.Post;
import org.sopt.sopkathon.domain.entity.Reaction;
import org.sopt.sopkathon.domain.repository.PostRepository;
import org.sopt.sopkathon.domain.repository.ReactionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReactionService {

    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    @Transactional
    public void createReaction(Long postId, ReactionRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(CommonErrorCode.NOT_FOUND));

        if (post.isDeleted()) {
            throw new BusinessException(CommonErrorCode.NOT_FOUND);
        }

        Reaction reaction = Reaction.builder()
                .postId(post.getId())
                .userId(1L)
                .content(requestDto.getContent())
                .build();

        reactionRepository.save(reaction);
    }
}