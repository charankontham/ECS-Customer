package com.ecs.ecs_customer.feign;

import com.ecs.ecs_customer.dto.ProductDto;
import com.ecs.ecs_customer.dto.ProductFinalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient("ECS-PRODUCT")
public interface ProductService {
    @GetMapping("/api/product/{id}")
    ResponseEntity<ProductFinalDto> getProductById(@PathVariable("id") int productId);

    @GetMapping("/api/product/")
    ResponseEntity<List<ProductFinalDto>> getAllProducts();

    @PostMapping("/api/product")
    ResponseEntity<?> addProduct(@RequestBody ProductDto productDto);

    @PutMapping("/api/product")
    ResponseEntity<?> updateProduct(@RequestBody ProductFinalDto productFinalDto);

    @DeleteMapping("/api/product/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable("id") int productId);
}
