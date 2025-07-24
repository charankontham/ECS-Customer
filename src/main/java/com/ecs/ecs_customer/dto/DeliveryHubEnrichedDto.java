package com.ecs.ecs_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeliveryHubEnrichedDto {
    private Integer deliveryHubId;
    private String deliveryHubName;
    private AddressDto deliveryHubAddress;
    private LocalDateTime dateAdded;
    private LocalDateTime dateModified;
}
