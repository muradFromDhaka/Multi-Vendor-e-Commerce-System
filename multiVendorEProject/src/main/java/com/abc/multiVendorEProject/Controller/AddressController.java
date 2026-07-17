package com.abc.multiVendorEProject.Controller;

import com.abc.multiVendorEProject.DTOs.projectDtos.AddressRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.AddressResponseDto;
import com.abc.multiVendorEProject.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<Page<AddressResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<AddressResponseDto> responseDto = addressService.getAll(page, size);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getById(@PathVariable Long id){
        AddressResponseDto responseDto = addressService.getById(id);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/my")
    public ResponseEntity<List<AddressResponseDto>> getMyAddresses() {

        return ResponseEntity.ok(addressService.getMyAddresses());
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> create(@RequestBody AddressRequestDto dto){
        AddressResponseDto responseDto = addressService.create(dto);

        return ResponseEntity.status(201).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> update(
            @PathVariable Long id,
            @RequestBody AddressRequestDto dto){

        AddressResponseDto responseDto = addressService.update(id,dto);

       return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        addressService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
