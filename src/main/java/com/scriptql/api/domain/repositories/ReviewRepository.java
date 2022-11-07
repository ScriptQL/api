package com.scriptql.api.domain.repositories;

import com.scriptql.api.domain.entities.Query;
import com.scriptql.api.domain.entities.Review;
import com.scriptql.api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    List<Review> findAllByQuery(Query query);

    Optional<Review> findByUserAndQuery(User user, Query query);

    long countAllByQueryAndAccepted(Query query, Boolean accepted);

}
