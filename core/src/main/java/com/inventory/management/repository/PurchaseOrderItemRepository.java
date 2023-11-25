package com.inventory.management.repository;

import com.inventory.management.domain.PurchaseOrderItem;
import com.inventory.management.domain.PurchaseOrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long>, CustomQuerydslPredicateExecutor<PurchaseOrderItem> {

    @Query("Select poi from PurchaseOrderItem poi JOIN fetch poi.purchaseOrder  where poi.purchaseOrder is not null")
    List<PurchaseOrderItem> findAllItems();

    @Query("select coalesce( Sum(poi.amount * poi.quantity), 0.0) as val from PurchaseOrderItem poi where poi.productName = :name")
    double findAllItemSumForProduct(@Param("name") String productName);

    @Query("select poi from PurchaseOrderItem poi JOIN fetch poi.purchaseOrder po " +
            "where poi.purchaseOrder is not null and poi.productName = :name order by po.createdDate, poi.id")
    List<PurchaseOrderItem> findAllProductItem(@Param("name") String productName);
}
