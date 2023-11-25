package com.inventory.management.repository;

import com.inventory.management.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CustomQuerydslPredicateExecutor<Category>  {

    Optional<Category> findByName(String name);

}
