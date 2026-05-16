package org.sopt.sopkathon.domain.repository;

import org.sopt.sopkathon.domain.entity.ViewedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewedPostRepository extends JpaRepository<ViewedPost, Long> {
}