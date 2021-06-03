package ru.itis.javalab.restjwt.services;

/**
 * @author Mikhail Khovaev
 * <p>
 * 10.05.2021
 */
public interface JWTBlackListService {
    void add(String token);
    boolean exists(String token);
}
