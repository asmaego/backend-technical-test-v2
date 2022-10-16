package com.tui.proof.mappers;

import com.tui.proof.dto.address.AddressDTO;
import com.tui.proof.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class AddressMapperTest {

    AddressMapper addressMapper = new AddressMapper();

    @Test
    void addressToAddressDto_when_not_null() {
        Address address = new Address(1L, "street", "postcode", "city", "country", null);

        AddressDTO dto = addressMapper.addressToAddressDto(address);

        assertEquals("city", dto.getCity());
        assertEquals("street", dto.getStreet());
        assertEquals(1L, dto.getId());
        assertEquals("country", dto.getCountry());
    }

    @Test
    void addressToAddressDto_when_null() {
        AddressDTO dto = addressMapper.addressToAddressDto(null);
        assertNull(dto);
    }

    @Test
    void addressListToAddressListDto_when_not_null() {
        List<Address> addressList= new ArrayList<>();
        addressList.add(new Address(1L, "street", "postcode", "city", "country", null));

        List<AddressDTO> addressDtoList=addressMapper.addressListToAddressListDto(addressList);

        assertEquals(1,addressDtoList.size());
        assertEquals("city", addressDtoList.get(0).getCity());
        assertEquals("street", addressDtoList.get(0).getStreet());
        assertEquals(1L, addressDtoList.get(0).getId());
        assertEquals("country", addressDtoList.get(0).getCountry());

    }

    @Test
    void addressListToAddressListDto_when_null() {
        List<AddressDTO> addressDtoList=addressMapper.addressListToAddressListDto(null);
        assertNull(addressDtoList);
    }

    @Test
    void addressDtoToAddress_when_not_null() {
        AddressDTO dto = new AddressDTO(1L,"street","postcode","city","country");

        Address address= addressMapper.addressDtoToAddress(dto);

        assertEquals("city", address.getCity());
        assertEquals("street", address.getStreet());
        assertEquals(1L, address.getId());
        assertEquals("country", address.getCountry());
    }

    @Test
    void addressDtoToAddress_when_null() {
        Address address= addressMapper.addressDtoToAddress(null);
        assertNull(address);
    }
}