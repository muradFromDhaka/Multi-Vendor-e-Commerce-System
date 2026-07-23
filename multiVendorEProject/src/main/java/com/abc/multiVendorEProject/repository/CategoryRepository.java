package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    long count();

    Page<Category> findByParentIsNull(Pageable pageable);

    @EntityGraph(attributePaths = {"parent"})
    Page<Category> findByParentId(Long parentId, Pageable pageable);

    @EntityGraph(attributePaths = {"parent"})
    Page<Category> findAll(Pageable pageable);

    @Query(value = """ 
            Select * from categories where lower(name) like lower(concat('%', :category ,'%') )
            """,
            nativeQuery = true)
    List<Category> searchByName(@Param("category") String name);

    @Query("""
SELECT c
FROM OrderItem oi
JOIN oi.variant v
JOIN v.product p
JOIN p.category c
GROUP BY c
ORDER BY SUM(oi.quantity) DESC
""")
    Page<Category> findBestSellingCategory(Pageable pageable);


    @Query("""
SELECT COALESCE(SUM(oi.quantity),0)
FROM OrderItem oi
WHERE oi.variant.product.category.id = :categoryId
""")
    Long getCategoryQuantitySold(
            @Param("categoryId") Long categoryId);
}
