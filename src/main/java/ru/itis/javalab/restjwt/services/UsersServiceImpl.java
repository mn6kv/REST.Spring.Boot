package ru.itis.javalab.restjwt.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
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
        return usersRepository.save(User.builder()
                                        .email(user.getEmail())
                                        .name(user.getName())
                                        .state(user.getState())
                                        .role(user.getRole())
                                        .build());
    }

    private User addUser(UserDto user) {
        return user != null
                ? usersRepository.save(User.builder()
                                 .email(user.getEmail())
                                 .name(user.getName())
                                 .state(user.getState())
                                 .role(user.getRole())
                                 .build())
                : null;
    }

    @Override
    public String signIn(UserDto user) {
        String token = tokenUtil.create(user);
        if (this.addUser(user) != null)
            return token;
        return null;
    }
}
