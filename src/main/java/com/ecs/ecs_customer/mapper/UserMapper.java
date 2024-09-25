package com.ecs.ecs_customer.mapper;

import com.ecs.ecs_customer.dto.UserDto;
import com.ecs.ecs_customer.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
