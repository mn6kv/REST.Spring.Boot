package ru.itis.javalab.restjwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.models.User;

@Component
public class TokenUtilImpl implements TokenUtil {
    public String create(UserDto user) {
        return JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("state", user.getState().toString())
                .withClaim("role", user.getRole().toString())
                .sign(Algorithm.HMAC256("key-pop"));
    }

    public DecodedJWT verify(String token) {
        try {
            return JWT.require(Algorithm.HMAC256("key-pop"))
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
