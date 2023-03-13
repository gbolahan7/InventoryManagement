package com.inventory.management.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CustomQuerydslPredicateExecutor<T> extends QuerydslPredicateExecutor<T> {
    Page<T> findAll(Predicate predicate, Pageable pageable);
}
