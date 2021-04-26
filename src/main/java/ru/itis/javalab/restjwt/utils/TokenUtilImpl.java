package ru.itis.javalab.restjwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.javalab.restjwt.dto.UserDto;

@Component
public class TokenUtilImpl implements TokenUtil {

    @Autowired
    @Qualifier("tokenUserDetailsService")
    UserDetailsService userDetailsService;

    private static final String SECRET_KEY = "key-pop";

    public String create(UserDto user) {
        return JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("state", user.getState().toString())
                .withClaim("role", user.getRole().toString())
                .sign(Algorithm.HMAC256(SECRET_KEY));
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

    public UserDetails getUserDetails(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("key-pop"))
                .build()
                .verify(token);
        String email = decodedJWT.getClaim("email").asString();
        System.out.println(email);
        return userDetailsService.loadUserByUsername(email);
    }
}
