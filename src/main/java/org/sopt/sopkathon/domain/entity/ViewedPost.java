package org.sopt.sopkathon.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "viewed_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewedPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Builder
    public ViewedPost(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}