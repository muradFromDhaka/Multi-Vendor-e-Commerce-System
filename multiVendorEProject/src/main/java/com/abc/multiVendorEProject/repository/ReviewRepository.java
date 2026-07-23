package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.Review;
import com.abc.multiVendorEProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    long count();

    List<Review> findByProduct(Product product);

    List<Review> findByUser(User user);

    Optional<Review> findByUserAndProduct(User user, Product product);

    Long countByProduct(Product product);

    @Query("""
       SELECT AVG(r.rating)
       FROM Review r
       WHERE r.product = :product
       """)
    Double getAverageRatingByProduct( @Param("product") Product product);


    @Query("""
SELECT COALESCE(AVG(r.rating),0)
FROM Review r
""")
    BigDecimal getAverageRating();


}
