package ru.itis.javalab.restjwt.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.javalab.restjwt.dto.UserDto;

public interface TokenUtil {
    String createAccess(UserDto user);
    String createRefresh(UserDto user);
    String create(UserDto user, long time);
    DecodedJWT verify(String token);
    UserDetails getUserDetails(String token);
    boolean isTokenActive(String token);
    String getAccessFromRefresh(String refreshToken);
    boolean checkRefreshToken(String rToken) throws NotFoundException;
}
