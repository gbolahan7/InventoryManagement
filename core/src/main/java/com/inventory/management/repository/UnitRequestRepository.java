package com.inventory.management.repository;

import com.inventory.management.domain.UnitRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRequestRepository extends JpaRepository<UnitRequest, Long>, CustomQuerydslPredicateExecutor<UnitRequest> {

}
