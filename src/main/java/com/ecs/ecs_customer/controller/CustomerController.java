package com.ecs.ecs_customer.controller;

import com.ecs.ecs_customer.dto.CustomerDto;
import com.ecs.ecs_customer.service.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDtoList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Integer customerId) {
        CustomerDto customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable("email") String email) {
        System.out.println("entered controller");
        CustomerDto customerDto = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDto customerDto) {
        if (customerService.isEmailExist(customerDto.getEmail())) {
            return new ResponseEntity<>("Duplicate email!", HttpStatus.CONFLICT);
        }
        String newCustomer = customerService.createCustomer(customerDto);
        if (Objects.nonNull(newCustomer)) {
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Validation failed!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping()
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDto) {
        if (customerService.updatingDuplicateEmail(customerDto)) {
            return new ResponseEntity<>("Duplicate email", HttpStatus.CONFLICT);
        }
        CustomerDto updatedCustomer = customerService.updateCustomer(customerDto);
        if (Objects.nonNull(updatedCustomer)) {
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        }
        return new ResponseEntity<>("Validation failed", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Integer customerId) {
        try {
            if (customerService.deleteCustomerById(customerId)) {
                return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to delete customer!", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> customerLogin(@RequestBody CustomerDto customerDto) {
        Object response = customerService.customerLogin(customerDto);
        if (response == HttpStatus.UNAUTHORIZED) {
            return new ResponseEntity<>("Wrong credentials!", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getUserDetailsService")
    public ResponseEntity<Object> getUserDetailsService(@RequestParam("username") String username) {
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(username), HttpStatus.OK);
    }
}
