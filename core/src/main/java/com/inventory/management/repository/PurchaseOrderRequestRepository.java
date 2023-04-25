package com.inventory.management.repository;

import com.inventory.management.domain.PurchaseOrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRequestRepository extends JpaRepository<PurchaseOrderRequest, Long>, CustomQuerydslPredicateExecutor<PurchaseOrderRequest> {

}
