package ru.itis.javalab.restjwt.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.restjwt.models.Order;
import ru.itis.javalab.restjwt.repositories.OrderRepository;
import ru.itis.javalab.restjwt.utils.TokenUtil;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public Long newOrder(String token, Long itemId) {
        DecodedJWT decodedJWT = tokenUtil.verify(token);
        decodedJWT.getSubject();
        orderRepository.save(Order.builder()
                .userId(Long.parseLong(decodedJWT.getSubject()))
                .itemId(itemId)
                .build());
        return null;
    }
}
