package com.ecs.ecs_customer.mapper;

import com.ecs.ecs_customer.dto.CustomerDto;
import com.ecs.ecs_customer.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getPassword(),
                customer.getRole()
        );
    }

    public static Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer(
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                customerDto.getPassword(),
                customerDto.getRole()
        );
    }
}
