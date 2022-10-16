package com.tui.proof.service;

import com.tui.proof.dto.address.AddressDTO;
import com.tui.proof.mappers.AddressMapper;
import com.tui.proof.model.Address;
import com.tui.proof.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AddressServiceTest {

    AddressService addressService;

    @Mock
    AddressRepository addressRepository;
    @Autowired
    AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressService = new AddressService(addressRepository, addressMapper);
    }

    @Test
    void getAddresses() {
        when(addressRepository.findAll()).thenReturn(List.of(new Address(1L, "street", "postcode", "city", "country", null)));
        List<AddressDTO> addresses = addressService.getAddresses();

        assertEquals(1, addresses.size());

        assertEquals("city", addresses.get(0).getCity());
        assertEquals("street", addresses.get(0).getStreet());
        assertEquals(1L, addresses.get(0).getId());
        assertEquals("country", addresses.get(0).getCountry());
    }

    @Test
    void getAddresses_empty() {
        when(addressRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0, addressService.getAddresses().size());
    }

    @Test
    void getAddress() {
        when(addressRepository.findById(any())).thenReturn(Optional.of(new Address(1L, "street", "postcode", "city", "country", null)));
        Address address = addressService.getAddress(16541L);

        assertNotNull(address);

        assertEquals("city", address.getCity());
        assertEquals("street", address.getStreet());
        assertEquals(1L, address.getId());
        assertEquals("country", address.getCountry());
    }

    @Test
    void getAddress_null() {
        when(addressRepository.findById(any())).thenReturn(Optional.empty());
        assertNull(addressService.getAddress(11L));
    }
}