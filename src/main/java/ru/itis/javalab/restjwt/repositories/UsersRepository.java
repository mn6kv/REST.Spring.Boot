package ru.itis.javalab.restjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.restjwt.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {
}
