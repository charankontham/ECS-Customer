package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.dto.UserDto;

public interface IUserService {

    UserDto getUserById(Integer userId);

    UserDto getUserByUsername(String username);

    UserDto addUser(UserDto userDto);

    boolean deleteUserByUsername(String username);


}
