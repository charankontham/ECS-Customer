package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.dto.CustomerDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICustomerService {

    String createCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Integer customerId);

    CustomerDto getCustomerByEmail(String email);

    CustomerDto updateCustomer(CustomerDto customerDto);

    @Transactional
    Boolean deleteCustomerById(Integer customerId);

    Object customerLogin(CustomerDto customerDto);

    boolean updatingDuplicateEmail(CustomerDto customerDto);

    boolean isEmailExist(String email);
}
