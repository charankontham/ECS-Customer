package com.ecs.ecs_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentDto {
    private Integer deliveryAgentId;
    private String deliveryAgentName;
    private String contactNumber;
    private String email;
    private String password;
    private Integer availabilityStatus;
    private Float rating;
    private Integer totalDeliveries;
    private String servingArea;
    private LocalDateTime dateAdded;
    private LocalDateTime dateModified;
}
