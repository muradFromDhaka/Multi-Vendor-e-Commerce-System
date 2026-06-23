package com.abc.SpringSecurityExample.repository;

import com.abc.SpringSecurityExample.entity.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long>{

    @EntityGraph(attributePaths = "product")
    Page<Deal> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "product")
    @Query("""
    SELECT d FROM Deal d
    WHERE d.startTime <= CURRENT_TIMESTAMP
    AND d.endTime >= CURRENT_TIMESTAMP
    """)
    Page<Deal> findActiveDeals(Pageable pageable);

}
