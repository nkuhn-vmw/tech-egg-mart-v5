package com.techegg.repository;

import com.techegg.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Product} entities.
 * Provides standard CRUD operations and query methods inherited from {@link JpaRepository}.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional query methods can be defined here if needed.
}
