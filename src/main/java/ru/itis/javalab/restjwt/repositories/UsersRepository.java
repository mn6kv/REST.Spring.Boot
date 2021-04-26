package ru.itis.javalab.restjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.restjwt.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndHashPassword(String email, String hashPassword);
}
