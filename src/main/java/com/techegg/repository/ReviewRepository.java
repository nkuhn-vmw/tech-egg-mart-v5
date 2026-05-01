package com.techegg.repository;

import com.techegg.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Review} entity providing basic CRUD operations.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    /**
     * Find all reviews for a given product.
     *
     * @param productId the id of the product
     * @return list of reviews
     */
    List<Review> findByProductId(Long productId);
}
