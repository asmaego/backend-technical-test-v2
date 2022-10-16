package com.tui.proof.mappers;

import com.tui.proof.dto.address.AddressDTO;
import com.tui.proof.model.Address;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AddressMapper {

    public AddressDTO addressToAddressDto(Address address) {
        if (Objects.isNull(address)) {
            return null;
        }
        return AddressDTO.builder()
                .id(address.getId())
                .city(address.getCity())
                .country(address.getCountry())
                .postcode(address.getPostcode())
                .street(address.getStreet())
                .build();
    }

    public List<AddressDTO> addressListToAddressListDto(List<Address> address) {
        if (Objects.isNull(address)) {
            return null;
        }
        return address.stream().map(this::addressToAddressDto).collect(Collectors.toList());
    }

    public Address addressDtoToAddress(AddressDTO addressDto) {
        if (Objects.isNull(addressDto)) {
            return null;
        }
        return Address.builder()
                .id(addressDto.getId())
                .postcode(addressDto.getPostcode())
                .country(addressDto.getCountry())
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .build();
    }
}
