package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.AddressDto;
import com.ecs.ecs_customer.entity.Address;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.mapper.AddressMapper;
import com.ecs.ecs_customer.repository.AddressRepository;
import com.ecs.ecs_customer.repository.CustomerRepository;
import com.ecs.ecs_customer.service.interfaces.IAddressService;
import com.ecs.ecs_customer.validations.AddressValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public AddressDto getAddressById(Integer addressId) {
        Address address = addressRepository.findById(addressId).
                orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
        return AddressMapper.mapToAddressDto(address);
    }

    @Override
    public List<AddressDto> getAllAddressByCustomerId(Integer customerId) {
        List<Address> addresses = addressRepository.findAllByCustomerId(customerId);
        return addresses.stream().map(AddressMapper::mapToAddressDto).collect(Collectors.toList());
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(AddressMapper::mapToAddressDto).collect(Collectors.toList());
    }

    @Override
    public Object addAddress(AddressDto addressDto) {
        boolean customerExists = customerRepository.existsById(addressDto.getCustomerId());
        if (!customerExists) {
            return HttpStatus.NOT_FOUND;
        }
        if (addressDto.getAddressId() != null) {
            boolean addressExists = addressRepository.existsById(addressDto.getAddressId());
            if (addressExists) {
                return HttpStatus.CONFLICT;
            }
        }
        if (AddressValidation.validateAddress(addressDto)) {
            Address address = addressRepository.save(AddressMapper.mapToAddress(addressDto));
            return AddressMapper.mapToAddressDto(address);
        }
        return null;
    }

    @Override
    public Object updateAddress(AddressDto addressDto) {
        boolean customerExists = customerRepository.existsById(addressDto.getCustomerId());
        if (!customerExists) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
        boolean addressExists = addressRepository.existsById(addressDto.getAddressId());
        if (!addressExists) {
            return HttpStatus.NOT_FOUND;
        }
        if (AddressValidation.validateAddress(addressDto)) {
            Address address = addressRepository.save(AddressMapper.mapToAddress(addressDto));
            return AddressMapper.mapToAddressDto(address);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteAddressById(Integer addressId) {
        if (addressId != 0 && addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteAddressByCustomerId(Integer customerId) {
        if (customerRepository.existsById(customerId)) {
            addressRepository.deleteAddressByCustomerId(customerId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void deleteAllAddressByIds(List<Integer> addressIds) {
        for (Integer addressId : addressIds) {
            if (addressId != 0 && addressRepository.existsById(addressId)) {
                addressRepository.deleteById(addressId);
            }
        }
    }
}
