package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.feign.OrderService;
import com.ecs.ecs_customer.feign.ProductReviewService;
import com.ecs.ecs_customer.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveDependencies {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductReviewService productReviewService;

    public void deleteCustomerDependencies(Integer customerId) {
        productReviewService.deleteProductReviewByCustomerId(customerId);
        HelperFunctions.removeCartByCustomerId(customerId, orderService);
        HelperFunctions.removeOrderByCustomerId(customerId, orderService);
        HelperFunctions.removeAddressesByCustomerId(customerId);
    }
}
