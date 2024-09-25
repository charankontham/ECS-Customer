package com.ecs.ecs_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private int cartId;
    private int customerId;
    private List<Integer> productIds;
    private List<Integer> productQuantities;
}