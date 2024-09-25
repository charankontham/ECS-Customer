package com.ecs.ecs_customer.util;

import com.ecs.ecs_customer.dto.*;
import com.ecs.ecs_customer.feign.CartService;
import com.ecs.ecs_customer.feign.OrderService;
import com.ecs.ecs_customer.service.interfaces.IAddressService;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

@Setter
public class HelperFunctions {

    private static IAddressService addressService;

    public static ResponseEntity<?> getResponseEntity(Object response) {
        if (Objects.equals(response, Constants.ProductNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found!");
        } else if (Objects.equals(response, Constants.CustomerNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found!");
        } else if (Objects.equals(response, Constants.ProductQuantityExceeded)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ProductQuantities Exceeded!");
        } else if (Objects.equals(response, Constants.AddressNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address Not Found!");
        } else if (Objects.equals(response, Constants.OrderNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order Not Found!");
        } else if (Objects.equals(response, Constants.CartNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart Not Found!");
        } else if (Objects.equals(response, Constants.UserNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found!");
        } else if (Objects.equals(response, Constants.ProductCategoryNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductCategory Not Found!");
        } else if (Objects.equals(response, Constants.ProductBrandNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductBrand Not Found!");
        } else if (Objects.equals(response, Constants.ProductReviewNotFound)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductReview Not Found!");
        } else if (Objects.equals(response, HttpStatus.CONFLICT)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate Entry!");
        } else if (Objects.equals(response, HttpStatus.BAD_REQUEST)) {
            return new ResponseEntity<>("Validation Failed/Bad Request!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static void removeCartByCustomerId(Integer customerId, CartService cartService) {
        CartFinalDto cart =  (CartFinalDto) cartService.getCartByCustomerId(customerId).getBody();
        if (cart != null) {
            cartService.deleteCart(cart.getCartId());
        }
    }

    public static void removeOrderByCustomerId(Integer customerId, OrderService orderService) {
        List<OrderFinalDto> orders = orderService.getAllOrdersByCustomerId(customerId).getBody();
        if (orders != null) {
            for (OrderFinalDto orderDto : orders) {
                orderService.deleteOrder(orderDto.getOrderId());
            }
        }
    }

    public static void removeAddressesByCustomerId(Integer customerId){
        addressService.deleteAddressByCustomerId(customerId);
    }

}
