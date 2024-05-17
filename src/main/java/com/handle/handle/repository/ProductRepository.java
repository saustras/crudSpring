package com.handle.handle.repository;

import com.handle.handle.domain.entity.Category;
import com.handle.handle.domain.entity.Products;
import com.handle.handle.domain.enums.ProductDeleted;
import com.handle.handle.domain.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findByDeletedStatus(ProductDeleted deletedStatus);

    List<Products> findByDeletedStatusAndStatus(ProductDeleted deletedStatus, ProductStatus status);

    Optional<Products> findByDeletedStatusAndId(ProductDeleted deletedStatus, @Param("id") Long id);

    List<Products> findByCategoryAndDeletedStatus(Category category, ProductDeleted deletedStatus);
}