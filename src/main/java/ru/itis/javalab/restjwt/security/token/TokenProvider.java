package ru.itis.javalab.restjwt.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
//        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
//        tokenAuthentication.setUserDetails(userDetails);
        tokenAuthentication.setUserDetails(tokenUtil.getUserDetails(tokenAuthentication.getName()));
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
