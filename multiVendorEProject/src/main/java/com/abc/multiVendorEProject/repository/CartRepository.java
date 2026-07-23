package com.abc.multiVendorEProject.repository;


import com.abc.multiVendorEProject.entity.Cart;
import com.abc.multiVendorEProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

    long count();

    Optional<Cart> findByUser(User currentUser);

    @Query("""
SELECT COUNT(c)
FROM Cart c
WHERE SIZE(c.items) > 0
""")
    Long countActiveCarts();



}
