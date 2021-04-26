package ru.itis.javalab.restjwt.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.javalab.restjwt.dto.UserDto;

public interface TokenUtil {
    String create(UserDto user);
    DecodedJWT verify(String token);
    UserDetails getUserDetails(String token);
}
