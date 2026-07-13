package com.abc.multiVendorEProject.repository;


import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long>{

    long count();

    Optional<Wishlist> findByUser(User user);

    boolean existsByUserAndProductsContaining(User user, Product product);

    boolean existsByUser(User user);

    void deleteByUser(User user);

    long countByUser(User user);

    @Query("""
       SELECT COUNT(p)
       FROM Wishlist w
       JOIN w.products p
       WHERE w.user = :user
       """)
    long countProductsInWishlist( @Param("user") User user );

}
