package ru.itis.javalab.restjwt.security.token;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.javalab.restjwt.services.TokenService;
import ru.itis.javalab.restjwt.utils.TokenUtil;

/**
 * @author Mikhail Khovaev
 * <p>
 * 24.04.2021
 */
@Component
public class TokenProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("tokenUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    TokenService tokenService;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        String accessToken = tokenAuthentication.getName();
        String refreshToken = tokenAuthentication.getRefreshToken();

        if (!tokenUtil.checkRefreshToken(refreshToken)) {
            throw new TokenExpiredException("Токен устарел, войдите еще раз");
        } else if (!tokenUtil.isTokenActive(accessToken)) {
            try {
                accessToken = tokenUtil.getAccessFromRefresh(refreshToken);
            } catch (TokenExpiredException e) {
                if (!tokenUtil.checkRefreshToken(refreshToken)) {
                    e.printStackTrace();
                    System.out.println("токены не совпали");
                }
            }
        }
        tokenAuthentication.setUserDetails(tokenUtil.getUserDetails(accessToken));
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
