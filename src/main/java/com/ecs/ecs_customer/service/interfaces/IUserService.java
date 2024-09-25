package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.dto.UserDto;

public interface IUserService {

    UserDto getUserById(int userId);

    UserDto getUserByUsername(String username);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    boolean deleteUserById(int userId);
}
