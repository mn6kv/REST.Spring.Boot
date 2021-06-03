package ru.itis.javalab.restjwt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.restjwt.services.JWTBlackListService;

/**
 * @author Mikhail Khovaev
 * <p>
 * 10.05.2021
 */
@Service
public class JWTBlackListServiceImpl implements JWTBlackListService {

    @Autowired
    BlackListRepositoryRedisTemplateImpl blackListRepository;

    @Override
    public void add(String token) {
        blackListRepository.save(token);
    }

    @Override
    public boolean exists(String token) {
        return blackListRepository.exists(token);
    }
}
