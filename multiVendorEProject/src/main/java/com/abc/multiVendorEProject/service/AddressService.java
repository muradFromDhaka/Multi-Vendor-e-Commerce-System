package com.abc.multiVendorEProject.service;

import com.abc.multiVendorEProject.DTOs.projectDtos.AddressRequestDto;
import com.abc.multiVendorEProject.DTOs.projectDtos.AddressResponseDto;
import com.abc.multiVendorEProject.entity.Address;
import com.abc.multiVendorEProject.entity.User;
import com.abc.multiVendorEProject.mapper.AddressMapper;
import com.abc.multiVendorEProject.repository.AddressRepository;
import com.abc.multiVendorEProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Page<AddressResponseDto> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<AddressResponseDto> dto = addressRepository.findAll(pageable)
                .map(address -> addressMapper.toDto(address, new AddressResponseDto()));


        return dto;
    }

    public AddressResponseDto getById(Long id){
        Address entity = addressRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Address not found."));

        return addressMapper.toDto(entity, new AddressResponseDto());
    }

    public AddressResponseDto create(AddressRequestDto dto){
        Address entity = addressMapper.toEntity(dto, new Address());
        User user = userRepository.findByUserNameAndDeletedFalse(dto.getUserName())
                .orElseThrow(()-> new RuntimeException("User not found."));
        entity.setUser(user);
        Address saved = addressRepository.save(entity);
        return addressMapper.toDto(saved, new AddressResponseDto());
    }

    public AddressResponseDto update(Long id, AddressRequestDto dto){
        Address existing = addressRepository.findById(id)
                .map(address -> addressMapper.toEntity(dto, address))
                .orElseThrow(()-> new RuntimeException("Address not found."));
        if(dto.getUserName() != null){
            User existingUser = userRepository.findByUserNameAndDeletedFalse(dto.getUserName())
                    .orElseThrow(()-> new RuntimeException("User not found"));
            existing.setUser(existingUser);
        }

        Address updateAddress = addressRepository.save(existing);

        return addressMapper.toDto(updateAddress,new AddressResponseDto());
    }

    public void delete(Long id){
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }
}
