package ru.itis.javalab.restjwt.services;

public interface OrderService {
    Long newOrder(String token, Long itemId);
}
