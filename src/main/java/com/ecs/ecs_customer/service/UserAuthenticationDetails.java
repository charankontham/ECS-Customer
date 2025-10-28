package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.AdminDto;
import com.ecs.ecs_customer.dto.UserPrincipal;
import com.ecs.ecs_customer.entity.Customer;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.feign.InventoryService;
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
    private InventoryService inventoryService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Customer customerResponse = customerRepository.findByEmail(username).orElse(null);
            if (Objects.nonNull(customerResponse)) {
                return new UserPrincipal(CustomerMapper.mapToCustomerDto(customerResponse));
            }else{
                ResponseEntity<AdminDto> adminResponse = inventoryService.getAdminByUsername(username);
                if(adminResponse.getStatusCode() == HttpStatus.OK && Objects.nonNull(adminResponse.getBody())){
                    return new UserPrincipal(adminResponse.getBody());
                }
            }
        }catch (Exception e){
            System.out.println("Exception catched!");
            ResponseEntity<AdminDto> adminResponse = inventoryService.getAdminByUsername(username);
            if(adminResponse.getStatusCode() == HttpStatus.OK && Objects.nonNull(adminResponse.getBody())){
                return new UserPrincipal(adminResponse.getBody());
            }
        }
        throw new ResourceNotFoundException("User not found");
    }
}
