package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.dto.CustomerDto;

import java.util.List;

public interface ICustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(int customerId);

    CustomerDto getCustomerByEmail(String email);

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomerById(int customerId);

    Object customerLogin(String email, String password);

    boolean updatingDuplicateEmail(CustomerDto customerDto);

    boolean isEmailExist(String email);

    boolean isCustomerExist(int customerId);
}
