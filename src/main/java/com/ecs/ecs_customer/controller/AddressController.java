package com.ecs.ecs_customer.controller;

import com.ecs.ecs_customer.dto.AddressDto;
import com.ecs.ecs_customer.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private IAddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable("id") Integer addressId) {
        AddressDto addressDto = addressService.getAddressById(addressId);
        return ResponseEntity.ok(addressDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/getAllAddressByCustomerId/{id}")
    public ResponseEntity<List<AddressDto>> getAllAddressByCustomerId(@PathVariable("id") Integer customerId) {
        return ResponseEntity.ok(addressService.getAllAddressByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<?> addAddress(@RequestBody AddressDto addressDto) {
        Object response = addressService.addAddress(addressDto);
        if (response == HttpStatus.CONFLICT) {
            return new ResponseEntity<>("Duplicate entry!", HttpStatus.CONFLICT);
        }
        if (response == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>("Customer mapping failed!", HttpStatus.NOT_FOUND);
        }
        if (Objects.nonNull(response)) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Validation failed!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> updateAddress(@RequestBody AddressDto addressDto) {
        Object response = addressService.updateAddress(addressDto);
        if (response == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>("Address not found!", HttpStatus.NOT_FOUND);
        } else if (response == HttpStatus.NOT_ACCEPTABLE) {
            return new ResponseEntity<>("Customer mapping failed!", HttpStatus.BAD_REQUEST);
        }
        if (Objects.nonNull(response)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Validation failed!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable("id") Integer addressId) {
        boolean isDeleted = addressService.deleteAddressById(addressId);
        if (isDeleted) {
            return new ResponseEntity<>("Deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Address not found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAddressByCustomerId/{id}")
    public ResponseEntity<String> deleteAddressByCustomerId(@PathVariable("id") Integer customerId) {
        boolean isDeleted = addressService.deleteAddressByCustomerId(customerId);
        if (isDeleted) {
            return new ResponseEntity<>("Deleted successfully!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Customer not found!", HttpStatus.NOT_FOUND);
    }
}
