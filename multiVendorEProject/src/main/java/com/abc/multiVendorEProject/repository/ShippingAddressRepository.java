package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {

    Optional<ShippingAddress> findByIdAndUser_UserName(
            Long id,
            String userName
    );
}
