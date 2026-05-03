package com.techegg.repository;

import com.techegg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link User} entity providing CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Additional query methods can be defined here if needed, e.g.:
    // Optional<User> findByUsername(String username);
    // Optional<User> findByEmail(String email);
}
