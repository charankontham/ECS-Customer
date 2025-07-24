package com.ecs.ecs_customer.repository;

import com.ecs.ecs_customer.entity.Address;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Transactional
    void deleteAddressByUserId(String userId) throws DataIntegrityViolationException;

    List<Address> findAllByUserId(String userId);
}
