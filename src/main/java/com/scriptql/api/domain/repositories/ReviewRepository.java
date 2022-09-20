package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.Query;
import com.scriptql.api.domain.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    List<Review> findAllByQuery(Query query);

}
