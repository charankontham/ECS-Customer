package com.ecs.ecs_customer.repository;

import com.ecs.ecs_customer.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Transactional
    void deleteAddressByCustomerId(Integer customerId);

    List<Address> findAllByCustomerId(Integer customerId);
}
