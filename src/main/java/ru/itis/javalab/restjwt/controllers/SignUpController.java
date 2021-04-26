package ru.itis.javalab.restjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.services.UsersService;

@RestController
public class SignUpController {

    @Autowired
    UsersService usersService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        String token = usersService.signUp(userDto);
        return token != null
                ? new ResponseEntity<>(token, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
