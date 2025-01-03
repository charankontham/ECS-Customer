package com.ecs.ecs_customer.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String username;
    private String password;

    @NotNull
    @NotBlank(message = "Role cannot be blank")
    @NotEmpty(message = "Role cannot be null")
    private String role;
}
