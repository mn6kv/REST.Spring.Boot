package ru.itis.javalab.restjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.services.UsersService;

/**
 * @author Mikhail Khovaev
 * <p>
 * 24.04.2021
 */
@RestController
public class SignInController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody UserDto userDto) {
        String token = usersService.signIn(userDto);
        return token != null
                ? new ResponseEntity<>(token, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
