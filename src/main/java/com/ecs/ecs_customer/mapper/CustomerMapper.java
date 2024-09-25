package com.ecs.ecs_customer.mapper;

import com.ecs.ecs_customer.dto.CustomerDto;
import com.ecs.ecs_customer.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getPassword()
        );
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer(
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getEmail(),
                customerDto.getPassword()
        );
    }
}
