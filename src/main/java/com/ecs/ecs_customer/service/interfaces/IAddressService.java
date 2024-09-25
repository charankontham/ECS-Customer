package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.dto.AddressDto;

import java.util.List;

public interface IAddressService {

    AddressDto getAddressById(int addressId);

    List<AddressDto> getAllAddressByCustomerId(int customerId);

    List<AddressDto> getAllAddresses();

    Object addAddress(AddressDto addressDto);

    Object updateAddress(AddressDto addressDto);

    boolean deleteAddressById(int addressId);

    void deleteAllAddressByIds(List<Integer> addressIds);

    void deleteAddressByCustomerId(int customerId);

    boolean isAddressExists(int addressId);
}
