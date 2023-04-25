package com.inventory.management.repository;

import com.inventory.management.domain.PerformanceSettingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSettingRequestRepository extends JpaRepository<PerformanceSettingRequest, Long>, CustomQuerydslPredicateExecutor<PerformanceSettingRequest> {

}
