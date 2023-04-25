package com.inventory.management.repository;

import com.inventory.management.domain.PerformanceSetting;
import com.inventory.management.domain.StaffPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffPerformanceRepository extends JpaRepository<StaffPerformance, Long>, CustomQuerydslPredicateExecutor<StaffPerformance>  {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update StaffPerformance sp  set sp.isNew = :isNew ")
    void updateNewStatus(@Param("isNew") boolean isNew);

    @Query("SELECT sp.modifiedDate FROM StaffPerformance sp order by sp.modifiedDate DESC")
    List<Instant> getLastTime();
}
