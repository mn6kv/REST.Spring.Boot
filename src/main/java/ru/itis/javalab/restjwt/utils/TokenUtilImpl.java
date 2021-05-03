package ru.itis.javalab.restjwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import javassist.NotFoundException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.models.User;
import ru.itis.javalab.restjwt.services.TokenService;
import ru.itis.javalab.restjwt.services.UsersService;

import java.util.Date;

@Component
public class TokenUtilImpl implements TokenUtil {

    @Autowired
    @Qualifier("tokenUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    TokenService tokenService;

    private static final String SECRET_KEY = "key-pop";
    private static final long ACCESS_EXPIRATION_TIME = 8 * 60 * 60 * 100;
    private static final long REFRESH_EXPIRATION_TIME = 8 * 60 * 60 * 100 * 365;

    @Override
    public String createAccess(UserDto user) {
        return this.create(user, ACCESS_EXPIRATION_TIME);
    }

    @Override
    public String createRefresh(UserDto user) {
        return this.create(user, REFRESH_EXPIRATION_TIME);
    }

    @Override
    public String create(UserDto user, long time) {
        return JWT.create()
                .withSubject(String.valueOf(user.getId()))
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("state", user.getState().toString())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(new Date(new Date().getTime() + time))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public DecodedJWT verify(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAccessFromRefresh(String refreshToken) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(refreshToken);
        UserDto user = UserDto.builder()
                .email(decodedJWT.getClaim("email").asString())
                .name(decodedJWT.getClaim("name").asString())
                .state(User.State.valueOf(decodedJWT.getClaim("state").asString()))
                .role(User.Role.valueOf(decodedJWT.getClaim("role").asString()))
                .build();
        return this.create(user, ACCESS_EXPIRATION_TIME);
    }

    public UserDetails getUserDetails(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);

        String email = decodedJWT.getClaim("email").asString();
        System.out.println(email);
        return userDetailsService.loadUserByUsername(email);
    }

    public boolean checkRefreshToken(String rToken) throws NotFoundException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(rToken);
        String email = decodedJWT.getClaim("email").asString();
        return rToken.equals(tokenService.getRefreshToken(email));
    }

    @Override
    public boolean isTokenActive(String token) throws TokenExpiredException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);
        if (decodedJWT.getExpiresAt().compareTo(new Date()) < 0)
            return false;
        return true;
    }
}
