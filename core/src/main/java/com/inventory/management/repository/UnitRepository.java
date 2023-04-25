package com.inventory.management.repository;

import com.inventory.management.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, CustomQuerydslPredicateExecutor<Unit>  {

    Optional<Unit> findByName(String name);

}
