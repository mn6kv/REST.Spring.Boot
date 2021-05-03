package ru.itis.javalab.restjwt.services;

import javassist.NotFoundException;

/**
 * @author Mikhail Khovaev
 * <p>
 * 03.05.2021
 */
public interface TokenService {
    String getRefreshToken(String userEmail) throws NotFoundException;
    void setRefreshToken(String email, String rToken);
}
