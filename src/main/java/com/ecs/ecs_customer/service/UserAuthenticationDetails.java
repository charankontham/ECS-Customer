package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.AdminDto;
import com.ecs.ecs_customer.dto.UserPrincipal;
import com.ecs.ecs_customer.entity.Customer;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.feign.AdminService;
import com.ecs.ecs_customer.mapper.CustomerMapper;
import com.ecs.ecs_customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class UserAuthenticationDetails implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customerResponse = customerRepository.findByEmail(username).orElse(null);
        if (Objects.nonNull(customerResponse)) {
            return new UserPrincipal(CustomerMapper.mapToCustomerDto(customerResponse));
        }
        ResponseEntity<AdminDto> adminResponse = adminService.getByUsername(username);
        if(adminResponse.getStatusCode() == HttpStatus.OK && Objects.nonNull(adminResponse.getBody())){
            return new UserPrincipal(adminResponse.getBody());
        }else{
            throw new ResourceNotFoundException("User not found");
        }
    }
}
