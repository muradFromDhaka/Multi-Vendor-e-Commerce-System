package com.abc.multiVendorEProject.repository;

import com.abc.multiVendorEProject.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(
            value = """
                    select * from address where lower(city) like lower(concat('%', :keyword, '%'))
                    """,
            nativeQuery = true
    )
    List<Address> searchByCity(@Param("keyword") String keyword);

}
