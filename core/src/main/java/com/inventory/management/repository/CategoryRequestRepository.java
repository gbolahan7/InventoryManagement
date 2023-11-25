package com.inventory.management.repository;

import com.inventory.management.domain.Category;
import com.inventory.management.domain.CategoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRequestRepository extends JpaRepository<CategoryRequest, Long>, CustomQuerydslPredicateExecutor<CategoryRequest> {

}
