package com.ecs.ecs_customer.mapper;

import com.ecs.ecs_customer.dto.AddressDto;
import com.ecs.ecs_customer.entity.Address;

public class AddressMapper {

    public static Address mapToAddress(AddressDto addressDto) {
        return new Address(
                addressDto.getAddressId(),
                addressDto.getUserId(),
                addressDto.getName(),
                addressDto.getContact(),
                addressDto.getStreet(),
                addressDto.getCity(),
                addressDto.getState(),
                addressDto.getZip(),
                addressDto.getCountry()
        );
    }

    public static AddressDto mapToAddressDto(Address address) {
        return new AddressDto(
                address.getAddressId(),
                address.getUserId(),
                address.getName(),
                address.getContact(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip(),
                address.getCountry()
        );
    }
}
