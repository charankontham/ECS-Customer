package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.UserPrincipal;
import com.ecs.ecs_customer.entity.Customer;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username).orElse(null);
        if (Objects.isNull(customer)) {
            throw new ResourceNotFoundException("Customer not found!");
        } else {
            return new UserPrincipal(customer);
        }
    }
}
