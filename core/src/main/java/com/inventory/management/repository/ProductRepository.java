package com.inventory.management.repository;

import com.inventory.management.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomQuerydslPredicateExecutor<Product>  {

    Optional<Product> findByName(String name);
    Optional<Product> findByCode(String code);
    @Query("Select p From Product p where p.status = 'Active' AND p.quantity > 0")
    List<Product> findAllActiveStatus();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Product p  set p.quantity = :quantity where p.code = :code")
    void updateProductAmount(@Param("code") String code, @Param("quantity") Long quantity);

    @Query("Select p.name From Product p order by p.name")
    List<String> getAllProductName();

}
