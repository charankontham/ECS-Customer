package com.ecs.ecs_customer.feign;

import com.ecs.ecs_customer.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("CART-SERVICE")
public interface CartService {
    @GetMapping("/{id}")
    ResponseEntity<?> getCart(@PathVariable("id") int cartId);

    @GetMapping("/getCartByCustomerId/{id}")
    ResponseEntity<?> getCartByCustomerId(@PathVariable("id") int customerId);

    @PostMapping
    ResponseEntity<?> addCart(@RequestBody CartDto cartDto);

    @PutMapping
    ResponseEntity<?> updateCart(@RequestBody CartDto cartDto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCart(@PathVariable("id") int cartId);
}
