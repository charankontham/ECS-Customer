package com.ecs.ecs_customer.repository;

import com.ecs.ecs_customer.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    void deleteAddressByCustomerId(int customerId);
    List<Address> findAllByCustomerId(int customerId);
}
