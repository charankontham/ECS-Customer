package com.ecs.ecs_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {
    private Integer categoryId;
    private String categoryName;
    private String categoryImage;
}
