package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.CustomerDto;
import com.ecs.ecs_customer.entity.Customer;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.mapper.CustomerMapper;
import com.ecs.ecs_customer.repository.CustomerRepository;
import com.ecs.ecs_customer.service.interfaces.ICustomerService;
import com.ecs.ecs_customer.validations.CustomerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RemoveDependencies removeDependencies;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    @Override
    public String createCustomer(CustomerDto customerDto) {
        if (CustomerValidation.validateCustomer(customerDto) &&
                customerRepository.findByEmail(customerDto.getEmail()).isEmpty()
        ) {
            customerDto.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
            Customer customerEntity = CustomerMapper.mapToCustomer(customerDto);
            Customer savedCustomer = customerRepository.save(customerEntity);
            return jwtService.generateToken(savedCustomer.getEmail());
        }
        return null;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map((CustomerMapper::mapToCustomerDto))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Integer customerId) {
        Customer retrievedCustomer = customerRepository.findById(customerId).
                orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));
        return CustomerMapper.mapToCustomerDto(retrievedCustomer);
    }

    @Override
    public CustomerDto getCustomerByEmail(String email) {
        System.out.println(email);
        System.out.println(customerRepository.existsByEmail(email));
        Customer retrievedCustomer = customerRepository.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));
        System.out.println(retrievedCustomer.getCustomerName());
        return CustomerMapper.mapToCustomerDto(retrievedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        if (CustomerValidation.validateCustomer(customerDto)) {
            Customer customer = customerRepository.findById(customerDto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));
            if (!customer.getPassword().equals(customerDto.getPassword())) {
                customerDto.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
            }
            Customer updatedCustomer = customerRepository.save(CustomerMapper.mapToCustomer(customerDto));
            return CustomerMapper.mapToCustomerDto(updatedCustomer);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteCustomerById(Integer customerId) {
        if (customerRepository.existsById(customerId)) {
            try {
                if (removeDependencies.deleteCustomerDependencies(customerId)) {
                    customerRepository.deleteById(customerId);
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }
        } else {
            return false;
        }
    }

    @Override
    public Object customerLogin(CustomerDto customerDto) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(customerDto.getEmail(), customerDto.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(customerDto.getEmail());
            }
            return HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return HttpStatus.UNAUTHORIZED;
        }
    }

    @Override
    public boolean updatingDuplicateEmail(CustomerDto customerDto) {
        Customer customerEntity = customerRepository.findById(customerDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));
        if (customerDto.getEmail().equals(customerEntity.getEmail())) {
            return false;
        } else {
            return isEmailExist(customerDto.getEmail());
        }
    }

    @Override
    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }
}
