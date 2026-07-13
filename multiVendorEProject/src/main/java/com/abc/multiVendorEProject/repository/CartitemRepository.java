package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartitemRepository extends JpaRepository<CartItem, Long>{

}
