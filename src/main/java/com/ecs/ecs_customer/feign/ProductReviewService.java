package com.ecs.ecs_customer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PRODUCT-REVIEW-SERVICE")
public interface ProductReviewService {
    @DeleteMapping("/deleteByCustomerId/{customerId}")
    ResponseEntity<String> deleteProductReviewByCustomerId(@PathVariable("customerId") int customerId);
}
