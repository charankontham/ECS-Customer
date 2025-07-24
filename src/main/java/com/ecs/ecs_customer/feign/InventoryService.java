package com.ecs.ecs_customer.feign;

import com.ecs.ecs_customer.dto.AdminDto;
import com.ecs.ecs_customer.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ECS-INVENTORY-ADMIN", configuration = FeignClientConfig.class)
public interface InventoryService {
    @GetMapping("/api/admin/getById/{id}")
    ResponseEntity<AdminDto> getAdminById(@PathVariable("id") String adminId);

    @GetMapping("/api/admin/getByUsername/{username}")
    ResponseEntity<AdminDto> getAdminByUsername(@PathVariable("username") String username);
}
