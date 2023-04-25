package com.inventory.management.repository;

import com.inventory.management.domain.PurchaseOrder;
import com.inventory.management.domain.PurchaseOrderItem;
import com.inventory.management.vo.dto.performance.PerformanceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, CustomQuerydslPredicateExecutor<PurchaseOrder>  {

    @Query("Select new com.inventory.management.vo.dto.performance.PerformanceOrder(count(poi.id) , cast(sum(poi.amount * poi.quantity) as java.lang.Double ), po.modifiedBy) from PurchaseOrder po" +
            " LEFT JOIN po.items poi  group by po.modifiedBy ")
    List<PerformanceOrder> findAllPerformanceOrder();

    @Query("Select new com.inventory.management.vo.dto.performance.PerformanceOrder(count(poi.id) , cast(sum(poi.amount * poi.quantity) as java.lang.Double ), po.modifiedBy) from PurchaseOrder po" +
            " LEFT JOIN po.items poi where po.modifiedDate > :date group by po.modifiedBy ")
    List<PerformanceOrder> findAllPerformanceOrder(@Param("date") Instant date);

}
