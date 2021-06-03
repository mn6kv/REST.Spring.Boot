package ru.itis.javalab.restjwt.repositories;

/**
 * @author Mikhail Khovaev
 * <p>
 * 10.05.2021
 */
public interface BlackListRepository {
    void save(String token);
    boolean exists(String token);
}
