package ru.itis.javalab.restjwt.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.javalab.restjwt.services.JWTBlackListService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mikhail Khovaev
 * <p>
 * 10.05.2021
 */
@Component
public class JWTLogoutFilter extends OncePerRequestFilter {

    @Autowired
    JWTBlackListService jwtBlackListService;

    private final RequestMatcher logoutReq = new AntPathRequestMatcher("/logout", "GET");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (logoutReq.matches(request)) {
            jwtBlackListService.add(request.getHeader("XSRF-TOKEN"));
            SecurityContextHolder.clearContext();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
