package com.inventory.management.repository;

import com.inventory.management.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT DISTINCT u from User u LEFT JOIN Fetch u.roles r LEFT JOIN Fetch r.privileges p where u.username = :username")
  Optional<User> findByUsername(@Param("username") String username);

  Boolean existsByUsernameOrEmail(String username, String email);

  @Query("SELECT COUNT(*) FROM User ")
  Long countAllUsers();

  @Query("SELECT DISTINCT u from User u LEFT JOIN Fetch u.roles r where r.name = 'SUPER_ADMIN' AND u.status = 'ACTIVE'")
  List<User> findAllUserWithSuperAdminRole();

  @Query("SELECT DISTINCT u from User u LEFT JOIN Fetch u.roles r where u.id = :id")
  Optional<User> findDeepById(@Param("id") Long id);
}
