package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.dto.AddressDto;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface IAddressService {
    AddressDto getAddressById(Integer addressId);

    List<AddressDto> getAllAddressByUserId(String userId);

    List<AddressDto> getAllAddresses();

    Object addAddress(AddressDto addressDto);

    Object updateAddress(AddressDto addressDto);

    boolean deleteAddressById(Integer addressId) throws DataIntegrityViolationException;

    Boolean deleteAddressByUserId(String userId) throws DataIntegrityViolationException;
}
