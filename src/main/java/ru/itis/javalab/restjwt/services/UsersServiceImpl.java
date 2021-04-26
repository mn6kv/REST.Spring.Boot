package ru.itis.javalab.restjwt.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.restjwt.dto.UserDto;
import ru.itis.javalab.restjwt.models.User;
import ru.itis.javalab.restjwt.repositories.UsersRepository;
import ru.itis.javalab.restjwt.utils.TokenUtil;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers(String token) {
        DecodedJWT decodedJWT = tokenUtil.verify(token);
        if (decodedJWT.getClaim("state").equals(User.State.BANNED))
            return null;
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public User addUser(String token, UserDto user) {
        DecodedJWT decodedJWT = tokenUtil.verify(token);
        if (decodedJWT.getClaim("state").asString().equals(User.State.BANNED.toString()) ||
                !decodedJWT.getClaim("role").asString().equals(User.Role.ADMIN.toString()))
            return null;
        return this.addUser(user);
    }

    private User addUser(UserDto user) {
        return user != null
                ? usersRepository.save(User.builder()
                .email(user.getEmail())
                .hashPassword(passwordEncoder.encode(user.getHashPassword()))
                .name(user.getName())
                .state(user.getState())
                .role(user.getRole())
                .build())
                : null;
    }

    @Override
    public String signUp(UserDto user) {
        String token = tokenUtil.create(user);
        if (this.addUser(user) != null)
            return token;
        return null;
    }

    @Override
    public String signIn(UserDto userDto) {
        User dbUser = usersRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(userDto.getHashPassword(), dbUser.getHashPassword()))
            return tokenUtil.create(userDto);
        else throw new UsernameNotFoundException("Invalid username or password");
    }
}
