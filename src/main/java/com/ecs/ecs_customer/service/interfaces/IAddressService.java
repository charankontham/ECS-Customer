package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.dto.AddressDto;

import java.util.List;

public interface IAddressService {
    AddressDto getAddressById(Integer addressId);

    List<AddressDto> getAllAddressByCustomerId(Integer customerId);

    List<AddressDto> getAllAddresses();

    Object addAddress(AddressDto addressDto);

    Object updateAddress(AddressDto addressDto);

    boolean deleteAddressById(Integer addressId);

    Boolean deleteAddressByCustomerId(Integer customerId);
}
