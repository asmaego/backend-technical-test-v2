package com.tui.proof.service;

import com.tui.proof.dto.address.AddressDTO;
import com.tui.proof.mappers.AddressMapper;
import com.tui.proof.model.Address;
import com.tui.proof.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressService {

    private AddressRepository addressRepository;
    private AddressMapper addressMapper;

    @Autowired
    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public List<AddressDTO> getAddresses() {
        return addressMapper.addressListToAddressListDto(addressRepository.findAll());
    }

    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

}
