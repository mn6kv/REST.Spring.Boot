package ru.itis.javalab.restjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.services.UsersService;

@RestController
public class SignInController {

    @Autowired
    UsersService usersService;

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody UserDto userDto) {
        String token = usersService.signIn(userDto);
        return token != null
                ? new ResponseEntity<>(token, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}