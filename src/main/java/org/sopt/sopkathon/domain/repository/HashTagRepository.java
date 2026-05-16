package org.sopt.sopkathon.domain.repository;

import org.sopt.sopkathon.domain.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
}