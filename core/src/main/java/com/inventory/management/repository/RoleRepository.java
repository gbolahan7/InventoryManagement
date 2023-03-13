package com.inventory.management.repository;


import com.inventory.management.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(String name);

  @Query(value = "SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.privileges",
          countQuery = " SELECT  COUNT(r) from Role r LEFT JOIN r.privileges")
  Page<Role> findAll(Pageable pageable);

  @Query("SELECT r FROM Role r WHERE r.name = 'SUPER_ADMIN'")
  Role findSuperAdminRole();
}
