package ru.itis.javalab.restjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.models.User;
import ru.itis.javalab.restjwt.services.UsersService;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(@RequestHeader(name = "XSRF-TOKEN") String token) {
        List<UserDto> users = usersService.getAllUsers(token);
        return users != null
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestHeader(name = "XSRF-TOKEN") String token, @RequestBody UserDto user) {
        usersService.addUser(token, user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
