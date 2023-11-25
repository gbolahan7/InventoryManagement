package com.inventory.management.repository;

import com.inventory.management.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>, CustomQuerydslPredicateExecutor<Visitor> {

    @Query("select count(v.id) from Visitor v group by v.user")
    List<Long> getUniqueVisitors();
}
