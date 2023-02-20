package com.inventory.management.repository;

import com.inventory.management.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from User u JOIN Fetch u.roles r JOIN Fetch r.privileges p where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    Boolean existsByUsernameOrEmail(String username, String email);

    @Query("SELECT (id) FROM User ")
    Long countAllUsers();
}