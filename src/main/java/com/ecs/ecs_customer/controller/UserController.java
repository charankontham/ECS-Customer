package com.ecs.ecs_customer.controller;

import com.ecs.ecs_customer.dto.UserDto;
import com.ecs.ecs_customer.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/public/authentication")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        UserDto newUserDto = userService.addUser(userDto);
        if (Objects.nonNull(newUserDto)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newUserDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed/Username already exists!");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        boolean isDeleted = userService.deleteUserByUsername(username);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("User seleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User sot found!");
    }
}
