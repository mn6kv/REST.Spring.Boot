package ru.itis.javalab.restjwt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.javalab.restjwt.repositories.BlackListRepository;

/**
 * @author Mikhail Khovaev
 * <p>
 * 10.05.2021
 */
@Repository
public class BlackListRepositoryRedisTemplateImpl implements BlackListRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String token) {
        redisTemplate.opsForValue().set(token, "");
    }

    @Override
    public boolean exists(String token) {
        Boolean hasKey = redisTemplate.hasKey(token);
        return hasKey != null && hasKey;
    }
}
