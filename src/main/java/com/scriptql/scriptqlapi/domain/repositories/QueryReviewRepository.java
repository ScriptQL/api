package com.scriptql.scriptqlapi.domain.repositories;

import com.scriptql.scriptqlapi.domain.entities.QueryReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryReviewRepository extends JpaRepository<QueryReview, Long> {
}
