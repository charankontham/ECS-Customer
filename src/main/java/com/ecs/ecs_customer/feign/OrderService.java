package com.ecs.ecs_customer.feign;

import com.ecs.ecs_customer.dto.OrderDto;
import com.ecs.ecs_customer.dto.OrderFinalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface OrderService {
    @GetMapping("/{id}")
    ResponseEntity<OrderFinalDto> getOrderById(@PathVariable("id") int orderId);

    @GetMapping("/")
    ResponseEntity<List<OrderFinalDto>> getAllOrders();

    @GetMapping("/getOrdersByCustomerId/{id}")
    ResponseEntity<List<OrderFinalDto>> getAllOrdersByCustomerId(@PathVariable("id") int customerId);

    @PostMapping
    ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto);

    @PutMapping
    ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteOrder(@PathVariable("id") int orderId);
}
