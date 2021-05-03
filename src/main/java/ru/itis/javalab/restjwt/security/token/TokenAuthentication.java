package ru.itis.javalab.restjwt.security.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.javalab.restjwt.security.userDetails.UserDetailsImpl;

import java.util.Collection;

/**
 * @author Mikhail Khovaev
 * <p>
 * 24.04.2021
 */
public class TokenAuthentication implements Authentication {

    private UserDetailsImpl userDetails;

    private boolean isAuthenticated;

    private String accessToken;
    private String refreshToken;

    public TokenAuthentication(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

        public void setUserDetails(UserDetails userDetails) {
        this.userDetails = (UserDetailsImpl)userDetails;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails != null
                ? userDetails.getUser()
                : null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.isAuthenticated = b;
    }

    @Override
    public String getName() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}

