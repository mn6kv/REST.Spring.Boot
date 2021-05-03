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
    public ResponseEntity<String[]> signUp(@RequestBody UserDto userDto) {
        String[] tokens = usersService.signUp(userDto);
        return tokens != null
                ? new ResponseEntity<>(tokens, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
