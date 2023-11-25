package com.inventory.management.repository;

import com.inventory.management.domain.Attachment;
import com.inventory.management.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>, CustomQuerydslPredicateExecutor<Attachment>  {

    @Query("Select a.name from Attachment  a where a.id = :id")
    String findAttachmentById(@Param("id") Long id);
}
