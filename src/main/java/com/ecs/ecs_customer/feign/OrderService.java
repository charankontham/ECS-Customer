package com.ecs.ecs_customer.feign;

import com.ecs.ecs_customer.config.FeignClientConfig;
import com.ecs.ecs_customer.dto.CartFinalDto;
import com.ecs.ecs_customer.dto.OrderFinalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ECS-ORDER", configuration = FeignClientConfig.class)
public interface OrderService {

    @GetMapping("/api/order/getOrdersByCustomerId/{id}")
    ResponseEntity<List<OrderFinalDto>> getAllOrdersByCustomerId(@PathVariable("id") Integer customerId);

    @DeleteMapping("/api/order/{id}")
    ResponseEntity<String> deleteOrder(@PathVariable("id") Integer orderId);

    @GetMapping("/api/cart/getCartByCustomerId/{id}")
    ResponseEntity<CartFinalDto> getCartByCustomerId(@PathVariable("id") Integer customerId);

    @DeleteMapping("/api/cart/{id}")
    ResponseEntity<String> deleteCartItem(@PathVariable("id") Integer cartItemId);
}
