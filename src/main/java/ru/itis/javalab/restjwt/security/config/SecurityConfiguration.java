package ru.itis.javalab.restjwt.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.javalab.restjwt.security.filters.JWTAuthFilter;
import ru.itis.javalab.restjwt.security.filters.JWTLogoutFilter;
import ru.itis.javalab.restjwt.security.token.TokenProvider;

/**
 * @author Mikhail Khovaev
 * <p>
 * 22.04.2021
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JWTAuthFilter jwtAuthFilter;

    @Autowired
    JWTLogoutFilter logoutFilter;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    @Qualifier("tokenUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(logoutFilter, LogoutFilter.class)
                .authorizeRequests()
                .antMatchers("/signIn").permitAll()
                .antMatchers("/signUp").permitAll()
                .antMatchers("/users").hasAuthority("ADMIN")
                .anyRequest().authenticated()

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))

                .and()
                .sessionManagement().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenProvider);
    }
}
