package ru.itis.javalab.restjwt.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.javalab.restjwt.security.token.TokenAuthentication;
import ru.itis.javalab.restjwt.services.JWTBlackListService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    JWTBlackListService blackListService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = request.getHeader("XSRF-TOKEN");
        String refreshToken = request.getHeader("REF-TOKEN");

        if (accessToken != null && blackListService.exists(accessToken)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (accessToken != null && refreshToken != null) {
            TokenAuthentication tokenAuthentication = new TokenAuthentication(accessToken, refreshToken);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }
        filterChain.doFilter(request, response);
    }

}
