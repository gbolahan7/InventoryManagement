package com.inventory.management.repository;

import com.inventory.management.domain.PerformanceSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerformanceSettingRepository extends JpaRepository<PerformanceSetting, Long>, CustomQuerydslPredicateExecutor<PerformanceSetting>  {

    Optional<PerformanceSetting> findByName(String name);

}
