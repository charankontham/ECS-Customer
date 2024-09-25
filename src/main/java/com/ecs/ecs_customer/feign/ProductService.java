package com.ecs.ecs_customer.feign;

import com.ecs.ecs_customer.dto.ProductDto;
import com.ecs.ecs_customer.dto.ProductFinalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient("PRODUCT-SERVICE")
public interface ProductService {
    @GetMapping("/{id}")
    ResponseEntity<ProductFinalDto> getProductById(@PathVariable("id") int productId);

    @GetMapping("/")
    ResponseEntity<List<ProductFinalDto>> getAllProducts();

    @PostMapping
    ResponseEntity<?> addProduct(@RequestBody ProductDto productDto);

    @PutMapping()
    ResponseEntity<?> updateProduct(@RequestBody ProductFinalDto productFinalDto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable("id") int productId);
}
