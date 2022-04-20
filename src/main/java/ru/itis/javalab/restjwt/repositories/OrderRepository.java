package ru.itis.javalab.restjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.restjwt.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
