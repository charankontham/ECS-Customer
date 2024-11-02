package com.ecs.ecs_customer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ECS-REVIEWS")
public interface ProductReviewService {
    @DeleteMapping("api/productReview/deleteByCustomerId/{customerId}")
    ResponseEntity<String> deleteProductReviewByCustomerId(@PathVariable("customerId") int customerId);
}
