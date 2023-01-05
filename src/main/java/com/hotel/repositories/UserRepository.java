package com.hotel.repositories;

import com.hotel.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE username = :query", nativeQuery = true)
    Optional<User> findByUsername(@Param("query") String query);

    Boolean existsByUsername(String username);
}
