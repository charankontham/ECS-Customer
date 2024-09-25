package com.ecs.ecs_customer.repository;

import com.ecs.ecs_customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String email);
}
