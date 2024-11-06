package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.dto.UserDto;
import com.ecs.ecs_customer.dto.UserPrincipal;
import com.ecs.ecs_customer.entity.User;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.mapper.UserMapper;
import com.ecs.ecs_customer.repository.UserRepository;
import com.ecs.ecs_customer.service.interfaces.IUserService;
import com.ecs.ecs_customer.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (UserValidation.validateUser(userDto)
                && !userRepository.existsById(userDto.getUserId())
                && userRepository.findByUsername(userDto.getUsername()).isEmpty()
        ) {
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            User newUser = userRepository.save(UserMapper.mapToUser(userDto));
            return UserMapper.mapToUserDto(newUser);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteUserByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            userRepository.deleteByUsername(username);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException("User not found!");
        } else {
            return new UserPrincipal(user);
        }
    }
}
