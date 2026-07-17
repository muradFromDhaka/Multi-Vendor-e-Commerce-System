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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

        User currentUser = getCurrentUser();

        Address entity = addressMapper.toEntity(dto, new Address());

        entity.setUser(currentUser);

        Address saved = addressRepository.save(entity);

        return addressMapper.toDto(saved, new AddressResponseDto());
    }

    public AddressResponseDto update(Long id, AddressRequestDto dto) {

        User currentUser = getCurrentUser();

        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!existing.getUser().getUserName()
                .equals(currentUser.getUserName())) {

            throw new RuntimeException("You are not allowed to update this address.");
        }

        addressMapper.toEntity(dto, existing);

        Address updated = addressRepository.save(existing);

        return addressMapper.toDto(updated, new AddressResponseDto());
    }


    public void delete(Long id) {

        User currentUser = getCurrentUser();

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        if (!address.getUser().getUserName()
                .equals(currentUser.getUserName())) {

            throw new RuntimeException("You are not allowed to delete this address.");
        }

        addressRepository.delete(address);
    }

    public List<AddressResponseDto> getMyAddresses() {

        User currentUser = getCurrentUser();

        return addressRepository.findByUser(currentUser)
                .stream()
                .map(address -> addressMapper.toDto(address, new AddressResponseDto()))
                .toList();
    }


    private User getCurrentUser() {
        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findById(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
