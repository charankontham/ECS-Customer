package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.CartFinalDto;
import com.ecs.ecs_customer.dto.CartItemEnrichedDto;
import com.ecs.ecs_customer.dto.OrderFinalDto;
import com.ecs.ecs_customer.feign.OrderService;
import com.ecs.ecs_customer.feign.ProductReviewService;
import com.ecs.ecs_customer.service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RemoveDependencies {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductReviewService productReviewService;
    @Autowired
    private IAddressService addressService;

    @Transactional
    public Boolean deleteCustomerDependencies(Integer customerId) {
        try {
            productReviewService.deleteProductReviewByCustomerId(customerId);
            if (!removeCartByCustomerId(customerId)) {
                return false;
            }
            if (!removeOrderByCustomerId(customerId)) {
                return false;
            }
            if (!addressService.deleteAddressByCustomerId(customerId)) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return true;
    }

    public Boolean removeCartByCustomerId(Integer customerId) {
        CartFinalDto cart = orderService.getCartByCustomerId(customerId).getBody();
        assert cart != null;
        for (CartItemEnrichedDto cartItem : cart.getCartItems()) {
            if (orderService.deleteCartItem(cartItem.getCartItemId()).getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
        }
        return true;
    }

    public Boolean removeOrderByCustomerId(Integer customerId) {
        List<OrderFinalDto> orders = orderService.getAllOrdersByCustomerId(customerId).getBody();
        if (orders != null) {
            for (OrderFinalDto orderDto : orders) {
                if (orderService.deleteOrder(orderDto.getOrderId()).getStatusCode() != HttpStatus.OK) {
                    return false;
                }
            }
        }
        return true;
    }
}
