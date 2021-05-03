package ru.itis.javalab.restjwt.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.javalab.restjwt.models.User;
import ru.itis.javalab.restjwt.repositories.UsersRepository;

/**
 * @author Mikhail Khovaev
 * <p>
 * 03.05.2021
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public String getRefreshToken(String userEmail) throws NotFoundException {
        User dbUser = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return dbUser.getRefreshToken();
    }

    @Override
    public void setRefreshToken(String email, String rToken) {
        User dbUser = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        dbUser.setRefreshToken(rToken);
        usersRepository.save(dbUser);
    }
}
