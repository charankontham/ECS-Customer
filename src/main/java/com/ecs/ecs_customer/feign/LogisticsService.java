package com.ecs.ecs_customer.feign;

import com.ecs.ecs_customer.config.FeignClientConfig;
import com.ecs.ecs_customer.dto.DeliveryAgentDto;
import com.ecs.ecs_customer.dto.DeliveryHubEnrichedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ECS-LOGISTICS", configuration = FeignClientConfig.class)
public interface LogisticsService {
    @GetMapping("/api/deliveryAgents/{id}")
    ResponseEntity<DeliveryAgentDto> getDeliveryAgentById(@PathVariable("id") Integer agentId);

    @GetMapping("/api/deliveryHubs/{id}")
    ResponseEntity<DeliveryHubEnrichedDto> getDeliveryHubById(@PathVariable("id") Integer hubId);
}
