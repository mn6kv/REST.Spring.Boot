package ru.itis.javalab.restjwt.services;

import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.models.User;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers(String token);
    User addUser(String token, UserDto user);
    String signUp(UserDto user);
    String signIn(UserDto userDto);
}
