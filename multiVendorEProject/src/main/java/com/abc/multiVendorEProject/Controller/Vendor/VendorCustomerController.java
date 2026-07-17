package com.abc.multiVendorEProject.Controller.Vendor;

import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerDetailsResponseDTO;
import com.abc.multiVendorEProject.DTOs.projectDtos.Vendor.Customer.VendorCustomerResponseDTO;
import com.abc.multiVendorEProject.service.Vendor.VendorCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor/customers")
@RequiredArgsConstructor
public class VendorCustomerController {

    private final VendorCustomerService vendorCustomerService;


    @GetMapping
    public Page<VendorCustomerResponseDTO> getAllCustomers(

            @PageableDefault(
                    page = 0,
                    size = 10,
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        System.out.println("Vendor Customer Controller Called");


        return vendorCustomerService.getAllCustomers(pageable);
    }


    @GetMapping("/{userName}")
    public VendorCustomerDetailsResponseDTO getCustomerDetails(
            @PathVariable String userName) {

        return vendorCustomerService.getCustomerDetails(userName);
    }

}