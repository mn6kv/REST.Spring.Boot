package ru.itis.javalab.restjwt.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.models.User;

public interface TokenUtil {
    public String create(UserDto user);
    public DecodedJWT verify(String token);
}
