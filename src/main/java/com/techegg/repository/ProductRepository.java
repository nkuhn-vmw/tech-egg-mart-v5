package com.techegg.repository;

import com.techegg.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Product} entity providing basic CRUD operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Find all products belonging to a specific category.
     *
     * @param categoryId the id of the category
     * @return list of products
     */
    List<Product> findByCategoryId(Long categoryId);
}
