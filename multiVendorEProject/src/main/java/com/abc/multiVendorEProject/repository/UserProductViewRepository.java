package com.abc.multiVendorEProject.repository;


import com.abc.multiVendorEProject.entity.Product;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.entity.UserProductView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductViewRepository
        extends JpaRepository<UserProductView, Long> {

    List<UserProductView> findByUserOrderByViewedAtDesc(User user);

    List<UserProductView> findByProduct(Product product);

    long countByProduct(Product product);
}
