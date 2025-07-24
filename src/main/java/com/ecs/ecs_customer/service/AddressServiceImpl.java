package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.AddressDto;
import com.ecs.ecs_customer.dto.AdminDto;
import com.ecs.ecs_customer.dto.DeliveryAgentDto;
import com.ecs.ecs_customer.dto.DeliveryHubEnrichedDto;
import com.ecs.ecs_customer.entity.Address;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.feign.InventoryService;
import com.ecs.ecs_customer.feign.LogisticsService;
import com.ecs.ecs_customer.mapper.AddressMapper;
import com.ecs.ecs_customer.repository.AddressRepository;
import com.ecs.ecs_customer.repository.CustomerRepository;
import com.ecs.ecs_customer.service.interfaces.IAddressService;
import com.ecs.ecs_customer.validations.AddressValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public AddressDto getAddressById(Integer addressId) {
        Address address = addressRepository.findById(addressId).
                orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
        return AddressMapper.mapToAddressDto(address);
    }

    @Override
    public List<AddressDto> getAllAddressByUserId(String userId) {
        List<Address> addresses = addressRepository.findAllByUserId(userId);
        return addresses.stream().map(AddressMapper::mapToAddressDto).collect(Collectors.toList());
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(AddressMapper::mapToAddressDto).collect(Collectors.toList());
    }

    @Override
    public Object addAddress(AddressDto addressDto) {
//        boolean customerExists = customerRepository.existsById(Integer.parseInt(userIdArray[1]));
//        if (!customerExists) {
//            return HttpStatus.NOT_FOUND;
//        }
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
//        boolean customerExists = customerRepository.existsById(addressDto.getCustomerId());
//        if (!customerExists) {
//            return HttpStatus.NOT_ACCEPTABLE;
//        }
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
    public boolean deleteAddressById(Integer addressId) throws DataIntegrityViolationException {
        if (addressId != 0 && addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteAddressByUserId(String userId) throws DataIntegrityViolationException {
        if(userId.isEmpty() ||
                AddressValidation.isIntegerRegex(userId) ||
                !userId.contains("_") ||
                userId.split("_").length != 2 ||
                AddressValidation.isIntegerRegex(userId.split("_")[0]))
        {
            return false;
        }
        if (userExistsById(userId)) {
            addressRepository.deleteAddressByUserId(userId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void deleteAllAddressByIds(List<Integer> addressIds) throws SQLIntegrityConstraintViolationException {
        for (Integer addressId : addressIds) {
            if (addressId != 0 && addressRepository.existsById(addressId)) {
                addressRepository.deleteById(addressId);
            }
        }
    }

    private boolean userExistsById(String userId){
        String[] userIdArray = userId.split("_");
        Integer convertedUserId = Integer.parseInt(userIdArray[1]);
        return switch (userIdArray[0]) {
            case "customer" -> customerRepository.existsById(convertedUserId);
            case "deliveryAgent" -> {
                ResponseEntity<DeliveryAgentDto> deliveryAgentResponse = logisticsService.getDeliveryAgentById(convertedUserId);
                yield deliveryAgentResponse.getStatusCode() == HttpStatus.OK && deliveryAgentResponse.getBody() != null &&
                        Objects.equals(deliveryAgentResponse.getBody().getDeliveryAgentId(), convertedUserId);
            }
            case "deliveryHub" -> {
                ResponseEntity<DeliveryHubEnrichedDto> deliveryHubResponse = logisticsService.getDeliveryHubById(convertedUserId);
                yield deliveryHubResponse.getStatusCode() == HttpStatus.OK && deliveryHubResponse.getBody() != null &&
                        Objects.equals(deliveryHubResponse.getBody().getDeliveryHubId(), convertedUserId);
            }
            case "admin" -> {
                ResponseEntity<AdminDto> adminResponse = inventoryService.getAdminById(userIdArray[1]);
                yield adminResponse.getStatusCode() == HttpStatus.OK && adminResponse.getBody() != null &&
                        Objects.equals(adminResponse.getBody().getId(), userIdArray[1]);
            }
            default -> false;
        };
    }
}
