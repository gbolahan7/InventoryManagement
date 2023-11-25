package com.inventory.management.repository;

import com.inventory.management.domain.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long>, CustomQuerydslPredicateExecutor<ProductRequest> {

}
